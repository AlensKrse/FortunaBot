package com.example.fortunaball.services;

import com.example.fortunaball.enums.FortunaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class FortunaBallAnswerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FortunaBallAnswerService.class);

    private final Random random = SecureRandom.getInstanceStrong();
    private static final int FORTUNE_TYPES_SIZE = 4;
    private static final int FORTUNE_ANSWERS_SIZE = 5;

    private final List<String> positiveAnswers = new ArrayList<>(List.of("Бесспорно", "Предрешено", "Никаких сомнений", "Определённо да", "Можешь быть уверен в этом"));
    private final List<String> hesitantlyPositiveAnswers = new ArrayList<>(List.of("Мне кажется — «да»", "Вероятнее всего", "Хорошие перспективы", "Знаки говорят — «да»", "Да"));
    private final List<String> neutralAnswers = new ArrayList<>(List.of("Пока не ясно, попробуй снова", "Спроси позже", "Лучше не рассказывать", "Сейчас нельзя предсказать", "Сконцентрируйся и спроси опять"));
    private final List<String> negativeAnswers = new ArrayList<>(List.of("Даже не думай", "Мой ответ — «нет»", "По моим данным — «нет»", "Перспективы не очень хорошие", "Весьма сомнительно"));

    private final List<String> positiveSmiles = new ArrayList<>(List.of("\uD83D\uDE03", "\uD83D\uDE0D", "\uD83D\uDE0A", "☺️", "\uD83D\uDE0F", "\uD83D\uDE09", "\uD83D\uDE0E", "\uD83E\uDD29"));
    private final List<String> hesitantlyPositiveSmiles = new ArrayList<>(List.of("\uD83D\uDE05", "\uD83D\uDE42", "\uD83E\uDD2B", "\uD83D\uDE43", "\uD83D\uDE3C", "\uD83D\uDC40", "\uD83E\uDD74", "\uD83D\uDE33"));
    private final List<String> neutralSmiles = new ArrayList<>(List.of("\uD83E\uDD28", "\uD83E\uDDD0", "\uD83E\uDD2D", "\uD83E\uDD25", "\uD83D\uDE36", "\uD83D\uDE13", "\uD83D\uDE15", "\uD83D\uDE36\u200D\uD83C\uDF2B️"));
    private final List<String> negativeSmiles = new ArrayList<>(List.of("\uD83D\uDE2B", "\uD83D\uDE2D", "\uD83D\uDE21", "\uD83E\uDD2F", "\uD83E\uDD2C", "\uD83E\uDD22", "\uD83D\uDCA9", "\uD83D\uDC7A"));

    private final String ERROR_CASE_ANSWER = "Ответить на этот вопрос не по силен ни кто!";
    private final String ERROR_CASE_SMILE = "☠️";

    public FortunaBallAnswerService() throws NoSuchAlgorithmException {
        // TODO document why this constructor is empty
    }

    public String getFortuneBallAnswer() {
        final int randomNumber = random.nextInt(FORTUNE_TYPES_SIZE);
        final int answerFortuna = random.nextInt(FORTUNE_ANSWERS_SIZE);

        LOGGER.info("Lets make some magic, random number for answer type is {} and random number of answer is {}", randomNumber, answerFortuna);

        final Optional<FortunaType> optionalFortunaType = FortunaType.getTypeById(randomNumber);

        final String answer;
        final String smile;
        if (optionalFortunaType.isPresent()) {
            final FortunaType fortunaType = optionalFortunaType.get();
            answer = getAnswer(answerFortuna, fortunaType);
            smile = getSmile(answerFortuna, fortunaType);
        } else {
            LOGGER.error("Some problem with random number: {}", randomNumber);
            answer = ERROR_CASE_ANSWER;
            smile = ERROR_CASE_SMILE;
        }

        return answer + " " + smile;
    }

    private String getAnswer(final int answerFortuna, final FortunaType fortunaType) {
        final String answer;
        if (fortunaType == FortunaType.POSITIVE_ANSWER) {
            answer = positiveAnswers.get(answerFortuna);
        } else if (fortunaType == FortunaType.HESITANTLY_POSITIVE_ANSWER) {
            answer = hesitantlyPositiveAnswers.get(answerFortuna);
        } else if (fortunaType == FortunaType.NEUTRAL_ANSWER) {
            answer = neutralAnswers.get(answerFortuna);
        } else if (fortunaType == FortunaType.NEGATIVE_ANSWER) {
            answer = negativeAnswers.get(answerFortuna);
        } else {
            answer = ERROR_CASE_ANSWER;
        }

        return answer;
    }

    private String getSmile(final int answerFortuna, final FortunaType fortunaType) {
        final String smile;
        if (fortunaType == FortunaType.POSITIVE_ANSWER) {
            smile = positiveSmiles.get(answerFortuna);
        } else if (fortunaType == FortunaType.HESITANTLY_POSITIVE_ANSWER) {
            smile = hesitantlyPositiveSmiles.get(answerFortuna);
        } else if (fortunaType == FortunaType.NEUTRAL_ANSWER) {
            smile = neutralSmiles.get(answerFortuna);
        } else if (fortunaType == FortunaType.NEGATIVE_ANSWER) {
            smile = negativeSmiles.get(answerFortuna);
        } else {
            smile = ERROR_CASE_SMILE;
        }

        return smile;
    }
}
