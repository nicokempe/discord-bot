package eu.nicokempe.discordbot.listener;

import eu.nicokempe.discordbot.DiscordBot;
import eu.nicokempe.discordbot.user.DiscordUser;
import eu.nicokempe.discordbot.user.IDiscordUser;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import okhttp3.FormBody;
import org.jetbrains.annotations.NotNull;

public class JoinListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        Member member = event.getMember();
        User user = member.getUser();

        DiscordBot.INSTANCE.sendPost("userJoin", new FormBody.Builder().add("name", user.getName()).add("discordId", user.getId()).build());

        IDiscordUser discordUser = new DiscordUser();
        discordUser.load(user.getIdLong());
        DiscordBot.INSTANCE.getUsers().add(discordUser);
    }
}
