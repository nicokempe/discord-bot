package eu.nicokempe.discordbot.command.manager;

import com.google.gson.JsonElement;
import eu.nicokempe.discordbot.DiscordBot;
import eu.nicokempe.discordbot.IDiscordBot;
import eu.nicokempe.discordbot.command.AbstractCommand;
import eu.nicokempe.discordbot.command.handler.CustomCommand;
import eu.nicokempe.discordbot.command.handler.ICommandManager;
import eu.nicokempe.discordbot.command.handler.action.message.EmbedMessage;
import eu.nicokempe.discordbot.command.handler.action.message.Footer;
import eu.nicokempe.discordbot.command.handler.action.message.Message;
import eu.nicokempe.discordbot.command.handler.action.message.MessageAction;
import eu.nicokempe.discordbot.request.RequestBuilder;
import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class CommandManager implements ICommandManager {

    private final List<AbstractCommand> queue = new ArrayList<>();
    private final Map<String, AbstractCommand> commands = new HashMap<>();
    private final List<CustomCommand> customCommands = new ArrayList<>();

    @Override
    public void loadCommands() {
        loadCustomCommands();
        if (queue.isEmpty()) {
            CustomCommand command = new CustomCommand("test", "Test Commmand", "-1", new MessageAction(
                    List.of(
                            new Message(
                                    "Hallo %user.nickname%",
                                    true,
                                    new EmbedMessage(
                                            536870911,
                                            null,
                                            new Footer("Sent from %user.name%", "%user.avatar%")
                                    )
                            )
                    )
            ));
            System.out.println(IDiscordBot.GSON.toJson(command));
            return;
        }
        CommandListUpdateAction commands = DiscordBot.INSTANCE.getGuild().updateCommands();
        for (AbstractCommand abstractCommand : queue) {
            commands.addCommands(toData(abstractCommand));
        }
        commands.queue();
        System.out.println(MessageFormat.format("{0} command(s) were successfully loaded!", queue.size()));
    }

    @Override
    public void loadCustomCommands() {
        for (JsonElement element : RequestBuilder.builder().route("commands").authKey(DiscordBot.INSTANCE.getAuthKey()).build().get().getAsJsonArray()) {
            CustomCommand customCommand = IDiscordBot.GSON.fromJson(element.getAsJsonObject().toString(), CustomCommand.class);
            AbstractCommand command = new CustomCommandWrapper(customCommand);
            addCommand(command);
        }
    }

    private CommandData toData(AbstractCommand command) {
        commands.put(command.getName(), command);
        if (command.getAliases() != null)
            for (String alias : command.getAliases())
                commands.put(alias, command);

        CommandData commandData = new CommandData(command.getName(), command.getDescription());
        commandData.addOptions(command.getOptionData());
        return commandData;
    }

    @Override
    public void addCommand(AbstractCommand command) {
        queue.add(command);
    }

    @Override
    public AbstractCommand getCommandByName(String name) {
        return commands.getOrDefault(name, null);
    }

    @Override
    public AbstractCommand getCommandByButtonId(String id) {
        return commands.values().stream().filter(abstractCommand -> abstractCommand.getBtnId().equals(id)).findFirst().orElse(null);
    }
}
