package eu.nicokempe.discordbot.autochannel;

import eu.nicokempe.discordbot.channel.ChannelEntry;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.List;
import java.util.function.Consumer;

public interface IAutoChannelObject {

    void loadAutoChannel();

    boolean isAutoChannel(long id);

    boolean isCreatedChannel(long id);

    void createChannel(long id, Consumer<VoiceChannel> created);

    void leaveChannel(long id);

    ChannelEntry getAutoChannel(long id);

    List<ChannelEntry> getAutoChannel();

}
