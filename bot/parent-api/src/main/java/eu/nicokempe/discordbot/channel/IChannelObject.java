package eu.nicokempe.discordbot.channel;

import java.util.List;

public interface IChannelObject {

    ChannelEntry getChannelById(String id);

    List<ChannelEntry> getEntries();

    void loadChannel();

    void update();

}
