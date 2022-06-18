package eu.nicokempe.discordbot.messenger;

import eu.nicokempe.discordbot.messenger.task.MessageTask;
import eu.nicokempe.discordbot.module.ModuleInterface;
import lombok.Getter;

@Getter
public class Messenger extends ModuleInterface {

    public static Messenger INSTANCE;

    public Messenger() {
        INSTANCE = this;
    }

    @Override
    public void enable() {
        getDiscordBot().getUpdateTask().addTask(new MessageTask());
    }

    @Override
    public void disable() {

    }
}
