package eu.nicokempe.discordbot.autochannel;

import eu.nicokempe.discordbot.IDiscordBot;
import eu.nicokempe.discordbot.channel.ChannelEntry;
import lombok.Getter;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class AutoChannelObject implements IAutoChannelObject {

    private final List<ChannelEntry> autoChannel = new ArrayList<>();

    @Override
    public void loadAutoChannel() {
        autoChannel.addAll(IDiscordBot.CHANNEL.stream().filter(ChannelEntry::isAutoChannel).toList());
    }

    @Override
    public boolean isAutoChannel(long id) {
        return getAutoChannel(id) != null;
    }

    @Override
    public void createChannel(long id, Consumer<VoiceChannel> created) {
        ChannelEntry autoChannel = getAutoChannel(id);
        AutoChannel.INSTANCE.getDiscordBot().getGuild().createVoiceChannel(autoChannel.getName() + "A").queue(voiceChannel -> {


            created.accept(voiceChannel);
        });
    }

    @Override
    public void leaveChannel(long id) {
        VoiceChannel channel = AutoChannel.INSTANCE.getDiscordBot().getGuild().getVoiceChannelById(id);
        if (channel == null) return;
        channel.delete().queue();
    }

    @Override
    public ChannelEntry getAutoChannel(long id) {
        return autoChannel.stream().filter(channelEntry -> channelEntry.getIdLong() == id).findFirst().orElse(null);
    }

}
