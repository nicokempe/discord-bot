package eu.nicokempe.discordbot.messenger.task;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import eu.nicokempe.discordbot.messenger.Messenger;
import eu.nicokempe.discordbot.request.RequestBuilder;
import lombok.Getter;
import net.dv8tion.jda.api.entities.Guild;
import okhttp3.FormBody;

@Getter
public class MessageTask implements Runnable {

    private final Thread thread;
    private final Guild guild;

    public MessageTask() {
        guild = Messenger.INSTANCE.getDiscordBot().getGuild();

        thread = new Thread(this, "message");
    }

    public void start() {
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            if (guild == null) return;
            for (JsonElement element : RequestBuilder.builder().route("messages").build().get().getAsJsonArray()) {
                JsonObject object = element.getAsJsonObject();

                if (!object.has("message") || !object.has("channel") || !object.has("id")) {
                    continue;
                }

                String message = object.get("message").getAsString();
                long channel = object.get("channel").getAsLong();
                long schedule = object.get("schedule").getAsLong();
                String id = object.get("id").getAsString();

                if (schedule > System.currentTimeMillis()) return;
                guild.getTextChannelById(channel).sendMessage(message).queue();
                RequestBuilder.builder().route("messages").body(new FormBody.Builder().add("id", id)).authKey(Messenger.INSTANCE.getDiscordBot().getAuthKey()).build().delete();
            }
            try {
                Thread.sleep(1000 * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
