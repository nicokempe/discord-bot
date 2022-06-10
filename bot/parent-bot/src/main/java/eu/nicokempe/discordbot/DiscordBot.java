package eu.nicokempe.discordbot;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eu.nicokempe.discordbot.command.handler.ICommandManager;
import eu.nicokempe.discordbot.command.manager.CommandManager;
import eu.nicokempe.discordbot.listener.JoinListener;
import eu.nicokempe.discordbot.listener.ReadyListener;
import eu.nicokempe.discordbot.listener.SlashListener;
import eu.nicokempe.discordbot.logger.Logger;
import eu.nicokempe.discordbot.module.IModuleLoader;
import eu.nicokempe.discordbot.module.ModuleLoader;
import eu.nicokempe.discordbot.user.IDiscordUser;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import okhttp3.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class DiscordBot implements IDiscordBot {

    public static IDiscordBot INSTANCE;

    private final List<IDiscordUser> discordUsers = new ArrayList<>();

    private IModuleLoader moduleLoader;
    private ICommandManager commandManager;

    private JDA jda;
    private Guild guild;

    public DiscordBot() {
        INSTANCE = this;
    }

    @SneakyThrows
    @Override
    public void enable() {
        new Logger();
        System.out.println("Loading bot...");

        commandManager = new CommandManager();

        String token = getResourceFileAsString();

        EnumSet<GatewayIntent> intents = EnumSet.of(
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.DIRECT_MESSAGES,
                GatewayIntent.GUILD_VOICE_STATES,
                GatewayIntent.GUILD_MESSAGE_REACTIONS,
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_EMOJIS
        );

        jda = JDABuilder.createDefault(token).
                enableIntents(intents).
                setStatus(OnlineStatus.ONLINE).
                addEventListeners(
                        new ReadyListener(),
                        new JoinListener(),
                        new SlashListener()
                ).
                setRawEventsEnabled(true).
                setMemberCachePolicy(MemberCachePolicy.ALL).
                enableCache(CacheFlag.VOICE_STATE).
                setActivity(Activity.playing("Something")).build();

        jda.setAutoReconnect(true);
        jda.awaitReady();

    }

    @Override
    public void disable() {
        moduleLoader.disable();
    }

    @SneakyThrows
    @Override
    public void loadModules() {
        moduleLoader = new ModuleLoader();
        System.out.println("Loading modules...");
        moduleLoader.loadModules(e -> {
            if(e != null && !(e instanceof NoSuchFileException)) {
                try {
                    throw e;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            loadCommands();
        });
    }

    private void loadCommands() {
        System.out.println("Loading commands...");
        commandManager.loadCommands();
        System.out.println("Bot enabled.");
    }

    @Override
    public long getGuildId() {
        return get("guild").getAsJsonObject().get("guildId").getAsLong();
    }

    @Override
    public JsonElement get(String typ) {
        URL url;
        try {
            url = new URL("http://45.93.249.108:8085/api/" + typ);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        InputStreamReader reader;
        try {
            reader = new InputStreamReader(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return JsonParser.parseReader(reader);
    }

    @Override
    public List<IDiscordUser> getUsers() {
        return discordUsers;
    }

    @Override
    public IDiscordUser getUser(long id) {
        return discordUsers.stream().filter(iDiscordUser -> iDiscordUser.getId() == id).findFirst().orElse(null);
    }

    @SneakyThrows
    public void sendPost(String typ, RequestBody formBody) {

        Request request = new Request.Builder()
                .url("http://45.93.249.108:8085/api/" + typ)
                .post(formBody)
                .build();

        OkHttpClient httpClient = new OkHttpClient();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        } catch (SocketTimeoutException e) {
            System.out.println("Timeout");
        }
    }

    private String getResourceFileAsString() throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream is = classLoader.getResourceAsStream("token.txt")) {
            if (is == null) return null;
            try (InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }

}
