package eu.nicokempe.discordbot.server.api.commands.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Footer {

    private final String text;
    private final String avatarUrl;

}
