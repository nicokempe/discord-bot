package eu.nicokempe.discordbot.maintenance.config;

import eu.nicokempe.discordbot.config.ConfigValue;
import eu.nicokempe.discordbot.config.ConfigWrapper;

public class MaintenanceConfig {

    public static final ConfigValue<Boolean> ENABLED = new ConfigWrapper<>("enabled", Boolean.class, false);
    public static final ConfigValue<Long> TIMED_MAINTENANCE = new ConfigWrapper<>("timed_maintenance", Long.class, 0L);
    public static final ConfigValue<String> REASON = new ConfigWrapper<>("reason", String.class, "");

}
