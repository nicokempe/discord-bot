package eu.nicokempe.discordbot.module;

import com.google.gson.Gson;
import eu.nicokempe.discordbot.IDiscordBot;
import eu.nicokempe.discordbot.request.RequestBuilder;
import eu.nicokempe.discordbot.update.UpdateTask;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Properties;
import java.util.function.Consumer;

@Getter
@Setter
public abstract class ModuleInterface {

    private IDiscordBot discordBot;
    private Properties properties;
    private IDiscordBot.AuthKey authKey;

    public abstract void enable();

    public abstract void disable();

    public void authenticate(String name, String password, Consumer<IDiscordBot.AuthKey> authKey) {
        RequestBuilder.builder().route("login").jsonBody(new JSONObject().put("name", name).put("password", password)).response(response -> {
            if (response.isSuccessful()) {
                try {
                    authKey.accept(new Gson().fromJson(response.body().string(), IDiscordBot.AuthKey.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Login failed");
            }
        }).build().post();
    }

}
