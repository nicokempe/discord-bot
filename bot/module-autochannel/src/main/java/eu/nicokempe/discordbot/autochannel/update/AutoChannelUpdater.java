package eu.nicokempe.discordbot.autochannel.update;

import eu.nicokempe.discordbot.autochannel.AutoChannel;

public class AutoChannelUpdater implements Runnable {
    @Override
    public void run() {
        AutoChannel.INSTANCE.getAutoChannelObject().loadAutoChannel();
    }
}
