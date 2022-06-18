package eu.nicokempe.discordbot.backup.update;

import eu.nicokempe.discordbot.backup.Backup;

public class BackupUpdater implements Runnable {
    @Override
    public void run() {
        Backup.INSTANCE.getBackupObject().load();
    }
}
