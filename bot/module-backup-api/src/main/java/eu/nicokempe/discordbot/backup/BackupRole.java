package eu.nicokempe.discordbot.backup;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.RoleIcon;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class BackupRole {

    private final String id;
    private final String name;
    private final int color;
    private final boolean mentionable, hoisted;
    private final RoleIcon roleIcon;
    private final List<String> member;
    private final EnumSet<Permission> permissions;

}
