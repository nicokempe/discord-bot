package eu.nicokempe.discordbot.listener;

import com.google.gson.Gson;
import eu.nicokempe.discordbot.DiscordBot;
import eu.nicokempe.discordbot.channel.ChannelEntry;
import eu.nicokempe.discordbot.user.DiscordUser;
import eu.nicokempe.discordbot.user.IDiscordUser;
import eu.nicokempe.discordbot.user.UserEntry;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import okhttp3.FormBody;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReadyListener extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        Guild guild = DiscordBot.INSTANCE.getJda().getGuildById(DiscordBot.INSTANCE.getGuildId());
        DiscordBot.INSTANCE.setGuild(guild);

        long timestamp = System.currentTimeMillis();
        if (guild == null) return;

        System.out.println("Loading Users...");
        guild.loadMembers(member -> {
            IDiscordUser discordUser = new DiscordUser();
            discordUser.load(member.getIdLong());
            DiscordBot.INSTANCE.getUsers().add(discordUser);
        }).onSuccess(unused -> {
            int took = Math.toIntExact((System.currentTimeMillis() - timestamp) / 1000);
            int rest = Math.toIntExact((System.currentTimeMillis() - timestamp) % 1000);
            System.out.println(MessageFormat.format("Loading {0} Users complete! (Took {1},{2}s)", DiscordBot.INSTANCE.getUsers().size(), took, rest));

            CurrentUser currentUser = new CurrentUser(
                    DiscordBot.INSTANCE.getUsers().stream().map(user ->
                            new UserEntry(
                                    user.getIdString(),
                                    user.getUser().getAvatarUrl(),
                                    user.getName(),
                                    user.getNickname(),
                                    System.currentTimeMillis(),
                                    user.isBot()
                            )).collect(Collectors.toList())
            );
            CurrentChannel currentChannel = new CurrentChannel(
                    guild.getChannels().stream().map(guildChannel ->
                            new ChannelEntry(
                                    guildChannel.getId(),
                                    guildChannel.getName(),
                                    guildChannel.getPosition(),
                                    guildChannel.getType()
                            )).collect(Collectors.toList())
            );

            DiscordBot.INSTANCE.sendPost("currentUser", new FormBody.Builder().add("member", new Gson().toJson(currentUser)).build());
            DiscordBot.INSTANCE.sendPost("currentChannel", new FormBody.Builder().add("channel", new Gson().toJson(currentChannel)).build());

            DiscordBot.INSTANCE.loadModules();
        });

    }

    @RequiredArgsConstructor
    @Getter
    public class CurrentUser {
        private final List<UserEntry> user;
        private final long timestamp = System.currentTimeMillis();
    }

    @RequiredArgsConstructor
    @Getter
    public class CurrentChannel {
        private final List<ChannelEntry> channel;
        private final long timestamp = System.currentTimeMillis();
    }
}
