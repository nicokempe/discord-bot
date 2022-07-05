package eu.nicokempe.discordbot.server.api.config;

import eu.nicokempe.discordbot.server.api.config.data.ConfigEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IConfigController {

    @PostMapping(path = "/config", produces = "application/json")
    ResponseEntity<ConfigEntry> putConfig(@RequestParam String target, @RequestBody String config);

    @GetMapping(path = "/config", produces = "application/json")
    ResponseEntity<ConfigEntry> getConfig(@RequestParam String target);

    @GetMapping(path = "/configs", produces = "application/json")
    ResponseEntity<List<ConfigEntry>> getConfigs();

}
