package eu.nicokempe.discordbot.logger;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

public class Logger {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy | HH:mm:ss");

    public Logger() {

        System.setOut(new PrintStream(System.out) {

            @Override
            public void println(String x) {
                String message = MessageFormat.format("[{0}] " + x, dateFormat.format(System.currentTimeMillis()));
                super.println(message);
            }

            @Override
            public void println(int x) {
                println(String.valueOf(x));
            }
        });
    }

}