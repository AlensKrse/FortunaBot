package com.example.fortunaball.bot;

import com.example.fortunaball.entities.ChatFeature;
import com.example.fortunaball.entities.mailing.Advice;
import com.example.fortunaball.entities.mailing.ChatAdvice;
import com.example.fortunaball.entities.mailing.ChatJoke;
import com.example.fortunaball.entities.mailing.ChatMeme;
import com.example.fortunaball.entities.mailing.Joke;
import com.example.fortunaball.entities.mailing.Meme;
import com.example.fortunaball.enums.MailingType;
import com.example.fortunaball.services.ChatFeatureService;
import com.example.fortunaball.services.mailing.AdviceService;
import com.example.fortunaball.services.mailing.ChatAdviceService;
import com.example.fortunaball.services.mailing.ChatJokeService;
import com.example.fortunaball.services.mailing.ChatMemeService;
import com.example.fortunaball.services.mailing.JokeService;
import com.example.fortunaball.services.mailing.MemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MailingDistributorService {

    private static final String ERROR_MESSAGE = "Out of %s! Need to refill!";

    @Autowired
    private ChatFeatureService chatFeatureService;

    @Autowired
    private ChatAdviceService chatAdviceService;

    @Autowired
    private ChatJokeService chatJokeService;

    @Autowired
    private ChatMemeService chatMemeService;

    @Autowired
    private AdviceService adviceService;

    @Autowired
    private JokeService jokeService;

    @Autowired
    private MemeService memeService;

    public void setMailingMessageIsUsed(final MailingType mailingType, final long chatId, final long mailingId) {
        if (mailingType == MailingType.ADVICE) {
            setAdviceIsUsedForChatId(chatId, mailingId);
        } else if (mailingType == MailingType.JOKE) {
            setJokeIsUsedForChatId(chatId, mailingId);
        } else if (mailingType == MailingType.MEME) {
            setMemeIsUsedForChatId(chatId, mailingId);
        } else {
            throw new UnsupportedOperationException(String.format("Mailing type is undefined with id %d", mailingType.getId()));
        }

    }

    public List<ChatFeature> getChatFeatures(final MailingType mailingType, final List<Long> chats) {
        final List<ChatFeature> chatFeatures;
        if (mailingType == MailingType.ADVICE) {
            chatFeatures = chatFeatureService.findAllWithAdviceActive(chats);
        } else if (mailingType == MailingType.JOKE) {
            chatFeatures = chatFeatureService.findAllWithJokesActive(chats);
        } else if (mailingType == MailingType.MEME) {
            chatFeatures = chatFeatureService.findAllWithMemesActive(chats);
        } else {
            throw new UnsupportedOperationException(String.format("Mailing type is undefined with id %d", mailingType.getId()));
        }
        return chatFeatures;
    }

    public Pair<Long, String> getMailingPair(final MailingType mailingType, final long chatId) {
        final Pair<Long, String> pair;
        if (mailingType == MailingType.ADVICE) {
            final Advice randomAdvice = getRandomAdvice(chatId);
            pair = Pair.of(randomAdvice.getId(), randomAdvice.getText());
        } else if (mailingType == MailingType.JOKE) {
            final Joke randomJoke = getRandomJoke(chatId);
            pair = Pair.of(randomJoke.getId(), randomJoke.getText());
        } else if (mailingType == MailingType.MEME) {
            final Meme randomMeme = getRandomMeme(chatId);
            pair = Pair.of(randomMeme.getId(), randomMeme.getText());
        } else {
            throw new UnsupportedOperationException(String.format("Mailing type is undefined by chatId: %d", chatId));
        }

        return pair;
    }

    private Advice getRandomAdvice(final long chatId) {
        final List<ChatAdvice> validAdvices = chatAdviceService.getValidAdvicesByChatIdAndIsNotUsed(chatId);
        final List<Long> adviceIds = validAdvices.stream().map(ChatAdvice::getAdviceId).collect(Collectors.toList());
        final Optional<Advice> optionalAdvice = Optional.ofNullable(adviceService.findFirstByIdIn(adviceIds));

        return optionalAdvice.orElseThrow(() -> {
            throw new UnsupportedOperationException(String.format(ERROR_MESSAGE, "advices"));
        });
    }

    private Joke getRandomJoke(final long chatId) {
        final List<ChatJoke> validJokes = chatJokeService.getValidJokesByChatIdAndIsNotUsed(chatId);
        final List<Long> jokeIds = validJokes.stream().map(ChatJoke::getJokeId).collect(Collectors.toList());
        final Optional<Joke> optionalJoke = Optional.ofNullable(jokeService.findFirstByIdIn(jokeIds));

        return optionalJoke.orElseThrow(() -> {
            throw new UnsupportedOperationException(String.format(ERROR_MESSAGE, "jokes"));
        });
    }

    private Meme getRandomMeme(final long chatId) {
        final List<ChatMeme> validMemes = chatMemeService.getValidMemesByChatIdAndIsNotUsed(chatId);
        final List<Long> memeIds = validMemes.stream().map(ChatMeme::getMemeId).collect(Collectors.toList());
        final Optional<Meme> optionalMeme = Optional.ofNullable(memeService.findFirstByIdIn(memeIds));

        return optionalMeme.orElseThrow(() -> {
            throw new UnsupportedOperationException(String.format(ERROR_MESSAGE, "memes"));
        });
    }


    private void setAdviceIsUsedForChatId(final long chatId, final long adviceId) {
        chatAdviceService.setAdviceIsUsedForChatId(chatId, adviceId);
    }

    private void setJokeIsUsedForChatId(final long chatId, final long jokeId) {
        chatJokeService.setJokeIsUsedForChatId(chatId, jokeId);
    }

    private void setMemeIsUsedForChatId(final long chatId, final long memeId) {
        chatMemeService.setMemeIsUsedForChatId(chatId, memeId);
    }
}
