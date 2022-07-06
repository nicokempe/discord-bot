package eu.nicokempe.discordbot.animation;

import eu.nicokempe.discordbot.animation.answer.QuestionAnswerType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class QuestionListEntry<T> {

    private final String key;
    private final String question;
    private final QuestionAnswerType<T> answerType;

}