package eu.nicokempe.discordbot.animation;

import eu.nicokempe.discordbot.user.IDiscordUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

@Getter
@RequiredArgsConstructor
public abstract class AbstractAnimation implements Runnable {

    private final Supplier<String> headerSupplier;
    private final Supplier<String> footerSupplier;
    private final Supplier<String> descriptionSupplier;
    private final IDiscordUser discordPlayer;
    private final Collection<Runnable> finishHandler = new ArrayList<>();
    public final long timeOut = TimeUnit.MINUTES.toMillis(3);
    public long lastResponse = System.currentTimeMillis();
    public Queue<QuestionListEntry<?>> entries = new LinkedBlockingQueue<>();
    public final Collection<BiConsumer<QuestionListEntry<?>, Object>> entryCompletionListeners = new ArrayList<>();

    public static final int VALID = 0;
    public static final int INVALID_EMAIL = 1;
    public static final int NOT_A_NUMBER = 3;
    public static final int LIST_NOT_CONTAINS = 4;
    public static final int ANSWER_EMPTY = 5;
    public static final int NUMBER_OUT_OF_RANGE = 6;

    private int updateInterval = 25;
    private long startTime;

    public abstract <T> void addEntry(QuestionListEntry<T> entry);

    public abstract void addEntriesAfter(String keyBefore, QuestionListEntry<?>... entries);

    public abstract void addEntriesBefore(String keyAfter, QuestionListEntry<?>... entries);

    public abstract Object getResult(String key);

    public void addFinishHandler(Runnable finishHandler) {
        this.finishHandler.add(finishHandler);
    }

    public void addEntryCompletionListener(BiConsumer<QuestionListEntry<?>, Object> listener) {
        this.entryCompletionListeners.add(listener);
    }

    protected abstract boolean handleTick();

    public void start() {
        /*discordPlayer.sendMessage(headerSupplier.get());
        discordPlayer.sendMessage(footerSupplier.get());*/

        MessageEmbed embed = new EmbedBuilder().setTitle(headerSupplier.get()).setFooter(footerSupplier.get()).setColor(Color.cyan).setDescription(descriptionSupplier.get()).build();
        discordPlayer.sendPrivateMessage(embed);

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        this.startTime = System.currentTimeMillis();
        if (((lastResponse + timeOut) > System.currentTimeMillis())) {
            while (!Thread.interrupted() && !handleTick()) {
                try {
                    Thread.sleep(this.updateInterval);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }

            for (Runnable runnable : finishHandler) {
                runnable.run();
            }
        } else {
            discordPlayer.sendPrivateMessage("You took too long!");
        }
    }

    public abstract Map<String, Object> getResults();

}