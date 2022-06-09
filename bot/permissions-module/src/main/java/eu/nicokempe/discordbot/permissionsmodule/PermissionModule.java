package eu.nicokempe.discordbot.permissionsmodule;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import eu.nicokempe.discordbot.module.ModuleInterface;
import eu.nicokempe.discordbot.permissionsmodule.groups.GroupMember;
import eu.nicokempe.discordbot.permissionsmodule.groups.PermissionGroup;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import okhttp3.FormBody;

import java.util.ArrayList;
import java.util.List;

public class PermissionModule extends ModuleInterface {

    private final List<PermissionGroup> groups = new ArrayList<>();

    @Override
    public void enable() {
        for (JsonElement roles : getDiscordBot().get("roles").getAsJsonArray()) {
            JsonObject jsonObject = roles.getAsJsonObject();
            String id = jsonObject.get("id").getAsString();
            String name = jsonObject.get("name").getAsString();
            int color = jsonObject.get("color").getAsInt();

            PermissionGroup group = new PermissionGroup(id, name, color);

            for (JsonElement member : jsonObject.get("member").getAsJsonArray()) {
                JsonObject memberObject = member.getAsJsonObject();
                String memberId = memberObject.get("id").getAsString();
                long timestamp = memberObject.get("timestamp").getAsLong();
                GroupMember groupMember = new GroupMember(memberId, timestamp);
                group.getMember().add(groupMember);
            }

            groups.add(group);
        }

        loadMember();

        System.out.println(new Gson().toJson(groups));
    }

    private void loadMember() {
        for (PermissionGroup group : groups) {
            for (GroupMember groupMember : group.getMember()) {
                Role role = getDiscordBot().getGuild().getRoleById(group.getId());
                if (role == null) {
                    role = getDiscordBot().getGuild().createRole().setName(group.getName()).setColor(group.getColor()).complete();
                }
                getDiscordBot().getGuild().addRoleToMember(groupMember.getId(), role).queue();
            }
        }
    }

    @Override
    public void disable() {

    }
}
