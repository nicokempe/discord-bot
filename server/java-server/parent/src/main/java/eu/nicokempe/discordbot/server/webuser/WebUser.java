package eu.nicokempe.discordbot.server.webuser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "webUser")
public class WebUser {

    @Id
    private String id;
    private String name;
    private String password;
    private String key;

    public WebUser() {
    }

    public WebUser(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
