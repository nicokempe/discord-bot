package eu.nicokempe.discordbot;

import com.google.gson.*;
import eu.nicokempe.discordbot.adapter.InterfaceAdapter;
import eu.nicokempe.discordbot.channel.ChannelEntry;
import eu.nicokempe.discordbot.command.handler.ICommandManager;
import eu.nicokempe.discordbot.command.handler.action.CommandAction;
import eu.nicokempe.discordbot.config.IConfigObject;
import eu.nicokempe.discordbot.config.JsonConfig;
import eu.nicokempe.discordbot.log.ILogObject;
import eu.nicokempe.discordbot.log.type.LogType;
import eu.nicokempe.discordbot.module.IModuleLoader;
import eu.nicokempe.discordbot.update.UpdateTask;
import eu.nicokempe.discordbot.user.IDiscordUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import okhttp3.RequestBody;

import java.util.ArrayList;
import java.util.List;

public interface IDiscordBot {

    Gson GSON = new GsonBuilder().registerTypeAdapter(CommandAction.class, new InterfaceAdapter<CommandAction>()).registerTypeAdapter(LogType.class, new InterfaceAdapter<LogType>()).create();
    public static final List<ChannelEntry> CHANNEL = new ArrayList<>();

    void enable();

    void disable();

    String getGuildId();

    void setGuild(Guild guild);

    IModuleLoader getModuleLoader();

    ILogObject getLogObject();

    Guild getGuild();

    JDA getJda();

    IConfigObject getDefaultConfig();

    List<IDiscordUser> getUsers();

    IDiscordUser getUser(long id);

    <T> T getUser(long id, Class<T> tClass);

    <T> List<T> getUsers(Class<T> tClass);

    ICommandManager getCommandManager();

    //AuthKey getAuthKey();

    UpdateTask getUpdateTask();

    IConfigObject getNewConfigObject();

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

    @AllArgsConstructor
    @Getter
    class AuthKey {
        private final String key;
        private final long timestamp;
    }

}
