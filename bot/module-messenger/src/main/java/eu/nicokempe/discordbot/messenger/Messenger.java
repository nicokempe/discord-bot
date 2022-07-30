package eu.nicokempe.discordbot.messenger;

import eu.nicokempe.discordbot.IDiscordBot;
import eu.nicokempe.discordbot.messenger.task.MessageTask;
import eu.nicokempe.discordbot.module.ModuleInterface;
import lombok.Getter;

@Getter
public class Messenger extends ModuleInterface {

    public static Messenger INSTANCE;

    private IDiscordBot.AuthKey authKey;

    public Messenger() {
        INSTANCE = this;
    }

    @Override
    public void enable() {
        authenticate("tylix", "123456", newKey -> {
            this.authKey = newKey;

            getDiscordBot().getUpdateTask().addTask(new MessageTask());
        });
    }

    @Override
    public void disable() {

    }
}
