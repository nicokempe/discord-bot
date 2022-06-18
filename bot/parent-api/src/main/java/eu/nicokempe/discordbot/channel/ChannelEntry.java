package eu.nicokempe.discordbot.channel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.api.entities.ChannelType;

@AllArgsConstructor
@Getter
public class ChannelEntry {

    private final String id;
    private final String name;
    private final int position;
    private final ChannelType channelType;

    public long getIdLong() {
        return Long.parseLong(id);
    }

}
