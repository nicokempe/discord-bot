package eu.nicokempe.discordbot.server.api.config.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ConfigEntry {

    private Map<String, Object> values = new HashMap<>();
    private long timestamp = System.currentTimeMillis();
/*
    public Map<String, Object> getValues(String category) {
        return values.getOrDefault(category, new HashMap<>());
    }

    public Object getValue(String category, String key) {
        return getValues(category).getOrDefault(key, null);
    }

    public <T> void putValue(String category, String key, T value) {
        if (!values.containsKey(category))
            values.put(category, new HashMap<>());
        values.get(category).put(key, value);
    }*/

}
