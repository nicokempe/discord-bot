package eu.nicokempe.discordbot.server.api.backup;

import eu.nicokempe.discordbot.server.api.backup.data.BackupEntry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IBackupController {

    @GetMapping("/backups")
    List<BackupEntry> getBackups();

    @PostMapping("/backups")
    void setBackups(@RequestBody List<BackupEntry> backups);

}
