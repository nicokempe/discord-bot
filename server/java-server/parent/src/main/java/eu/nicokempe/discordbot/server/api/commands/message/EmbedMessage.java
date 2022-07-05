package eu.nicokempe.discordbot.server.api.commands.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

@AllArgsConstructor
@Getter
public class EmbedMessage {

    private int color;
    private final String title;
    private final Footer footer;

    public EmbedMessage setColor(Color color) {
        this.color = color.getRGB();
        return this;
    }

}
