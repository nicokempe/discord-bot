package eu.nicokempe.discordbot.command.handler;

import eu.nicokempe.discordbot.command.AbstractCommand;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.util.List;

public interface ICommandManager {

    void loadCommands();

    void addCommand(AbstractCommand command);

    void loadCustomCommands();

    List<CustomCommand> getCustomCommands();

    AbstractCommand getCommandByName(String name);

    AbstractCommand getCommandByButtonId(String id);

}
