package eu.nicokempe.discordbot.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserEntry {

    private String id;
    private String password;
    private String key;
    private String avatarUrl;
    private String name, nickname;
    private long discordJoined;
    private boolean isBot;
    private boolean owner = false;

    public long getIdLong() {
        return Long.parseLong(id);
    }

}
