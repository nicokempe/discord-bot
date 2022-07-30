package eu.nicokempe.discordbot.log.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class StartUpType extends LogType {

    private final long took;

    public StartUpType(long took) {
        super(StartUpType.class.getName());
        this.took = took;
    }
}
