package eu.nicokempe.discordbot.listener;

import eu.nicokempe.discordbot.DiscordBot;
import eu.nicokempe.discordbot.command.AbstractCommand;
import eu.nicokempe.discordbot.log.data.LogEntry;
import eu.nicokempe.discordbot.log.type.CommandType;
import eu.nicokempe.discordbot.user.IDiscordUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Collection;
import java.util.List;

public class SlashListener extends ListenerAdapter {

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        AbstractCommand command = DiscordBot.INSTANCE.getCommandManager().getCommandByName(event.getName());
        if (command == null) return;
        DiscordBot.INSTANCE.getLogObject().saveLog(new LogEntry(event.getUser().getId()).logType(new CommandType(event.getCommandString(), event.getChannelType(), event.getChannel().getId())));
        DiscordBot.INSTANCE.getLogObject().update();
        if (command.getChannel() != -1 && command.getChannel() != event.getChannel().getIdLong()) {
            event.replyEmbeds(new EmbedBuilder().setColor(Color.red).setDescription("This command may only be executed in channel " + DiscordBot.INSTANCE.getGuild().getTextChannelById(command.getChannel()).getAsMention()).build()).setEphemeral(true).queue();
            return;
        }
        IDiscordUser user = DiscordBot.INSTANCE.getUser(event.getUser().getIdLong());
        if (noPermission(user, command)) {
            event.replyEmbeds(new EmbedBuilder().setColor(Color.red).setDescription("This command could not be found!").setFooter(user.getNickname(), user.getUser().getAvatarUrl()).build()).setEphemeral(true).queue();
            return;
        }
        command.onExecute(user, event);
    }

    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {
        if (event.getComponentId().split("_").length < 2) return;
        AbstractCommand command = DiscordBot.INSTANCE.getCommandManager().getCommandByButtonId(event.getComponentId().split("_")[0]);
        if (command == null) return;
        IDiscordUser user = DiscordBot.INSTANCE.getUser(event.getUser().getIdLong());
        if (noPermission(user, command)) {
            return;
        }
        command.onButtonClick(user, event, event.getComponentId().split("_")[1]);
    }

    private boolean noPermission(IDiscordUser user, AbstractCommand command) {
        Guild guild = DiscordBot.INSTANCE.getGuild();
        List<Long> commandGroups = command.getPermissionGroups();
        boolean hasGroup = false;
        Collection<Role> role = commandGroups.stream().map(guild::getRoleById).toList();
        List<Long> member = guild.getMembersWithRoles(role).stream().map(ISnowflake::getIdLong).toList();
        if (member.contains(user.getId()))
            hasGroup = true;
        return commandGroups.size() != 0 && !hasGroup;
    }
}
