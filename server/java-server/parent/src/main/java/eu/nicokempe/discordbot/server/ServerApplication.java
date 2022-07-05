package eu.nicokempe.discordbot.server;

import eu.nicokempe.discordbot.server.api.IServerApplication;
import eu.nicokempe.discordbot.server.api.commands.CommandRepository;
import eu.nicokempe.discordbot.server.api.config.JsonConfig;
import eu.nicokempe.discordbot.server.api.config.ConfigRepository;
import eu.nicokempe.discordbot.server.api.webuser.WebUserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.Collections;

@Getter
@SpringBootApplication
public class ServerApplication implements IServerApplication {

    public static ServerApplication INSTANCE;

    public static final JsonConfig CONFIG = new JsonConfig(new File("../config.json"));

    public ServerApplication() {
        INSTANCE = this;
    }

    @Autowired
    private WebUserRepository repository;
    @Autowired
    private ConfigRepository configRepository;
    @Autowired
    private CommandRepository commandRepository;

    public static void main(String[] args) {
        int port = Integer.parseInt(CONFIG.getOrDefaultSet("host", String.class, "http://127.0.0.1:8081/api/").split(":")[2].split("/")[0]);

        SpringApplication app = new SpringApplication(ServerApplication.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", port));
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

        INSTANCE.enable();
    }

    @Override
    public void enable() {

    }
}
