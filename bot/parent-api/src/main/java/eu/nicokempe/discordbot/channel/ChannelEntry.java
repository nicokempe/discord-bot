package eu.nicokempe.discordbot.channel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.api.entities.ChannelType;

@AllArgsConstructor
@Getter
public class ChannelEntry {

    private String id;
    private String name;
    private int position;
    private ChannelType channelType;
    private boolean autoChannel = false;

    public long getIdLong() {
        return Long.parseLong(id);
    }

}
