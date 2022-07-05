package eu.nicokempe.discordbot.server.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.nicokempe.discordbot.server.api.adapter.InterfaceAdapter;
import eu.nicokempe.discordbot.server.api.commands.CommandAction;
import eu.nicokempe.discordbot.server.api.config.JsonConfig;

public interface IServerApplication {

    Gson GSON = new GsonBuilder().registerTypeAdapter(CommandAction.class, new InterfaceAdapter<>()).setPrettyPrinting().create();

    void enable();

    static String generateString(int length) {
        StringBuilder result = new StringBuilder();
        while (result.length() < length)
            result.append(getChar());
        return result.toString();
    }

    private static char getChar() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789#?!:;,.-_";
        int s = getInt(chars.length());
        return chars.charAt(s - 1);
    }

    private static int getInt(int max) {
        return (int) Math.ceil(Math.random() * max);
    }

}
