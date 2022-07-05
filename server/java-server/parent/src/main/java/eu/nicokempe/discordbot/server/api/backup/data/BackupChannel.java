package eu.nicokempe.discordbot.server.api.backup.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class BackupChannel {

    private final String id;
    private final String channelType;
    private final String name;
    private final String description;
    private final String category;
    private final int position;
    private final boolean nsfw, news;
    private final int userLimit, slowMode;
    private final List<ChannelPermissionEntry> permissionEntries;

    public long getIdLong() {
        return Long.parseLong(id);
    }

}
