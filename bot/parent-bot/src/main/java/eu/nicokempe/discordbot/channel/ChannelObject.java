package eu.nicokempe.discordbot.channel;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import eu.nicokempe.discordbot.DiscordBot;
import eu.nicokempe.discordbot.request.RequestBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.GuildChannel;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChannelObject implements IChannelObject {

    private final List<ChannelEntry> entries = new ArrayList<>();

    @Override
    public ChannelEntry getChannelById(String id) {
        return entries.stream().filter(channelEntry -> channelEntry.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void loadChannel() {
        for (JsonElement channel : RequestBuilder.builder().authKey(DiscordBot.INSTANCE.getAuthKey()).route("channel").build().get().getAsJsonArray()) {
            ChannelEntry entry = new Gson().fromJson(channel.getAsJsonObject().toString(), ChannelEntry.class);
            entries.add(entry);
            System.out.println(entry.getId());
            if(entry.isAutoChannel())
                System.out.println("auto channel");
        }

        int size = entries.size();

        for (GuildChannel channel : DiscordBot.INSTANCE.getGuild().getChannels()) {
            if(getChannelById(channel.getId()) == null) {
                ChannelEntry entry = new ChannelEntry(
                        channel.getId(),
                        channel.getName(),
                        channel.getPosition(),
                        channel.getType(),
                        false,
                        true);
                entries.add(entry);
            } else {
                
            }
        }

        if(size != entries.size()) {
            update();
        }
    }

    @Override
    public void update() {
        CurrentChannel currentChannel = new CurrentChannel(this.entries);
        RequestBuilder.builder().route("channel")
                .jsonBody(new JSONObject().put("channel", new Gson().toJson(currentChannel)))
                .authKey(DiscordBot.INSTANCE.getAuthKey())
                .build().post();

    }


    @RequiredArgsConstructor
    @Getter
    public class CurrentChannel {
        private final List<ChannelEntry> entries;
        private final long timestamp = System.currentTimeMillis();
    }
}
