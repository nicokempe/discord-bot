package eu.nicokempe.discordbot.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserEntry {

    private final String id;
    private final String avatar;
    private final String userName, nickname;
    private final long discordJoined;
    private final boolean isBot;

    public long getIdLong() {
        return Long.parseLong(id);
    }

}
