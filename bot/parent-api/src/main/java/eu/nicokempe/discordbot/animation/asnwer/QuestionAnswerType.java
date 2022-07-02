package eu.nicokempe.discordbot.animation.asnwer;

import java.util.Collection;
import java.util.List;

public interface QuestionAnswerType<T> {

    int isValidInput(String input);

    T parse(String input);

    Collection<String> getPossibleAnswers();

    default String getPossibleAnswersAsString() {
        Collection<String> possibleAnswers = this.getPossibleAnswers();
        return possibleAnswers != null ? String.join(", ", possibleAnswers) : null;
    }

    default String getRecommendation() {
        return null;
    }

    default List<String> getCompletableAnswers() {
        return null;
    }
}