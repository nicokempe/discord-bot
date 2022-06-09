package eu.nicokempe.discordbot;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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

}
