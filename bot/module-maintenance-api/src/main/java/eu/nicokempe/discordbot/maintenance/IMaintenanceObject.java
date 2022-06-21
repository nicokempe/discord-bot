package eu.nicokempe.discordbot.maintenance;

import org.jetbrains.annotations.Nullable;

public interface IMaintenanceObject {

    void load();

    void update();

    boolean isMaintenance();

    void enableMaintenance(@Nullable String reason);

    void disableMaintenance();

    long getTimedMaintenance();

    void setTimedMaintenance(long value);

}
