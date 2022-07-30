package eu.nicokempe.discordbot.log.type;

import net.dv8tion.jda.api.entities.ChannelType;

public class CommandType extends LogType {

    private final String command;
    private final ChannelType channelType;
    private final String channel;

    public CommandType(String command, ChannelType channelType, String channel) {
        super(CommandType.class.getName());
        this.command = command;
        this.channelType = channelType;
        this.channel = channel;
    }
}
