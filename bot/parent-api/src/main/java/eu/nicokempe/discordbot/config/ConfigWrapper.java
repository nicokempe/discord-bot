package eu.nicokempe.discordbot.config;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConfigWrapper<T> extends ConfigValue<T> {

    public static final List<ConfigValue<?>> VALUES = new CopyOnWriteArrayList<>();

    public ConfigWrapper(String key, Class<T> tClass, T defaultValue) {
        super(key, tClass, defaultValue);
        VALUES.add(this);
    }

    public static <T> ConfigValue<T> getValueByKey(String key) {
        return (ConfigValue<T>) VALUES.stream().filter(configValue -> configValue.getKey().equals(key)).findFirst().orElse(null);
    }
}
