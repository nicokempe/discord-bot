package eu.nicokempe.discordbot;

import eu.nicokempe.discordbot.module.IModuleLoader;

public interface IDiscordBot {

    void enable();

    void disable();

    IModuleLoader getModuleLoader();

}
