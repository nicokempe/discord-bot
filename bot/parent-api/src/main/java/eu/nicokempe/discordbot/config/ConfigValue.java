package eu.nicokempe.discordbot.config;

import lombok.Getter;

@Getter
public abstract class ConfigValue<T> {

    private final String key;
    private final Class<T> tClass;
    private final T defaultValue;

    public ConfigValue(String key, Class<T> tClass, T defaultValue) {
        this.key = key;
        this.tClass = tClass;
        this.defaultValue = defaultValue;
    }

}
