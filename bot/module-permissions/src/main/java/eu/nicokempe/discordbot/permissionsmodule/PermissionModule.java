package eu.nicokempe.discordbot.permissionsmodule;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import eu.nicokempe.discordbot.module.ModuleInterface;
import eu.nicokempe.discordbot.permissionsmodule.commands.PushCommand;
import eu.nicokempe.discordbot.permissionsmodule.groups.GroupMember;
import eu.nicokempe.discordbot.permissionsmodule.groups.PermissionGroup;
import eu.nicokempe.discordbot.request.RequestBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.exceptions.ContextException;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import okhttp3.FormBody;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class PermissionModule extends ModuleInterface {

    private final List<PermissionGroup> groups = new ArrayList<>();

    @Override
    public void enable() {
        getDiscordBot().getCommandManager().addCommand(new PushCommand("push"));

        for (JsonElement roles : RequestBuilder.builder().route("roles").authKey(getDiscordBot().getAuthKey()).build().get().getAsJsonArray()) {
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
            Role role = getDiscordBot().getGuild().getRoleById(group.getId());
            if (role == null) {
                //getDiscordBot().getGuild().createRole().setName(group.getName()).setColor(group.getColor()).queue(success -> group.setId(success.getId()));
            }

            groups.add(group);
        }

        loadMember();

    }

    private void loadMember() {
        for (PermissionGroup group : groups) {
            for (GroupMember groupMember : group.getMember()) {
                Role role = getDiscordBot().getGuild().getRoleById(group.getId());
                if (role == null)
                    return;
                addMember(role, groupMember.getId());
            }
        }
    }

    private void addMember(Role role, String memberId) {
        try {
            getDiscordBot().getGuild().addRoleToMember(memberId, role).queue();
        } catch (HierarchyException e) {
            System.out.println(MessageFormat.format("{0}'s role could not be changed. Please adjust the rights of the bot!", role.getName()));
        }
    }

    @Override
    public void disable() {

    }
}
