package eu.nicokempe.discordbot.server.backup;

import eu.nicokempe.discordbot.server.api.backup.IBackupController;
import eu.nicokempe.discordbot.server.api.backup.data.BackupEntry;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RestController
@RequestMapping("/api")
public class BackupController implements IBackupController {

    private List<BackupEntry> backups = new ArrayList<>();

}
