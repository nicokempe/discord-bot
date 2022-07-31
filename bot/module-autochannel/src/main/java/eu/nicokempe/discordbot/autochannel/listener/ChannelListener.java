package eu.nicokempe.discordbot.autochannel.listener;

import eu.nicokempe.discordbot.autochannel.AutoChannel;
import eu.nicokempe.discordbot.autochannel.IAutoChannelObject;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ChannelListener extends ListenerAdapter {

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
        VoiceChannel newChannel = event.getChannelJoined();
        IAutoChannelObject autoChannelObject = AutoChannel.INSTANCE.getAutoChannelObject();

        if (autoChannelObject.isAutoChannel(newChannel.getIdLong()))
            autoChannelObject.createChannel(newChannel.getIdLong(), voiceChannel -> {
                AutoChannel.INSTANCE.getDiscordBot().getGuild().moveVoiceMember(event.getMember(), voiceChannel).queue();
            });

    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        VoiceChannel oldChannel = event.getChannelLeft();
        IAutoChannelObject autoChannelObject = AutoChannel.INSTANCE.getAutoChannelObject();

        if (autoChannelObject.isAutoChannel(oldChannel.getIdLong()) && oldChannel.getMembers().isEmpty()) {
            autoChannelObject.leaveChannel(oldChannel.getIdLong());
        }
    }
}
