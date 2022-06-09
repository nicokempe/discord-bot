package eu.nicokempe.discordbot.module;

import eu.nicokempe.discordbot.IDiscordBot;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Properties;

@Getter
@Setter
public abstract class ModuleInterface {

    private IDiscordBot discordBot;
    private Properties properties;

    public abstract void enable();

    public abstract void disable();

}
