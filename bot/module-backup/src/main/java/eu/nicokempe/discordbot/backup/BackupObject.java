package eu.nicokempe.discordbot.backup;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import eu.nicokempe.discordbot.IDiscordBot;
import eu.nicokempe.discordbot.request.RequestBuilder;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import okhttp3.FormBody;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Getter
@Setter
public class BackupObject implements IBackupObject {

    private final List<BackupEntry> backups = new ArrayList<>();
    private long backupInterval = -1L;
    private boolean createNew = false;

    @Override
    public void load() {
        backups.clear();
        JsonObject backupRequest = RequestBuilder.builder().route("backups").authKey(Backup.INSTANCE.getDiscordBot().getAuthKey()).build().get().getAsJsonObject();
        backupInterval = backupRequest.get("backupInterval").getAsLong();

        if (backupRequest.get("createNew").getAsBoolean()) {
            saveBackup(Backup.INSTANCE.getDiscordBot().getGuild().getSelfMember().getIdLong(), backupEntry -> {
                createNew = false;
                update();
            });
        }

        for (JsonElement element : backupRequest.get("backups").getAsJsonArray()) {
            BackupEntry backup = new Gson().fromJson(element.getAsJsonObject().toString(), BackupEntry.class);
            backups.add(backup);
        }

    }

    @Override
    public void update() {
        RequestBuilder.builder()
                .route("backups")
                .authKey(Backup.INSTANCE.getDiscordBot().getAuthKey())
                .body(
                        new FormBody.Builder()
                                .add("backups", new Gson().toJson(new BackupLoadObject(backups, backupInterval, createNew))))
                .build().post();
    }

    @Override
    public void saveBackup(long creator, Consumer<BackupEntry> onFinish) {
        Guild guild = Backup.INSTANCE.getDiscordBot().getGuild();

        BackupEntry entry = new BackupEntry(String.valueOf(creator), guild.getIconUrl());

        for (GuildChannel channel : guild.getChannels()) {

            Category channelCategory = guild.getCategories().
                    stream().
                    filter(category ->
                            category.getChannels().
                                    stream().
                                    anyMatch(categoryChannel ->
                                            channel.getIdLong() == categoryChannel.getIdLong())).
                    findFirst().
                    orElse(null);

            BackupChannel backupChannel = new BackupChannel(
                    channel.getId(),
                    channel.getType(),
                    channel.getName(),
                    channel.getType().equals(ChannelType.TEXT) ? ((TextChannel) channel).getTopic() : null,
                    channelCategory == null ? null : channelCategory.getName(),
                    channel.getPosition(),
                    channel.getType().equals(ChannelType.TEXT) && ((TextChannel) channel).isNSFW(),
                    channel.getType().equals(ChannelType.TEXT) && ((TextChannel) channel).isNews(),
                    channel.getType().equals(ChannelType.VOICE) ? ((VoiceChannel) channel).getUserLimit() : 0,
                    channel.getType().equals(ChannelType.TEXT) ? ((TextChannel) channel).getSlowmode() : 0,
                    getPermissionEntry(channel.getPermissionOverrides())
            );

            entry.getChannels().add(backupChannel);
        }

        for (Role role : guild.getRoles()) {
            if (role.isPublicRole()) continue;

            BackupRole backupRole = new BackupRole(
                    role.getId(),
                    role.getName(),
                    role.getColorRaw(),
                    role.isMentionable(),
                    role.isHoisted(),
                    role.getIcon(),
                    guild.getMembersWithRoles(List.of(role)).stream().map(member -> member.getUser().getId()).collect(Collectors.toList()),
                    role.getPermissions()
            );

            entry.getRoles().add(backupRole);
        }

        backups.add(entry);
        onFinish.accept(entry);

        update();
    }

    private List<ChannelPermissionEntry> getPermissionEntry(List<PermissionOverride> permissionOverrides) {
        return permissionOverrides.stream().map(permissionOverride ->
                new ChannelPermissionEntry(
                        permissionOverride.getPermissionHolder() instanceof Member ? 0 : 1,
                        permissionOverride.getPermissionHolder().getId(),
                        permissionOverride.getAllowed(),
                        permissionOverride.getDenied()
                )).collect(Collectors.toList());
    }

    @Override
    public void loadBackup(String id, Runnable onFinish) throws BackupNotFoundException {
        if (!existBackup(id)) throw new BackupNotFoundException("There is no backup with id" + id, id);

        System.out.println("Loading backup with id " + id + "...");

        Guild guild = Backup.INSTANCE.getDiscordBot().getGuild();

        for (GuildChannel channel : guild.getChannels()) {
            channel.delete().queue();
        }

        List<Role> roles = guild.getRoles().stream().filter(role -> !role.getPermissions().contains(Permission.ADMINISTRATOR) && !role.isManaged() && !role.isPublicRole()).toList();
        AtomicInteger i = new AtomicInteger(roles.size());
        for (Role role : roles) {
            role.delete().queue(unused -> {
                i.getAndDecrement();
                if (i.get() == 0) createRoles(id, onFinish);
            });
        }
        if (i.get() == 0) createRoles(id, onFinish);

    }

