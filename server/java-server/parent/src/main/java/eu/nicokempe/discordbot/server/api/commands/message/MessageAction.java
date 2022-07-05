package eu.nicokempe.discordbot.server.api.commands.message;

import eu.nicokempe.discordbot.server.api.commands.CommandAction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MessageAction extends CommandAction {

    private final List<Message> messages;

}
