package eu.nicokempe.discordbot.log;

import com.google.gson.JsonElement;
import eu.nicokempe.discordbot.DiscordBot;
import eu.nicokempe.discordbot.IDiscordBot;
import eu.nicokempe.discordbot.log.data.LogEntry;
import eu.nicokempe.discordbot.request.RequestBuilder;
import lombok.Getter;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Getter
public class LogObject implements ILogObject {

    private final List<LogEntry> logs = new ArrayList<>();

    @Override
    public void saveLog(LogEntry entry) {
        logs.add(entry);
    }

    @Override
    public void update() {
        String json = IDiscordBot.GSON.toJson(new LogLoadObject(logs));
        logs.clear();
        RequestBuilder.builder()
                .route("logs")
                .authKey(DiscordBot.INSTANCE.getAuthKey())
                .jsonBody(
                        new JSONObject()
                                .put("logs", json)
                ).build().post();
    }
}
