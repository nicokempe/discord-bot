package eu.nicokempe.discordbot.backup;

import eu.nicokempe.discordbot.backup.commands.BackupCommand;
import eu.nicokempe.discordbot.module.ModuleInterface;
import lombok.Getter;

@Getter
public class Backup extends ModuleInterface {

    public static Backup INSTANCE;

    public Backup() {
        INSTANCE = this;
    }

    private IBackupObject backupObject;

    @Override
    public void enable() {
        backupObject = new BackupObject();
        backupObject.load();

        getDiscordBot().getCommandManager().addCommand(new BackupCommand("backup", "Saved backups"));
    }

    @Override
    public void disable() {

    }
}