    private void createRoles(String id, Runnable onFinish) {
        Guild guild = Backup.INSTANCE.getDiscordBot().getGuild();
        BackupEntry entry = getBackup(id);

        Map<String, Role> newRole = new HashMap<>();

        List<BackupRole> roles = entry.getRoles().stream().filter(backupRole -> guild.getRolesByName(backupRole.getName(), false).isEmpty()).toList();
        AtomicInteger i = new AtomicInteger(roles.size());
        for (BackupRole role : roles) {
            guild.createRole().
                    setName(role.getName()).
                    setColor(role.getColor()).
                    setIcon(role.getRoleIcon() == null ? null : role.getRoleIcon().getIconUrl()).
                    setHoisted(role.isHoisted()).
                    setMentionable(role.isMentionable()).
                    setPermissions(role.getPermissions()).
                    queue(created -> {
                                newRole.put(role.getId(), created);
                                for (String s : role.getMember()) {
                                    Member member = guild.getMemberById(s);
                                    if (member == null) continue;
                                    guild.addRoleToMember(member, created).queue();
                                }
                                i.getAndDecrement();
                                if (i.get() == 0) createChannel(id, onFinish, newRole);
                            }
                    );
        }
        if (i.get() == 0) createChannel(id, onFinish, newRole);
    }

    private void createChannel(String id, Runnable onFinish, Map<String, Role> newRole) {
        Guild guild = Backup.INSTANCE.getDiscordBot().getGuild();
        BackupEntry entry = getBackup(id);

        for (BackupChannel channel : entry.getChannels()) {
            switch (channel.getChannelType()) {
                case CATEGORY -> guild.createCategory(channel.getName()).queue(categories -> {
                    categories.getManager().setPosition(channel.getPosition()).queue();

                    applyPermissions(categories, channel, newRole);
                });
                case VOICE -> guild.createVoiceChannel(channel.getName()).queue(voiceChannel -> {
                    voiceChannel.getManager().setPosition(channel.getPosition()).queue();
                    voiceChannel.getManager().setUserLimit(channel.getUserLimit()).queue();

                    if (channel.getCategory() != null)
                        voiceChannel.getManager().setParent(guild.getCategoriesByName(channel.getCategory(), true).get(0)).queue();

                    applyPermissions(voiceChannel, channel, newRole);
                });
                case TEXT -> guild.createTextChannel(channel.getName()).queue(textChannel -> {
                    textChannel.getManager().setTopic(channel.getDescription()).queue();
                    textChannel.getManager().setPosition(channel.getPosition()).queue();
                    textChannel.getManager().setSlowmode(channel.getSlowMode()).queue();

                    if (channel.getCategory() != null)
                        textChannel.getManager().setParent(guild.getCategoriesByName(channel.getCategory(), true).get(0)).queue();

                    applyPermissions(textChannel, channel, newRole);
                });
            }
        }

        onFinish.run();
    }

    private void applyPermissions(GuildChannel guildChannel, BackupChannel backupChannel, Map<String, Role> newRole) {
        Guild guild = Backup.INSTANCE.getDiscordBot().getGuild();

        for (ChannelPermissionEntry permissionEntry : backupChannel.getPermissionEntries()) {
            IPermissionHolder permissionHolder = permissionEntry.getTyp() == 0 ? guild.getMemberById(permissionEntry.getId()) : newRole.getOrDefault(permissionEntry.getId(), null);
            if (permissionHolder == null) {

                continue;
            }
            guildChannel.createPermissionOverride(permissionHolder).setPermissions(permissionEntry.getAllowed(), permissionEntry.getDenied()).queue();
        }
    }

    @Override
    public void deleteBackup(String id) throws BackupNotFoundException {
        if (!existBackup(id)) throw new BackupNotFoundException("There is no backup with id" + id, id);
        backups.removeIf(backupEntry -> backupEntry.getId().equals(id));
        update();
    }

    @Override
    public boolean existBackup(String id) {
        return getBackup(id) != null;
    }

    @Override
    public BackupEntry getBackup(String id) {
        return getBackups().stream().filter(backupEntry -> backupEntry.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public BackupEntry getLastBackup() throws BackupNotFoundException {
        return getBackups().stream().findFirst().orElseThrow(() -> new BackupNotFoundException("There is no last backup", null));
    }

    @Override
    public BackupEntry getFirstBackup() throws BackupNotFoundException {
        List<BackupEntry> reversedBackups = new ArrayList<>(getBackups());
        Collections.reverse(reversedBackups);
        return reversedBackups.stream().findFirst().orElseThrow(() -> new BackupNotFoundException("There is no first backup", null));
    }
}
