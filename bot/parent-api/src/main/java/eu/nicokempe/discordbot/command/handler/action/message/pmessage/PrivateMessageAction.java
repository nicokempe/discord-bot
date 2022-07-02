package eu.nicokempe.discordbot.command.handler.action.message.pmessage;

import eu.nicokempe.discordbot.command.handler.action.message.Message;
import eu.nicokempe.discordbot.command.handler.action.message.MessageAction;

import java.util.List;

public class PrivateMessageAction extends MessageAction {

    public PrivateMessageAction(List<Message> messages) {
        super(messages);
    }
}
