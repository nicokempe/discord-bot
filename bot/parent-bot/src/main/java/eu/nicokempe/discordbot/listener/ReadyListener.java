package eu.nicokempe.discordbot.listener;

import eu.nicokempe.discordbot.DiscordBot;
import eu.nicokempe.discordbot.user.DiscordUser;
import eu.nicokempe.discordbot.user.IDiscordUser;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import okhttp3.FormBody;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class ReadyListener extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        Guild guild = DiscordBot.INSTANCE.getJda().getGuildById(DiscordBot.INSTANCE.getGuildId());
        DiscordBot.INSTANCE.setGuild(guild);

        long timestamp = System.currentTimeMillis();
        if (guild == null) return;

        System.out.println("Loading Users...");
        guild.loadMembers(member -> {
            if (member.getUser().isBot()) return;
            IDiscordUser discordUser = new DiscordUser();
            discordUser.load(member.getIdLong());
            DiscordBot.INSTANCE.getUsers().add(discordUser);
        }).onSuccess(unused -> {
            int took = Math.toIntExact((System.currentTimeMillis() - timestamp) / 1000);
            System.out.println(MessageFormat.format("Loading {0} Users complete! (Took {1}s)", DiscordBot.INSTANCE.getUsers().size(), took));
            DiscordBot.INSTANCE.sendPost("currentUser", new FormBody.Builder().add("amount", String.valueOf(DiscordBot.INSTANCE.getUsers().size())).add("timestamp", String.valueOf(System.currentTimeMillis())).build());
            DiscordBot.INSTANCE.loadModules();
        });
    }
}
