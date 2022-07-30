package eu.nicokempe.discordbot.command.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import eu.nicokempe.discordbot.IDiscordBot;
import eu.nicokempe.discordbot.command.handler.action.CommandAction;
import eu.nicokempe.discordbot.user.IDiscordUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class CustomCommand {

    private final String name;
    private final String description;
    private final String channel;
    private final List<String> permissionGroups = new ArrayList<>();
    private final List<OptionData> optionData = new ArrayList<>();

    private final String commandAction;

    public long getChannelLong() {
        return Long.parseLong(channel);
    }

    @SneakyThrows
    public CommandAction getCommandAction() {
        if (commandAction == null) return null;
        return IDiscordBot.GSON.fromJson(commandAction.replace("\n", ""), CommandAction.class);
    }

}
