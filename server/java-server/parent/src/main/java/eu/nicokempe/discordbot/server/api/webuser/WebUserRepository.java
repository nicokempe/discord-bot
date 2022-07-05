package eu.nicokempe.discordbot.server.api.webuser;

import eu.nicokempe.discordbot.server.webuser.WebUser;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public interface WebUserRepository extends MongoRepository<WebUser, String> {

    Optional<WebUser> findById(String id);

    WebUser findByName(String name);

}
