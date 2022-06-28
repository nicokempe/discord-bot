package eu.nicokempe.discordbot.command.handler.action.message;

import eu.nicokempe.discordbot.command.handler.action.CommandAction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MessageAction extends CommandAction {

    private final List<Message> messages;

}
