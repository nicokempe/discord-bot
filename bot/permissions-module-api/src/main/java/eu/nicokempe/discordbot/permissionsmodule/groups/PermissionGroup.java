package eu.nicokempe.discordbot.permissionsmodule.groups;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class PermissionGroup {

    private final String id;
    private final String name;
    private final int color;
    private final List<GroupMember> member = new ArrayList<>();

    public long getIdLong(){
        return Long.parseLong(id);
    }

}
