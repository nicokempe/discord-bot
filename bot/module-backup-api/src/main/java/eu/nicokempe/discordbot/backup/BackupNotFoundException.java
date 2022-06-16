package eu.nicokempe.discordbot.backup;

import lombok.Getter;

@Getter
public class BackupNotFoundException extends Exception {

    private final String id;

    public BackupNotFoundException(String message, String id) {
        super(message);
        this.id = id;
    }
}