package eu.nicokempe.discordbot.animation.answer;

import eu.nicokempe.discordbot.animation.AbstractAnimation;

import java.util.Collection;

public class QuestionAnswerTypeInt implements QuestionAnswerType<Integer> {

    @Override
    public int isValidInput(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return AbstractAnimation.NOT_A_NUMBER;
        }
        return AbstractAnimation.VALID;
    }

    @Override
    public Integer parse(String input) {
        return Integer.parseInt(input);
    }

    @Override
    public Collection<String> getPossibleAnswers() {
        return null;
    }

}