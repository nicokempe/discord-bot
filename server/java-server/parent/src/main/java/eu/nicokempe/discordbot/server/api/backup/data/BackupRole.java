package eu.nicokempe.discordbot.server.api.backup.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class BackupRole {

    private final String id;
    private final String name;
    private final int color;
    private final boolean mentionable, hoisted;
    private final String roleIcon;
    private final List<String> member;
    private final List<String> permissions;

}
