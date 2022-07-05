package eu.nicokempe.discordbot.server.api.config;

import eu.nicokempe.discordbot.server.api.config.data.ConfigEntry;
import eu.nicokempe.discordbot.server.api.config.data.ConfigLoadObject;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ConfigRepository extends MongoRepository<ConfigLoadObject, String> {

    ConfigLoadObject findByCategory(String category);

}
