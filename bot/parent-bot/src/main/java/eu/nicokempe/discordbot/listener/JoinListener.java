package eu.nicokempe.discordbot.listener;

import eu.nicokempe.discordbot.DiscordBot;
import eu.nicokempe.discordbot.config.DefaultConfigValue;
import eu.nicokempe.discordbot.log.data.LogEntry;
import eu.nicokempe.discordbot.log.type.UserJoinType;
import eu.nicokempe.discordbot.request.RequestBuilder;
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

        DiscordBot.INSTANCE.getLogObject().saveLog(new LogEntry(user.getId()).logType(new UserJoinType()));
        DiscordBot.INSTANCE.getLogObject().update();

        //String avatar = user.getAvatarUrl() == null ? "" : user.getAvatarUrl();
        //RequestBuilder.builder().route("userJoin").body(new FormBody.Builder().add("name", user.getName()).add("discordId", user.getId()).add("avatar", avatar)).authKey(DiscordBot.INSTANCE.getAuthKey()).build().post();

        IDiscordUser discordUser = new DiscordUser();
        discordUser.load(user.getIdLong());
        DiscordBot.INSTANCE.getUsers().add(discordUser);

        if (DiscordBot.INSTANCE.getDefaultConfig().getValue(DefaultConfigValue.WELCOME_MESSAGE_ENABLED))
            event.getGuild().getTextChannelById(DiscordBot.INSTANCE.getDefaultConfig().getValue(DefaultConfigValue.WELCOME_LEAVE_MESSAGE_CHANNEL)).sendMessage(DiscordBot.INSTANCE.getDefaultConfig().getValue(DefaultConfigValue.WELCOME_MESSAGE)).queue();
    }
}
