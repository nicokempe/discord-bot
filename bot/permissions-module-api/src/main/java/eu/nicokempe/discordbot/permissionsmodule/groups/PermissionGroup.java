package eu.nicokempe.discordbot.permissionsmodule.groups;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PermissionGroup {

    private String id;
    private final String name;
    private final int color;
    private final List<GroupMember> member = new ArrayList<>();

    public long getIdLong(){
        return Long.parseLong(id);
    }

}
