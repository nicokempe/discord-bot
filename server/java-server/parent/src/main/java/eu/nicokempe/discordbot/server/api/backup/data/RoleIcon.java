package eu.nicokempe.discordbot.server.api.backup.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RoleIcon {
    private final String iconId;
    private final String emoji;
    private final long roleId;
}