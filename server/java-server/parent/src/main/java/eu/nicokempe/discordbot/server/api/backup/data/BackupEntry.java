package eu.nicokempe.discordbot.server.api.backup.data;

import eu.nicokempe.discordbot.server.api.IServerApplication;
import eu.nicokempe.discordbot.server.api.backup.IBackupController;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class BackupEntry {

    private final String id = IServerApplication.generateString(10);
    private final long timestamp = System.currentTimeMillis();
    private final String creator;
    private final List<BackupChannel> channels = new ArrayList<>();
    private final List<BackupRole> roles = new ArrayList<>();
    private final String icon;

}