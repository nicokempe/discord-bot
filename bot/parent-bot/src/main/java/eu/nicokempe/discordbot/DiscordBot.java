package eu.nicokempe.discordbot;

import com.google.gson.Gson;
import eu.nicokempe.discordbot.channel.ChannelObject;
import eu.nicokempe.discordbot.channel.IChannelObject;
import eu.nicokempe.discordbot.command.TestCommand;
import eu.nicokempe.discordbot.command.handler.ICommandManager;
import eu.nicokempe.discordbot.command.manager.CommandManager;
import eu.nicokempe.discordbot.config.*;
import eu.nicokempe.discordbot.listener.JoinListener;
import eu.nicokempe.discordbot.listener.MessageListener;
import eu.nicokempe.discordbot.listener.ReadyListener;
import eu.nicokempe.discordbot.listener.SlashListener;
import eu.nicokempe.discordbot.log.ILogObject;
import eu.nicokempe.discordbot.log.LogObject;
import eu.nicokempe.discordbot.log.data.LogEntry;
import eu.nicokempe.discordbot.log.type.StartUpType;
import eu.nicokempe.discordbot.logger.Logger;
import eu.nicokempe.discordbot.module.IModuleLoader;
import eu.nicokempe.discordbot.module.ModuleLoader;
import eu.nicokempe.discordbot.request.RequestBuilder;
import eu.nicokempe.discordbot.update.UpdateTask;
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
import org.json.JSONObject;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;

@Getter
@Setter
public class DiscordBot implements IDiscordBot {

    public static DiscordBot INSTANCE;
    protected AuthKey authKey;

    private final List<IDiscordUser> discordUsers = new ArrayList<>();

    private IModuleLoader moduleLoader;
    private ICommandManager commandManager;
    private IConfigObject defaultConfig;
    private IChannelObject channelObject;

    private ILogObject logObject;
    private Timer timer = new Timer();
    private UpdateTask updateTask;

    private JDA jda;
    private Guild guild;
    private JsonConfig config;

    private long start;

    public DiscordBot() {
        INSTANCE = this;
    }

    @Override
    public void enable() {
        new Logger();

        config = new JsonConfig(new File("../config.json"));
        String name = config.get("name", String.class);
        String password = config.get("password", String.class);
        String host = config.get("host", String.class);

        if (name == null) name = config.set("name", "Admin");
        if (password == null) password = config.set("password", "123456");
        if (host == null) host = config.set("host", "http://127.0.0.1:8081/api/");
        config.saveConfig();

        System.out.println("Logging in...");
        RequestBuilder.url = host;
        RequestBuilder.builder().route("login").jsonBody(new JSONObject().put("name", name).put("password", password)).response(response -> {
            if (response.isSuccessful()) {
                try {
                    authKey = new Gson().fromJson(response.body().string(), AuthKey.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                updateTask = new UpdateTask();

                logObject = new LogObject();

                defaultConfig = new ConfigObject();
                defaultConfig.load("default");
                DefaultConfigValue.load(defaultConfig);

                init();
            } else {
                System.out.println("Login failed");
            }
        }).build().post();
    }

    @SneakyThrows
    private void init() {
        start = System.currentTimeMillis();
        System.out.println("Loading bot...");

        commandManager = new CommandManager();

        String token = config.getOrDefaultSet("token", String.class, "yourToken");

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
                        new SlashListener(),
                        new MessageListener()
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
        System.out.println("Disabling bot...");
        moduleLoader.disable();
        defaultConfig.update();
        logObject.update();
        System.out.println("Goodbye!");
    }

    @SneakyThrows
    public void loadModules() {
        channelObject = new ChannelObject();
        channelObject.loadChannel();

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
        //commandManager.addCommand(new TestCommand("apply", "test Command"));
        commandManager.loadCommands();

        timer.schedule(updateTask, 0, 5 * 1000);

        long tookMillis = System.currentTimeMillis() - start;
        logObject.saveLog(new LogEntry(getGuildId()).logType(new StartUpType(tookMillis)));
        logObject.update();

        int took = Math.toIntExact(tookMillis / 1000);
        int rest = Math.toIntExact(tookMillis % 1000);
        System.out.println(MessageFormat.format("Loading complete! (Took {1},{2}s)", DiscordBot.INSTANCE.getUsers().size(), took, rest));
    }

    @Override
    public String getGuildId() {
        return defaultConfig.getValue(DefaultConfigValue.GUILD_ID);
    }

    @Override
    public List<IDiscordUser> getUsers() {
        return discordUsers;
    }

    @Override
    public IDiscordUser getUser(long id) {
        return discordUsers.stream().filter(iDiscordUser -> iDiscordUser.getId() == id).findFirst().orElse(null);
    }

    @Override
    public <T> T getUser(long id, Class<T> tClass) {
        if (getUser(id) == null) return null;
        return getUser(id).getPlayer(tClass);
    }

    @Override
    public <T> List<T> getUsers(Class<T> tClass) {
        return getUsers().stream().filter(iDiscordUser -> iDiscordUser.getPlayer(tClass) != null).map(iDiscordUser -> iDiscordUser.getPlayer(tClass)).collect(Collectors.toList());
    }

    @Override
    public IConfigObject getNewConfigObject() {
        return new ConfigObject();
    }

}
