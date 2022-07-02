package eu.nicokempe.discordbot.listener;

import eu.nicokempe.discordbot.DiscordBot;
import eu.nicokempe.discordbot.user.IDiscordUser;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        IDiscordUser discordUser = DiscordBot.INSTANCE.getUser(event.getMessage().getAuthor().getIdLong());
        if (discordUser.getRequestAnswer() != null) {
            discordUser.getRequestAnswer().accept(event.getMessage().getContentRaw());
        }
    }

}