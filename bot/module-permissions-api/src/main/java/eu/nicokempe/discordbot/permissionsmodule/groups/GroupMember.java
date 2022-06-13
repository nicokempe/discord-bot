package eu.nicokempe.discordbot.permissionsmodule.groups;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Member;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class GroupMember {

    private final String id;
    private long timestamp = System.currentTimeMillis();

    public long getIdLong() {
        return Long.parseLong(id);
    }

}
