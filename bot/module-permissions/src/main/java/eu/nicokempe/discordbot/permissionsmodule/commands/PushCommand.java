package eu.nicokempe.discordbot.permissionsmodule.commands;

import eu.nicokempe.discordbot.command.AbstractCommand;
import eu.nicokempe.discordbot.user.IDiscordUser;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class PushCommand extends AbstractCommand {

    public PushCommand(String name) {
        super(name, "Push roles to rest");
    }

    @Override
    public void onExecute(IDiscordUser user, SlashCommandEvent event) {

    }
}
