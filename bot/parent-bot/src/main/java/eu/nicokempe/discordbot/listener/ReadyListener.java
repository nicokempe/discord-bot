package eu.nicokempe.discordbot.listener;

import com.google.gson.Gson;
import eu.nicokempe.discordbot.DiscordBot;
import eu.nicokempe.discordbot.IDiscordBot;
import eu.nicokempe.discordbot.channel.ChannelEntry;
import eu.nicokempe.discordbot.request.RequestBuilder;
import eu.nicokempe.discordbot.user.DiscordUser;
import eu.nicokempe.discordbot.user.IDiscordUser;
import eu.nicokempe.discordbot.user.UserEntry;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import okhttp3.FormBody;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

public class ReadyListener extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        Guild guild = DiscordBot.INSTANCE.getJda().getGuildById(DiscordBot.INSTANCE.getGuildId());
        DiscordBot.INSTANCE.setGuild(guild);

        if (guild == null) return;

        System.out.println("Loading users...");
        guild.loadMembers(member -> {
            IDiscordUser discordUser = new DiscordUser();
            discordUser.load(member.getIdLong());
            DiscordBot.INSTANCE.getUsers().add(discordUser);
        }).onSuccess(unused -> {
            System.out.println(MessageFormat.format("{0} user(s) was successfully loaded!", DiscordBot.INSTANCE.getUsers().size()));

            CurrentClass<UserEntry> currentUser = new CurrentClass<>(
                    DiscordBot.INSTANCE.getUsers().stream().map(user ->
                            new UserEntry(
                                    user.getIdString(),
                                    user.getUser().getAvatarUrl(),
                                    user.getName(),
                                    user.getNickname(),
                                    user.getMember().getTimeJoined().toInstant().toEpochMilli(),
                                    user.isBot()
                            )).collect(Collectors.toList())
            );
            CurrentClass<ChannelEntry> currentChannel = new CurrentClass<>(
                    guild.getChannels().stream().map(guildChannel ->
                            new ChannelEntry(
                                    guildChannel.getId(),
                                    guildChannel.getName(),
                                    guildChannel.getPosition(),
                                    guildChannel.getType()
                            )).collect(Collectors.toList())
            );

            RequestBuilder.builder().route("currentUser").body(new FormBody.Builder().add("member", new Gson().toJson(currentUser))).authKey(DiscordBot.INSTANCE.getAuthKey()).build().post();
            RequestBuilder.builder().route("currentChannel").body(new FormBody.Builder().add("channel", new Gson().toJson(currentChannel))).authKey(DiscordBot.INSTANCE.getAuthKey()).build().post();

            DiscordBot.INSTANCE.loadModules();
        });

    }

    @RequiredArgsConstructor
    @Getter
    public class CurrentClass<T> {
        private final List<T> user;
        private final long timestamp = System.currentTimeMillis();
    }

}
