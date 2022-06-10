package eu.nicokempe.discordbot;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import eu.nicokempe.discordbot.command.handler.ICommandManager;
import eu.nicokempe.discordbot.module.IModuleLoader;
import eu.nicokempe.discordbot.user.IDiscordUser;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import okhttp3.RequestBody;

import java.util.List;

public interface IDiscordBot {

    void enable();

    void disable();

    void loadModules();

    void sendPost(String typ, RequestBody body);

    long getGuildId();

    void setGuild(Guild guild);

    IModuleLoader getModuleLoader();

    JsonElement get(String typ);

    Guild getGuild();

    JDA getJda();

    List<IDiscordUser> getUsers();

    IDiscordUser getUser(long id);

    ICommandManager getCommandManager();

    static String generateString(int length) {
        StringBuilder result = new StringBuilder();
        while (result.length() < length)
            result.append(getChar());
        return result.toString();
    }

    private static char getChar() {
        int s = getInt("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".length());
        return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt(s - 1);
    }

    private static int getInt(int max) {
        return (int) Math.ceil(Math.random() * max);
    }

}
