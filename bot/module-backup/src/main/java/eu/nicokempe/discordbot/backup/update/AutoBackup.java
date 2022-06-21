package eu.nicokempe.discordbot.backup.update;

import eu.nicokempe.discordbot.backup.Backup;
import lombok.SneakyThrows;

public class AutoBackup implements Runnable {

    public AutoBackup() {

    }

    @SneakyThrows
    @Override
    public void run() {
        long backupInterval = Backup.INSTANCE.getBackupObject().getBackupInterval();
        if (backupInterval == -1) return;
        if (Backup.INSTANCE.getBackupObject().getLastBackup().getTimestamp() + backupInterval < System.currentTimeMillis()) {
            Backup.INSTANCE.getBackupObject().saveBackup(Backup.INSTANCE.getDiscordBot().getGuild().getSelfMember().getIdLong(), backupEntry -> {

            });
        }
    }
}
