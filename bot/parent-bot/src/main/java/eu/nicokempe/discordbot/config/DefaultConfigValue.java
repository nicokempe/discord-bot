package eu.nicokempe.discordbot.config;

public class DefaultConfigValue {

    public static final ConfigValue<String> GUILD_ID = new ConfigWrapper<>("guildId", String.class, "983850293693390848");

    public static final ConfigValue<String> WELCOME_MESSAGE = new ConfigWrapper<>("welcome_message", String.class, "Welcome %user.name% [F]%user.name% is the %count% user[/F]");
    public static final ConfigValue<String> LEAVE_MESSAGE = new ConfigWrapper<>("leave_message", String.class, "%user.name% has left our server");
    public static final ConfigValue<Boolean> WELCOME_MESSAGE_ENABLED = new ConfigWrapper<>("welcome_message_enabled", Boolean.class, true);
    public static final ConfigValue<Boolean> LEAVE_MESSAGE_ENABLED = new ConfigWrapper<>("leave_message_enabled", Boolean.class, false);
    public static final ConfigValue<String> WELCOME_LEAVE_MESSAGE_CHANNEL = new ConfigWrapper<>("welcome_leave_message_channel", String.class, "988435959383203890");

}
