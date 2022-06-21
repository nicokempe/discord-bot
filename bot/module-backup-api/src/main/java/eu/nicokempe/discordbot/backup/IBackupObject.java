package eu.nicokempe.discordbot.backup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public interface IBackupObject {

    void load();

    void update();

    void saveBackup(long creator, Consumer<BackupEntry> onFinish);

    void loadBackup(String id, Runnable onFinish) throws BackupNotFoundException;

    void deleteBackup(String id) throws BackupNotFoundException;

    boolean existBackup(String id);

    BackupEntry getBackup(String id);

    List<BackupEntry> getBackups();

    BackupEntry getLastBackup() throws BackupNotFoundException;

    BackupEntry getFirstBackup() throws BackupNotFoundException;

    long getBackupInterval();

    void setBackupInterval(long interval);

    @Getter
    @Setter
    @AllArgsConstructor
    class BackupLoadObject {
        private List<BackupEntry> entries;
        private long backupInterval = -1;
        private boolean createNew;
    }

}
