package eu.nicokempe.discordbot.server.config;

import com.google.gson.Gson;
import eu.nicokempe.discordbot.server.ServerApplication;
import eu.nicokempe.discordbot.server.api.config.IConfigController;
import eu.nicokempe.discordbot.server.api.config.data.ConfigEntry;
import eu.nicokempe.discordbot.server.api.config.data.ConfigLoadObject;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ConfigController implements IConfigController {

    @Override
    public ResponseEntity<ConfigEntry> putConfig(String target, String entry) {
        ConfigLoadObject loadObject = getConfigLoadObject(target);

        if (loadObject == null)
            loadObject = new ConfigLoadObject(target);

        ConfigEntry configEntry = new Gson().fromJson(new JSONObject(entry).getString("config"), ConfigEntry.class);
        loadObject.setConfigEntry(configEntry);

        return ResponseEntity.ok(ServerApplication.INSTANCE.getConfigRepository().save(loadObject).getConfigEntry());
    }

    @Override
    public ResponseEntity<ConfigEntry> getConfig(String target) {
        ConfigLoadObject loadObject = getConfigLoadObject(target);

        if (loadObject == null) return ResponseEntity.of(Optional.of(new ConfigEntry()));

        return ResponseEntity.ok(loadObject.getConfigEntry());
    }

    private ConfigLoadObject getConfigLoadObject(String target) {
        return ServerApplication.INSTANCE.getConfigRepository().findByCategory(target);
    }

    @Override
    public ResponseEntity<List<ConfigEntry>> getConfigs() {
        return ResponseEntity.ok(ServerApplication.INSTANCE.getConfigRepository().findAll().stream().map(ConfigLoadObject::getConfigEntry).collect(Collectors.toList()));
    }

}
