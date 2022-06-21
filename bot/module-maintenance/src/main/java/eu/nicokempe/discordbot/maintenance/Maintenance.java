package eu.nicokempe.discordbot.maintenance;

import eu.nicokempe.discordbot.module.ModuleInterface;
import lombok.Getter;

@Getter
public class Maintenance extends ModuleInterface {

    public static Maintenance INSTANCE;

    private IMaintenanceObject maintenanceObject;

    public Maintenance() {
        INSTANCE = this;
    }

    @Override
    public void enable() {
        maintenanceObject = new MaintenanceObject();
        maintenanceObject.load();
    }

    @Override
    public void disable() {
        maintenanceObject.update();
    }
}
