package eu.nicokempe.discordbot.update;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class UpdateTask extends TimerTask {

    public List<Runnable> tasks = new ArrayList<>();

    @Override
    public void run() {
        tasks.forEach(Runnable::run);
    }

    public void addTask(Runnable runnable) {
        tasks.add(runnable);
    }
}
