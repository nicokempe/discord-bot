package eu.nicokempe.discordbot.log.type;

import lombok.Getter;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageType;

@Getter
public class UserMessageType extends LogType {

    private final String message;
    private final ChannelType channelType;
    private final String channel;

    public UserMessageType(String message, ChannelType channelType, String channel) {
        super(UserMessageType.class.getName());
        this.message = message;
        this.channelType = channelType;
        this.channel = channel;
    }
}
