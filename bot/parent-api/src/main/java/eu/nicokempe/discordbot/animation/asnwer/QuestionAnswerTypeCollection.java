package eu.nicokempe.discordbot.animation.asnwer;

import eu.nicokempe.discordbot.animation.AbstractAnimation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class QuestionAnswerTypeCollection implements QuestionAnswerType<Collection<String>> {

    private Collection<String> possibleAnswers;
    private boolean allowEmpty = true;

    public QuestionAnswerTypeCollection(Collection<String> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    public QuestionAnswerTypeCollection() {
    }

    public QuestionAnswerTypeCollection disallowEmpty() {
        this.allowEmpty = false;
        return this;
    }

    @Override
    public int isValidInput(String input) {
        if (!this.allowEmpty && input.trim().isEmpty()) {
            return AbstractAnimation.ANSWER_EMPTY;
        }
        return this.possibleAnswers == null || possibleAnswers.stream().anyMatch(s -> s.equalsIgnoreCase(input)) ? AbstractAnimation.VALID : AbstractAnimation.LIST_NOT_CONTAINS;
    }

    @Override
    public Collection<String> parse(String input) {
        return new ArrayList<>(Arrays.asList(input.split(";")));
    }

    @Override
    public List<String> getCompletableAnswers() {
        return this.possibleAnswers != null ? new ArrayList<>(this.possibleAnswers) : null;
    }

    @Override
    public Collection<String> getPossibleAnswers() {
        return this.possibleAnswers;
    }
}