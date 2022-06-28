package eu.nicokempe.discordbot.command.handler.action.message.pmessage;

import eu.nicokempe.discordbot.command.handler.action.message.Message;
import eu.nicokempe.discordbot.command.handler.action.message.MessageAction;

import java.util.List;

public class PrivateMessage extends MessageAction {

    public PrivateMessage(List<Message> messages) {
        super(messages);
    }
}
