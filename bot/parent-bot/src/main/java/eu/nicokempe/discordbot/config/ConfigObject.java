package eu.nicokempe.discordbot.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import eu.nicokempe.discordbot.DiscordBot;
import eu.nicokempe.discordbot.request.RequestBuilder;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class ConfigObject implements IConfigObject {

    private String route;
    private ConfigEntry configEntry;

    @Override
    public void load(String route) {
        this.route = route;

        JsonObject element = RequestBuilder.builder().route("config?target=" + route).authKey(DiscordBot.INSTANCE.getAuthKey()).build().get().getAsJsonObject();

        configEntry = new Gson().fromJson(element.toString(), ConfigEntry.class);
    }

    @Override
    public void update() {
        String entry = new Gson().toJson(configEntry);
        RequestBuilder.builder().route("config?target=" + route).authKey(DiscordBot.INSTANCE.getAuthKey()).response(response -> {

        })/*.body(new FormBody.Builder().add("config", new Gson().toJson(configEntry)))*/.jsonBody(new JSONObject().put("config", entry)).build().post();
    }

    @Override
    public <T> T getValue(ConfigValue<T> setting) {
        return configEntry.getValue(setting);
    }

    @Override
    public <T> void setValue(ConfigValue<T> setting, T value) {
        configEntry.setValue(setting, value);
    }

    @Override
    public <E, T extends List<E>> void addListValue(ConfigValue<T> setting, E value) {
        configEntry.getValue(setting).add(value);
    }

    @Override
    public <E, Z, T extends Map<E, Z>> void putMapValue(ConfigValue<T> setting, E key, Z value) {
        configEntry.getValue(setting).put(key, value);
    }

    @Override
    public <E, Z, T extends Map<E, Z>> Z getMapValue(ConfigValue<T> setting, E key) {
        return configEntry.getValue(setting).getOrDefault(key, null);
    }

    @Override
    public Map<ConfigValue<?>, Object> getSettingObjects() {
        return null;
    }
}
