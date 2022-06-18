package eu.nicokempe.discordbot.backup;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.ChannelType;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class BackupChannel {

    private final String id;
    private final ChannelType channelType;
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
