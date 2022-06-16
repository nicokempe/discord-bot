package eu.nicokempe.discordbot.backup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.api.Permission;

import java.util.EnumSet;

@Getter
@AllArgsConstructor
public class ChannelPermissionEntry {

    private final String name;
    private final long channelId;
    private final String categoryName;
    private final long overrideId;
    private final EnumSet<Permission> allowed;
    private final EnumSet<Permission> denied;

}