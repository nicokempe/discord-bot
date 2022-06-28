package eu.nicokempe.discordbot.command.handler;

import eu.nicokempe.discordbot.command.handler.action.CommandAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
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

    private final CommandAction commandAction;

    public long getChannelLong() {
        return Long.parseLong(channel);
    }

}
