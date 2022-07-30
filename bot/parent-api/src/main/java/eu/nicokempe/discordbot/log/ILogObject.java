package eu.nicokempe.discordbot.log;

import eu.nicokempe.discordbot.log.data.LogEntry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public interface ILogObject {

    void saveLog(LogEntry entry);

    void update();

    List<LogEntry> getLogs();

    @Setter
    @AllArgsConstructor
    @Getter
    class LogLoadObject {

        private final List<LogEntry> logs;

    }

}
