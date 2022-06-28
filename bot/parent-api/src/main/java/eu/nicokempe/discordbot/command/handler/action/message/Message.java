package eu.nicokempe.discordbot.command.handler.action.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Getter
@AllArgsConstructor
public class Message {

    private final String message;
    private final boolean ephemeral;
    @Nullable
    private final EmbedMessage embedBuilder;

}
