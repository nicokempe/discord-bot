package eu.nicokempe.discordbot.command;

import eu.nicokempe.discordbot.IDiscordBot;
import eu.nicokempe.discordbot.user.IDiscordUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public abstract class AbstractCommand {

    private final String name;
    private final String description;
    private long channel = -1;
    private final List<OptionData> optionData = new ArrayList<>();
    private final List<String> aliases = new ArrayList<>();
    private final List<Long> permissionGroups = new ArrayList<>();
    private final String btnId = IDiscordBot.generateString(10);

    public abstract void onExecute(IDiscordUser user, SlashCommandEvent event);

    public void onButtonClick(IDiscordUser user, ButtonClickEvent clickEvent, String name) {

    }

    public void setAliases(String... aliases) {
        this.aliases.addAll(Arrays.asList(aliases));
    }

    public void setPermissionGroups(Long... permissionGroups) {
        this.permissionGroups.addAll(Arrays.asList(permissionGroups));
    }

    public void setPermissionGroups(List<Long> permissionGroups) {
        this.permissionGroups.addAll(permissionGroups);
    }

    public void addOption(OptionData... option) {
        optionData.addAll(List.of(option));
    }

    public String buttonName(String name) {
        return btnId + "_" + name;
    }

}