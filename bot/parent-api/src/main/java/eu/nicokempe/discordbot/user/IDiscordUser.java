package eu.nicokempe.discordbot.user;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

public interface IDiscordUser {

    boolean isBot();

    void load(long id);

    long getId();

    String getIdString();

    void setNickname(String name);

    Member getMember();

    User getUser();

    String getName();

    String getNickname();

    OnlineStatus getStatus();

    <T> T getPlayer(Class<T> tClass);

    <T> void setPlayer(Class<T> tClass, T player);

}
