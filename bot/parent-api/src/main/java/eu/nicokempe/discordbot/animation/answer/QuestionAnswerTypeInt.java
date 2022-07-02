package eu.nicokempe.discordbot.animation.answer;

import eu.nicokempe.discordbot.animation.AbstractAnimation;
import lombok.Getter;

import java.util.Collection;

@Getter
public class QuestionAnswerTypeInt implements QuestionAnswerType<Integer> {

    private int max = 0;
    private int min = 0;

    @Override
    public int isValidInput(String input) {
        try {
            int i = Integer.parseInt(input);
            if (min != 0 && i < min || max != 0 && i > max) return AbstractAnimation.NUMBER_OUT_OF_RANGE;
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

    public QuestionAnswerTypeInt range(int min, int max) {
        this.min = min;
        this.max = max;
        return this;
    }

}