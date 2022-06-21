package eu.nicokempe.discordbot.config;

public class ConfigWrapper<T> extends ConfigValue<T> {

    public ConfigWrapper(String key, Class<T> tClass, T defaultValue) {
        super(key, tClass, defaultValue);
    }
}
