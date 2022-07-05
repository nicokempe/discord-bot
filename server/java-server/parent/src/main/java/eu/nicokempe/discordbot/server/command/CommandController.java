package eu.nicokempe.discordbot.server.command;

import eu.nicokempe.discordbot.server.ServerApplication;
import eu.nicokempe.discordbot.server.api.commands.CustomCommand;
import eu.nicokempe.discordbot.server.api.commands.ICommandController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommandController implements ICommandController {

    @Override
    public List<CustomCommand> getCommands() {
        return ServerApplication.INSTANCE.getCommandRepository().findAll();
    }
}
