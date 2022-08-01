package eu.nicokempe.discordbot.channel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.ChannelType;

@AllArgsConstructor
@Getter
@Setter
public class ChannelEntry {

    private String id;
    private String name;
    private int position;
    private ChannelType channelType;
    private boolean autoChannel = false;
    private boolean enabled = false;

    public long getIdLong() {
        return Long.parseLong(id);
    }

}
