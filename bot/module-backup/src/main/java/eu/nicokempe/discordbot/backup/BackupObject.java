package eu.nicokempe.discordbot.backup;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import eu.nicokempe.discordbot.request.RequestBuilder;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.internal.entities.MemberImpl;
import net.dv8tion.jda.internal.entities.PermissionOverrideImpl;
import okhttp3.FormBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Getter
@Setter
public class BackupObject implements IBackupObject {

    private final List<BackupEntry> backups = new ArrayList<>();
    private long backupInterval = -1L;

    @Override
    public void load() {
        backups.clear();
        JsonObject backupRequest = RequestBuilder.builder().route("backups").authKey(Backup.INSTANCE.getDiscordBot().getAuthKey()).build().get().getAsJsonObject();
        backupInterval = backupRequest.get("backupInterval").getAsLong();

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
                                .add("backups", new Gson().toJson(new BackupLoadObject(backups, backupInterval))))
                .build().post();
    }

    @Override
    public void saveBackup(long creator, Consumer<BackupEntry> onFinish) {
        BackupEntry entry = new BackupEntry(String.valueOf(creator));

        Guild guild = Backup.INSTANCE.getDiscordBot().getGuild();

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

        backups.add(entry);
        onFinish.accept(entry);

        update();
    }

    private List<ChannelPermissionEntry> getPermissionEntry(List<PermissionOverride> permissionOverrides) {
        Guild guild = Backup.INSTANCE.getDiscordBot().getGuild();

        return permissionOverrides.stream().map(permissionOverride ->
                new ChannelPermissionEntry(
                        permissionOverride.getChannel().getName(),
                        permissionOverride.getChannel().getId(),
                        permissionOverride.getId(),
                        permissionOverride.getAllowed(),
                        permissionOverride.getDenied()
                )).collect(Collectors.toList());
    }


    @Override
    public void loadBackup(String id, Runnable onFinish) throws BackupNotFoundException {
        if (!existBackup(id)) throw new BackupNotFoundException("There is no backup with id" + id, id);
        BackupEntry entry = getBackup(id);

        Guild guild = Backup.INSTANCE.getDiscordBot().getGuild();

        for (GuildChannel channel : guild.getChannels()) {
            channel.delete().queue();
        }

        for (BackupChannel channel : entry.getChannels().stream().filter(backupChannel -> backupChannel.getChannelType().equals(ChannelType.CATEGORY)).toList()) {
            guild.createCategory(channel.getName()).queue(categories -> {
                categories.getManager().setPosition(channel.getPosition()).queue();
            });
        }

        for (BackupChannel channel : entry.getChannels().stream().filter(backupChannel -> backupChannel.getChannelType().equals(ChannelType.VOICE)).toList()) {
            //Category category = guild.getCategories().stream().filter(categories -> channel.getCategory() != null && categories.getName().equalsIgnoreCase(channel.getCategory())).findFirst().orElse(null);
            guild.createVoiceChannel(channel.getName()).queue(voiceChannel -> {
                voiceChannel.getManager().setPosition(channel.getPosition()).queue();
                voiceChannel.getManager().setUserLimit(channel.getUserLimit()).queue();

                if (channel.getCategory() != null)
                    voiceChannel.getManager().setParent(guild.getCategoriesByName(channel.getCategory(), true).get(0)).queue();
            });
        }

        for (BackupChannel channel : entry.getChannels().stream().filter(backupChannel -> backupChannel.getChannelType().equals(ChannelType.TEXT)).toList()) {
            //Category category = guild.getCategories().stream().filter(categories -> channel.getCategory() != null && categories.getName().equalsIgnoreCase(channel.getCategory())).findFirst().orElse(null);

            guild.createTextChannel(channel.getName()).queue(textChannel -> {
                textChannel.getManager().setTopic(channel.getDescription()).queue();
                textChannel.getManager().setPosition(channel.getPosition()).queue();
                textChannel.getManager().setSlowmode(channel.getSlowMode()).queue();

                if (channel.getCategory() != null)
                    textChannel.getManager().setParent(guild.getCategoriesByName(channel.getCategory(), true).get(0)).queue();
            });
        }

        onFinish.run();
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
