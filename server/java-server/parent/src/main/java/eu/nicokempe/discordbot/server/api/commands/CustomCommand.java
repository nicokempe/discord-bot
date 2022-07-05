package eu.nicokempe.discordbot.server.api.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "commands")
@AllArgsConstructor
@Getter
public class CustomCommand {

    @Id
    private String id;
    private final String name;
    private final String description;
    private final String channel;
    private final List<String> permissionGroups = new ArrayList<>();
    private final List<String> optionData = new ArrayList<>();

    private final CommandAction commandAction;

    public long getChannelLong() {
        return Long.parseLong(channel);
    }

}
