package eu.nicokempe.discordbot.server.api.commands;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface ICommandController {

    @GetMapping(path = "/commands", produces = "application/json")
    List<CustomCommand> getCommands();
}
