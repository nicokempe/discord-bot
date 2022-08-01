package eu.nicokempe.discordbot.autochannel;

import eu.nicokempe.discordbot.autochannel.listener.ChannelListener;
import eu.nicokempe.discordbot.autochannel.update.AutoChannelUpdater;
import eu.nicokempe.discordbot.module.ModuleInterface;
import lombok.Getter;

@Getter
public class AutoChannel extends ModuleInterface {

    public static AutoChannel INSTANCE;

    private IAutoChannelObject autoChannelObject;

    public AutoChannel() {
        INSTANCE = this;
    }

    @Override
    public void enable() {
        this.autoChannelObject = new AutoChannelObject();
        this.autoChannelObject.loadAutoChannel();

        getDiscordBot().getUpdateTask().addTask(new AutoChannelUpdater());

        getDiscordBot().getJda().addEventListener(new ChannelListener());
    }

    @Override
    public void disable() {

    }
}
