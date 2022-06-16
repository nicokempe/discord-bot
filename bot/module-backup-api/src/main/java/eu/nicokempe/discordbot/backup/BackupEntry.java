package eu.nicokempe.discordbot.backup;

import eu.nicokempe.discordbot.IDiscordBot;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class BackupEntry {

    private final String id = IDiscordBot.generateString(10);
    private final long timestamp = System.currentTimeMillis();
    private final String creator;
    private final List<BackupChannel> channels = new ArrayList<>();

    /*private final String id = IDiscordBot.generateString(8);
    private final long timestamp = System.currentTimeMillis();
    private final List<ChannelPermissionEntry> textChannel = new ArrayList<>();
    private final List<ChannelPermissionEntry> voiceChannel = new ArrayList<>();
    private final List<ChannelPermissionEntry> categories = new ArrayList<>();

    public List<ChannelPermissionEntry> getAllEntries() {
        List<ChannelPermissionEntry> toReturn = new ArrayList<>();
        toReturn.addAll(textChannel);
        toReturn.addAll(voiceChannel);
        toReturn.addAll(categories);
        return toReturn;
    }*/

}