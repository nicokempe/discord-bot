package eu.nicokempe.discordbot.backup;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import eu.nicokempe.discordbot.request.RequestBuilder;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.PermissionOverride;
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

    private final Guild guild;

    public BackupObject() {
        guild = Backup.INSTANCE.getDiscordBot().getGuild();
    }

    @Override
    public void load() {
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
    public void saveBackup(Consumer<BackupEntry> onFinish) {
        BackupEntry entry = new BackupEntry();

        entry.getVoiceChannel().addAll(getPermissionEntry(getPermissionOverride(new ArrayList<>(guild.getVoiceChannels()))));
        entry.getTextChannel().addAll(getPermissionEntry(getPermissionOverride(new ArrayList<>(guild.getTextChannels()))));
        entry.getCategories().addAll(getPermissionEntry(getPermissionOverride(new ArrayList<>(guild.getCategories()))));

        backups.add(entry);
        onFinish.accept(entry);

        update();
    }

    private List<ChannelPermissionEntry> getPermissionEntry(List<PermissionOverride> permissionOverrides) {
        return permissionOverrides.stream().map(permissionOverride -> {
            Category channelCategory = guild.getCategories().
                    stream().
                    filter(category ->
                            category.getChannels().
                                    stream().
                                    anyMatch(channel ->
                                            channel.getIdLong() == permissionOverride.getChannel().getIdLong())).
                    findFirst().
                    orElse(null);

            return new ChannelPermissionEntry(
                    permissionOverride.getChannel().getName(),
                    permissionOverride.getChannel().getIdLong(),
                    channelCategory == null ? null : channelCategory.getName(),
                    permissionOverride.getIdLong(),
                    permissionOverride.getAllowed(),
                    permissionOverride.getDenied()
            );
        }).collect(Collectors.toList());
    }

    private List<PermissionOverride> getPermissionOverride(List<GuildChannel> channel) {
        return channel.stream().flatMap(voiceChannel -> voiceChannel.getPermissionOverrides().stream()).collect(Collectors.toList());
    }

    @Override
    public void loadBackup(String id, Runnable onFinish) throws BackupNotFoundException {
        if (!existBackup(id)) throw new BackupNotFoundException("There is no backup with id" + id, id);
        BackupEntry entry = getBackup(id);

        for (GuildChannel channel : guild.getChannels()) {
            channel.delete().queue();
        }

        for (ChannelPermissionEntry permissionEntry : entry.getTextChannel()) {
            Category category = guild.getCategoriesByName(permissionEntry.getCategoryName(), true).stream().findFirst().orElse(null);

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
