package eu.nicokempe.discordbot.maintenance;

import eu.nicokempe.discordbot.backup.Backup;
import eu.nicokempe.discordbot.config.IConfigObject;
import eu.nicokempe.discordbot.maintenance.config.MaintenanceConfig;
import org.jetbrains.annotations.Nullable;

public class MaintenanceObject implements IMaintenanceObject {

    private IConfigObject configObject;

    @Override
    public void load() {
        configObject = Maintenance.INSTANCE.getDiscordBot().getNewConfigObject();
        configObject.load("maintenance");
    }

    @Override
    public void update() {
        configObject.update();
    }

    @Override
    public boolean isMaintenance() {
        return configObject.getValue(MaintenanceConfig.ENABLED);
    }

    @Override
    public void enableMaintenance(@Nullable String reason) {

        if (Maintenance.INSTANCE.getDiscordBot().getModuleLoader().isModuleLoaded("Backup-module")) {
            Backup.INSTANCE.getBackupObject().saveBackup(Maintenance.INSTANCE.getDiscordBot().getGuild().getSelfMember().getIdLong(), backupEntry -> {

            });
        }

        configObject.setValue(MaintenanceConfig.ENABLED, true);
        configObject.setValue(MaintenanceConfig.REASON, reason);
        update();
    }

    @Override
    public void disableMaintenance() {

        configObject.setValue(MaintenanceConfig.ENABLED, false);
        update();
    }

    @Override
    public long getTimedMaintenance() {
        return configObject.getValue(MaintenanceConfig.TIMED_MAINTENANCE);
    }

    @Override
    public void setTimedMaintenance(long value) {
        configObject.setValue(MaintenanceConfig.TIMED_MAINTENANCE, value);
    }
}
