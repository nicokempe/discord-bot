package eu.nicokempe.discordbot.backup.commands;

import eu.nicokempe.discordbot.backup.*;
import eu.nicokempe.discordbot.command.AbstractCommand;
import eu.nicokempe.discordbot.user.IDiscordUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyAction;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BackupCommand extends AbstractCommand {

    private final IBackupObject backupObject;

    public BackupCommand(String name, String description) {
        super(name, description);
        addOption(new OptionData(OptionType.STRING, "type", "Type", true).
                        addChoice("save", "save").
                        addChoice("load", "load").
                        addChoice("list", "list").
                        addChoice("delete", "delete").
                        addChoice("interval", "interval"),
                new OptionData(OptionType.STRING, "id", "id of backup", false),
                new OptionData(OptionType.INTEGER, "interval", "interval of auto-backup", false));
        setAliases("bu");

        backupObject = Backup.INSTANCE.getBackupObject();
    }

    @Override
    public void onExecute(IDiscordUser user, SlashCommandEvent event) {
        String type = event.getOption("type").getAsString();

        switch (type.toLowerCase()) {
            case "save" -> {
                saveBackup(event);
            }
            case "load" -> {
                if (event.getOption("id") == null) {

                    return;
                }
                String id = event.getOption("id").getAsString();
                if (!backupObject.existBackup(id)) {
                    event.replyEmbeds(new EmbedBuilder().setColor(Color.red).setDescription("This backup does not exist!").build()).setEphemeral(true).queue();
                    return;
                }
                event.replyEmbeds(new EmbedBuilder().setColor(Color.cyan).setDescription("You sure to load this backup?").build()).setEphemeral(true).addActionRow(Button.danger(buttonName("confirm_" + id), "Confirm")).queue();
            }
            case "list" -> {
                EmbedBuilder embedBuilder = new EmbedBuilder().setColor(Color.cyan);
                embedBuilder.setTitle("List of backups");
                List<BackupEntry> backups = backupObject.getBackups();
                for (int i = 0; i < backups.size(); i++) {
                    BackupEntry backup = backups.get(i);
                    embedBuilder.addField(backup.getId() + "  Nr. " + (i + 1), "``" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(backup.getTimestamp()) + "``", true);
                }
                if (backups.isEmpty())
                    embedBuilder.setDescription("No backups present.");
                ReplyAction action = event.replyEmbeds(embedBuilder.build()).setEphemeral(true).addActionRow(Button.danger(buttonName("deleteAll"), "Delete all").withDisabled(/*!user.getPermissionObject().hasDiscordRank(DiscordBot.INSTANCE.getGroupLoader().getDiscordId(PermissionGroupLoader.STAR)) || */backups.isEmpty()), Button.success(buttonName("create"), "Create one"));
                action.queue();
            }
            case "delete" -> {
                if (event.getOption("id") == null) {

                    return;
                }
                String id = event.getOption("id").getAsString();
                deleteBackup(id, event);
            }
            case "interval" -> {
                if (event.getOption("interval") == null) {

                    return;
                }
                long interval = event.getOption("interval").getAsLong();
                backupObject.setBackupInterval(interval);
                event.replyEmbeds(new EmbedBuilder().setDescription("Set backup interval to " + TimeUnit.MINUTES.toHours(interval) + "h " + interval + "m").setColor(Color.cyan).build()).setEphemeral(true).queue();
            }
        }
    }

    private void saveBackup(GenericInteractionCreateEvent event) {
        backupObject.saveBackup(event.getUser().getIdLong(), entry -> {
            event.replyEmbeds(new EmbedBuilder().setDescription("Created backup ``" + entry.getId() + "``").setColor(Color.cyan).setFooter("Timestamp: " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(entry.getTimestamp())).build()).setEphemeral(true).queue();
        });
    }

    private void deleteBackup(String id, GenericInteractionCreateEvent event) {
        try {
            backupObject.deleteBackup(id);
            event.replyEmbeds(new EmbedBuilder().setColor(Color.red).setDescription("Deleted backup " + id).build()).setEphemeral(true).queue();
        } catch (BackupNotFoundException e) {
            event.replyEmbeds(new EmbedBuilder().setColor(Color.red).setDescription("This backup does not exist!").build()).setEphemeral(true).queue();
        }
    }

    @Override
    public void onButtonClick(IDiscordUser user, ButtonClickEvent clickEvent, String name) {
        switch (name) {
            case "delete" -> {
                String id = clickEvent.getComponentId().split("_")[2];
                deleteBackup(id, clickEvent);
            }
            case "confirm" -> {
                String id = clickEvent.getComponentId().split("_")[2];
                try {
                    backupObject.loadBackup(id, () -> {
                        clickEvent.replyEmbeds(new EmbedBuilder().setColor(Color.green).setDescription("loaded backup " + id).build()).setEphemeral(true).addActionRow(Button.danger(buttonName("delete_" + id), "Delete Backup")).queue();
                    });
                } catch (BackupNotFoundException e) {
                    clickEvent.replyEmbeds(new EmbedBuilder().setColor(Color.red).setDescription("This backup does not exist!").build()).setEphemeral(true).queue();
                }
            }
            case "deleteAll" -> {
                EmbedBuilder embedBuilder = new EmbedBuilder().setColor(Color.red).setTitle("Deleted all Backups.");
                List<BackupEntry> backups = new ArrayList<>(backupObject.getBackups());
                for (int i = 0; i < backups.size(); i++) {
                    BackupEntry backup = backups.get(i);
                    try {
                        embedBuilder.addField("Backup " + (i + 1), backup.getId(), true);
                        backupObject.deleteBackup(backup.getId());
                    } catch (BackupNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                clickEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            }
            case "create" -> saveBackup(clickEvent);
        }
    }
}
