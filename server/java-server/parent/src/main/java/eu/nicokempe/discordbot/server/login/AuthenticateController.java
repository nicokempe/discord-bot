package eu.nicokempe.discordbot.server.login;

import eu.nicokempe.discordbot.server.ServerApplication;
import eu.nicokempe.discordbot.server.api.IServerApplication;
import eu.nicokempe.discordbot.server.webuser.WebUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthenticateController {

    private final List<AuthKey> keys = new ArrayList<>();

    @PostMapping(path = "/login", consumes = "application/json")
    public ResponseEntity<AuthKey> login(@RequestBody LoginRequestObject loginRequest) {
        WebUser user = ServerApplication.INSTANCE.getRepository().findByName(loginRequest.name());
        if (user == null) {
            return ResponseEntity.of(Optional.empty());
        }
        if (!BCrypt.checkpw(loginRequest.password(), user.getPassword())) {
            return ResponseEntity.of(Optional.empty());
        }
        AuthKey key = new AuthKey(IServerApplication.generateString(250));
        user.setKey(key.getKey());
        ServerApplication.INSTANCE.getRepository().save(user);
        return ResponseEntity.ok(key);
    }

    public record LoginRequestObject(String name, String password) {
    }

    @AllArgsConstructor
    @Getter
    class AuthKey {
        private final String key;
        private final long timestamp = System.currentTimeMillis();
    }

}
