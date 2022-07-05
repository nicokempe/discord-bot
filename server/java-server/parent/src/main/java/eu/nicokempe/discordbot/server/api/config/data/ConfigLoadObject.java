package eu.nicokempe.discordbot.server.api.config.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config")
@Getter
@Setter
public class ConfigLoadObject {

    @Id
    private String id;
    private String category;
    private ConfigEntry configEntry;

    public ConfigLoadObject() {
    }

    public ConfigLoadObject(String category) {
        this.category = category;
        this.configEntry = new ConfigEntry();
    }

}
