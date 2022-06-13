package eu.nicokempe.discordbot.messenger;

import eu.nicokempe.discordbot.messenger.task.MessageTask;
import eu.nicokempe.discordbot.module.ModuleInterface;
import lombok.Getter;

@Getter
public class Messenger extends ModuleInterface {

    public static Messenger INSTANCE;

    private MessageTask messageTask;

    public Messenger() {
        INSTANCE = this;
    }

    @Override
    public void enable() {
        messageTask = new MessageTask();
        messageTask.start();
    }

    @Override
    public void disable() {

    }
}
