package eu.nicokempe.discordbot.animation;

import eu.nicokempe.discordbot.animation.asnwer.QuestionAnswerType;
import eu.nicokempe.discordbot.animation.task.ITask;
import eu.nicokempe.discordbot.animation.task.ListenableTask;
import eu.nicokempe.discordbot.command.manager.CommandManager;
import eu.nicokempe.discordbot.user.IDiscordUser;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

@Getter
public class QuestionListAnimation extends AbstractAnimation {

    private final Map<String, Object> results = new HashMap<>();
    private final Collection<Runnable> finishHandler = new ArrayList<>();

    public QuestionListAnimation(Supplier<String> headerSupplier, Supplier<String> footerSupplier, Supplier<String> descriptionSupplier, IDiscordUser discordPlayer) {
        super(headerSupplier, footerSupplier, descriptionSupplier, discordPlayer);
    }

    @Override
    public <T> void addEntry(QuestionListEntry<T> entry) {
        entries.add(entry);
    }

    @Override
    public Object getResult(String key) {
        if (!results.containsKey(key)) return null;
        return this.results.get(key);
    }

    @Override
    protected boolean handleTick() {
        QuestionListEntry<?> entry;

        if (this.entries.isEmpty() || (entry = this.entries.poll()) == null) {
            return true;
        }

        QuestionAnswerType<?> answerType = entry.getAnswerType();

        String possibleAnswers = answerType.getPossibleAnswersAsString();
        getDiscordPlayer().sendPrivateMessage(entry.getQuestion());
        if (possibleAnswers != null) {
            getDiscordPlayer().sendPrivateMessage("Mögliche Antworten:");
            getDiscordPlayer().sendPrivateMessage("```" + possibleAnswers + "```");
        }

        ITask<Void> task = new ListenableTask<>(() -> null);

        getDiscordPlayer().requestAnswer(s -> {
            if (validateInput(answerType, entry, s)) {
                getDiscordPlayer().requestAnswer(null);
                lastResponse = System.currentTimeMillis();
                try {
                    task.call();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        /*CommandManager.addCommandHandler(handlerId, container -> {
            if (container.getEvent().getAuthor().equals(getDiscordPlayer().getUser()))
                if (validateInput(answerType, entry, container.getRaw())) {
                    CommandManager.getHandler().remove(handlerId);
                    lastResponse = System.currentTimeMillis();
                    try {
                        task.call();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
        });*/

        try {
            task.get();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }
    public void addEntryCompletionListener(BiConsumer<QuestionListEntry<?>, Object> listener) {
        this.entryCompletionListeners.add(listener);
    }

    @Override
    public void addEntriesAfter(String keyBefore, QuestionListEntry<?>... entries) {
        if (entries.length == 0) {
            return;
        }
        Queue<QuestionListEntry<?>> newEntries = new LinkedBlockingQueue<>();
        for (QuestionListEntry<?> oldEntry : this.entries) {
            newEntries.add(oldEntry);
            if (oldEntry.getKey().equals(keyBefore)) {
                newEntries.addAll(Arrays.asList(entries));
            }
        }
        this.entries = newEntries;
    }

    @Override
    public void addEntriesBefore(String keyAfter, QuestionListEntry<?>... entries) {
        if (entries.length == 0) {
            return;
        }
        Queue<QuestionListEntry<?>> newEntries = new LinkedBlockingQueue<>();
        for (QuestionListEntry<?> oldEntry : this.entries) {
            if (oldEntry.getKey().equals(keyAfter)) {
                newEntries.addAll(Arrays.asList(entries));
            }
            newEntries.add(oldEntry);
        }
        this.entries = newEntries;
    }

    private boolean validateInput(QuestionAnswerType<?> answerType, QuestionListEntry<?> entry, String input) {
        if (input.equalsIgnoreCase("cancel")) {
            this.entries.clear();
            return true;
        }

        int valid = answerType.isValidInput(input);
        if (valid == VALID) {
            Object result = answerType.parse(input);
            this.results.put(entry.getKey(), result);
            for (BiConsumer<QuestionListEntry<?>, Object> listener : this.entryCompletionListeners) {
                listener.accept(entry, result);
            }
            return true;
        } else {
            switch (valid) {
                case INVALID_EMAIL ->
                        getDiscordPlayer().sendPrivateMessage("Diese Email konnte nicht gefunden werden!");
                case LIST_NOT_CONTAINS ->
                        getDiscordPlayer().sendPrivateMessage("Diese Antwortmöglichkeit gibt es nicht!");
                case NOT_A_NUMBER -> getDiscordPlayer().sendPrivateMessage("Bitte gebe eine echte Zahl an!");
                case ANSWER_EMPTY -> getDiscordPlayer().sendPrivateMessage("Bitte gebe eine richte Antwort an!");
            }
            return false;
        }

    }

    public boolean hasResult(String key) {
        return this.results.containsKey(key);
    }
}