package eu.nicokempe.discordbot.server.api.commands.message;

import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Message {

    private final String message;
    private final boolean ephemeral;
    @Nullable
    private final EmbedMessage embedBuilder;

}
