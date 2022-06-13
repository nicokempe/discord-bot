package eu.nicokempe.discordbot;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import eu.nicokempe.discordbot.command.handler.ICommandManager;
import eu.nicokempe.discordbot.command.manager.CommandManager;
import eu.nicokempe.discordbot.listener.JoinListener;
import eu.nicokempe.discordbot.listener.ReadyListener;
import eu.nicokempe.discordbot.listener.SlashListener;
import eu.nicokempe.discordbot.logger.Logger;
import eu.nicokempe.discordbot.module.IModuleLoader;
import eu.nicokempe.discordbot.module.ModuleLoader;
import eu.nicokempe.discordbot.request.RequestBuilder;
import eu.nicokempe.discordbot.user.IDiscordUser;
import lombok.AllArgsConstructor;
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
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class DiscordBot implements IDiscordBot {

    public static IDiscordBot INSTANCE;
    protected AuthKey authKey;

    private final List<IDiscordUser> discordUsers = new ArrayList<>();

    private IModuleLoader moduleLoader;
    private ICommandManager commandManager;

    private JDA jda;
    private Guild guild;

    private Thread messageThread;

    public DiscordBot() {
        INSTANCE = this;
    }

    @Override
    public void enable() {
        new Logger();
        RequestBuilder.builder().route("login").body(new FormBody.Builder().add("name", "Admin").add("password", "123456")).response(response -> {
            if (response.isSuccessful()) {
                try {
                    authKey = new Gson().fromJson(response.body().string(), DiscordBot.AuthKey.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                init();
            }
        }).build().post();
    }

    @SneakyThrows
    private void init() {
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

        messageThread = new Thread(() -> {
            while (true) {
                if (guild == null) return;
                for (JsonElement element : RequestBuilder.builder().route("messages").build().get().getAsJsonArray()) {
                    JsonObject object = element.getAsJsonObject();

                    if (!object.has("message") || !object.has("channel") || !object.has("id")) {
                        continue;
                    }

                    String message = object.get("message").getAsString();
                    long channel = object.get("channel").getAsLong();
                    long schedule = object.get("schedule").getAsLong();
                    String id = object.get("id").getAsString();

                    if (schedule > System.currentTimeMillis()) return;
                    guild.getTextChannelById(channel).sendMessage(message).queue();
                    RequestBuilder.builder().route("messages").body(new FormBody.Builder().add("id", id)).authKey(authKey).build().delete();
                }
                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "message");

        messageThread.start();
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
            if (e != null && !(e instanceof NoSuchFileException)) {
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
        return RequestBuilder.builder().route("guild").build().get().getAsJsonObject().get("guildId").getAsLong();
        //return get("guild").getAsJsonObject().get("guildId").getAsLong();
    }

    @Override
    public List<IDiscordUser> getUsers() {
        return discordUsers;
    }

    @Override
    public IDiscordUser getUser(long id) {
        return discordUsers.stream().filter(iDiscordUser -> iDiscordUser.getId() == id).findFirst().orElse(null);
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
