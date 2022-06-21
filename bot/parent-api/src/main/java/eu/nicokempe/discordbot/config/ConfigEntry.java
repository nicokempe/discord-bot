package eu.nicokempe.discordbot.config;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ConfigEntry {

    private final Map<String, Object> values = new HashMap<>();
    private final long timestamp = System.currentTimeMillis();

    public <T> void setValue(ConfigValue<T> setting, T value) {
        values.put(setting.getKey(), value);
    }

    public <T> T getValue(ConfigValue<T> setting) {
        return (T) values.getOrDefault(setting.getKey(), setting.getDefaultValue());
    }

}
