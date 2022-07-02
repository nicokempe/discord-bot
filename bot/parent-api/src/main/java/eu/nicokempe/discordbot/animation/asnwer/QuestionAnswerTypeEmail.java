package eu.nicokempe.discordbot.animation.asnwer;

import eu.nicokempe.discordbot.animation.AbstractAnimation;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Collection;

public class QuestionAnswerTypeEmail implements QuestionAnswerType<String> {

    @Override
    public int isValidInput(String email) {
        return EmailValidator.getInstance().isValid(email) ? AbstractAnimation.VALID : AbstractAnimation.INVALID_EMAIL;
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