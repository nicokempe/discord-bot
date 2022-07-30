package eu.nicokempe.discordbot.listener;

import eu.nicokempe.discordbot.DiscordBot;
import eu.nicokempe.discordbot.log.data.LogEntry;
import eu.nicokempe.discordbot.log.type.UserMessageType;
import eu.nicokempe.discordbot.user.IDiscordUser;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        IDiscordUser discordUser = DiscordBot.INSTANCE.getUser(event.getMessage().getAuthor().getIdLong());

        DiscordBot.INSTANCE.getLogObject().saveLog(new LogEntry(event.getMessage().getAuthor().getId()).logType(new UserMessageType(event.getMessage().getContentRaw(), event.getChannelType(), event.getChannel().getId())));
        DiscordBot.INSTANCE.getLogObject().update();

        if (discordUser.getRequestAnswer() != null) {
            discordUser.getRequestAnswer().accept(event.getMessage().getContentRaw());
        }
    }

}