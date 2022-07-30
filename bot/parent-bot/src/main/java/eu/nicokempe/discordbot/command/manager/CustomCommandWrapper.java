package eu.nicokempe.discordbot.command.manager;

import eu.nicokempe.discordbot.command.AbstractCommand;
import eu.nicokempe.discordbot.command.handler.CustomCommand;
import eu.nicokempe.discordbot.command.handler.Placeholder;
import eu.nicokempe.discordbot.command.handler.action.message.EmbedMessage;
import eu.nicokempe.discordbot.command.handler.action.message.Footer;
import eu.nicokempe.discordbot.command.handler.action.message.Message;
import eu.nicokempe.discordbot.command.handler.action.message.MessageAction;
import eu.nicokempe.discordbot.command.handler.action.message.pmessage.PrivateMessageAction;
import eu.nicokempe.discordbot.user.IDiscordUser;
import lombok.NonNull;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.util.stream.Collectors;

public class CustomCommandWrapper extends AbstractCommand {

    private final CustomCommand customCommand;

    public CustomCommandWrapper(@NonNull CustomCommand customCommand) {
        super(customCommand.getName(), customCommand.getDescription());

        this.customCommand = customCommand;

        setChannel(customCommand.getChannelLong());
        setPermissionGroups(customCommand.getPermissionGroups().stream().map(Long::parseLong).collect(Collectors.toList()));
    }

    @Override
    public void onExecute(IDiscordUser user, SlashCommandEvent event) {
        if (customCommand.getCommandAction() == null) throw new NullPointerException("No command action defined.");
        if (customCommand.getCommandAction() instanceof MessageAction messageAction) {
            for (Message message : messageAction.getMessages()) {
                if (message.getEmbedBuilder() == null) {
                    String messageString = message.getMessage();
                    messageString = replace(messageString, new Placeholder("%user.nickname%", user.getNickname()), new Placeholder("%user.name%", user.getName()), new Placeholder("%user.avatar%", user.getUser().getAvatarUrl() == null ? "null" : user.getUser().getAvatarUrl()));

                    if (messageAction instanceof PrivateMessageAction)
                        user.sendPrivateMessage(messageString);
                    else
                        event.reply(messageString).setEphemeral(message.isEphemeral()).queue();
                } else {
                    EmbedMessage embedMessage = message.getEmbedBuilder();

                    String title = embedMessage.getTitle();
                    String description = message.getMessage();
                    Footer footer = embedMessage.getFooter();
                    if (title != null)
                        title = replace(title, new Placeholder("%user.nickname%", user.getNickname()), new Placeholder("%user.name%", user.getName()), new Placeholder("%user.avatar%", user.getUser().getAvatarUrl() == null ? "null" : user.getUser().getAvatarUrl()));
                    if (description != null)
                        description = replace(description, new Placeholder("%user.nickname%", user.getNickname()), new Placeholder("%user.name%", user.getName()), new Placeholder("%user.avatar%", user.getUser().getAvatarUrl() == null ? "null" : user.getUser().getAvatarUrl()));
                    if (footer != null) {
                        String text = replace(footer.getText(), new Placeholder("%user.nickname%", user.getNickname()), new Placeholder("%user.name%", user.getName()), new Placeholder("%user.avatar%", user.getUser().getAvatarUrl() == null ? "null" : user.getUser().getAvatarUrl()));
                        String avatarUrl = replace(footer.getAvatarUrl(), new Placeholder("%user.avatar%", user.getUser().getAvatarUrl() == null ? null : user.getUser().getAvatarUrl()));
                        footer = new Footer(text, avatarUrl);
                    }

                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setTitle(title);
                    embedBuilder.setDescription(description);
                    if (footer != null)
                        embedBuilder.setFooter(footer.getText(), footer.getAvatarUrl());
                    embedBuilder.setColor(embedMessage.getColor());

                    if (messageAction instanceof PrivateMessageAction)
                        user.sendPrivateMessage(embedBuilder.build());
                    else
                        event.replyEmbeds(embedBuilder.build()).setEphemeral(message.isEphemeral()).queue();

                }
            }
        }
    }

    private String replace(String message, Placeholder... placeholders) {
        String toReturn = message;
        for (Placeholder placeholder : placeholders) {
            toReturn = toReturn.replace(placeholder.target(), placeholder.replacement().toString());
        }
        return toReturn;
    }
}
