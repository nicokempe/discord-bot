package eu.nicokempe.discordbot.backup;

import eu.nicokempe.discordbot.IDiscordBot;
import eu.nicokempe.discordbot.backup.commands.BackupCommand;
import eu.nicokempe.discordbot.backup.update.BackupUpdater;
import eu.nicokempe.discordbot.module.ModuleInterface;
import lombok.Getter;

@Getter
public class Backup extends ModuleInterface {

    public static Backup INSTANCE;

    private IDiscordBot.AuthKey authKey;

    public Backup() {
        INSTANCE = this;
    }

    private IBackupObject backupObject;

    @Override
    public void enable() {
        authenticate("tylix", "123456", newKey -> {
            this.authKey = newKey;

            backupObject = new BackupObject();
            backupObject.load();

            getDiscordBot().getUpdateTask().addTask(new BackupUpdater());

            getDiscordBot().getCommandManager().addCommand(new BackupCommand("backup", "Saved backups"));
        });
    }

    @Override
    public void disable() {

    }
}
