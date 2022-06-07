package eu.nicokempe.discordbot;

public class Main {

    public static void main(String[] args) {
        IDiscordBot bot = new DiscordBot();
        bot.enable();
        Runtime.getRuntime().addShutdownHook(new Thread(bot::disable));
    }

}
