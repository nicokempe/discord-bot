package eu.nicokempe.discordbot.log.type;

import eu.nicokempe.discordbot.IDiscordBot;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public abstract class LogType {

    private final String _class;

}
