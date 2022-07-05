package eu.nicokempe.discordbot.server.api.commands;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommandRepository extends MongoRepository<CustomCommand, String> {

    CustomCommand findByName(String name);

}
