package eu.nicokempe.discordbot.animation.asnwer;

import eu.nicokempe.discordbot.animation.AbstractAnimation;

import java.util.Collection;

public class QuestionAnswerTypeString implements QuestionAnswerType<String>{
    @Override
    public int isValidInput(String input) {
        return AbstractAnimation.VALID;
    }

    @Override
    public String parse(String input) {
        return input;
    }

    @Override
    public Collection<String> getPossibleAnswers() {
        return null;
    }
}