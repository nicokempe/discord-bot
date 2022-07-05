package eu.nicokempe.discordbot.server.api.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eu.nicokempe.discordbot.server.api.IServerApplication;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonConfig {

    private final File file;
    private JsonObject object = new JsonObject();

    public JsonConfig(File file) {
        this.file = file;
        reloadFromFile();
    }

    public void saveConfig() {
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (final IOException e) {
                return;
            }
        }
        try (final FileWriter writer = new FileWriter(this.file)) {
            writer.write(IServerApplication.GSON.toJson(this.object));
        } finally {
            return;
        }
    }

    public void reloadFromFile() {
        if (!this.file.exists()) {
            return;
        }
        try (final FileReader reader = new FileReader(this.file)) {
            this.object = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (final IOException e) {
        } finally {
            return;
        }
    }

    public <T> T get(String key, Class<T> tClass) {
        if (!this.object.has(key)) return null;
        return IServerApplication.GSON.fromJson(this.object.get(key), tClass);
    }

    public <T> T getOrDefaultSet(String key, Class<T> tClass, T defaultValue) {
        if (!this.object.has(key)) {
            set(key, defaultValue);
            saveConfig();
        }
        return IServerApplication.GSON.fromJson(this.object.get(key), tClass);
    }

    public <T> T set(String key, T t) {
        this.object.add(key, IServerApplication.GSON.toJsonTree(t));
        return t;
    }

    public void fromString(String string) {
        this.object = JsonParser.parseString(string).getAsJsonObject();
    }
}