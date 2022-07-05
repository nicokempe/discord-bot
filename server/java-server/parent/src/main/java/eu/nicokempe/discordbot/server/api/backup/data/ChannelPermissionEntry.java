package eu.nicokempe.discordbot.server.api.backup.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ChannelPermissionEntry {

    private final int typ;
    private final String id;
    private final List<String> allowed;
    private final List<String> denied;

    /*private final String name;
    private final String channelId;
    private final String overrideId;
    private final EnumSet<Permission> allowed;
    private final EnumSet<Permission> denied;*/

}