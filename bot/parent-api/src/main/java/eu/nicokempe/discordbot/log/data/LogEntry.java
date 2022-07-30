package eu.nicokempe.discordbot.log.data;

import com.google.gson.Gson;
import eu.nicokempe.discordbot.IDiscordBot;
import eu.nicokempe.discordbot.command.handler.action.CommandAction;
import eu.nicokempe.discordbot.log.type.LogType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LogEntry {

    private final String causeId;
    private final long timestamp = System.currentTimeMillis();
    private String logType;

    public LogEntry logType(LogType logType) {
        this.logType = IDiscordBot.GSON.toJson(logType);
        return this;
    }

    public LogType getLogType() {
        if (logType == null) return null;
        return IDiscordBot.GSON.fromJson(logType.replace("\n", ""), LogType.class);
    }

}
