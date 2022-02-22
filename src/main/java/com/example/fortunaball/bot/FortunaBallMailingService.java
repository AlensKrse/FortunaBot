package com.example.fortunaball.bot;

import com.example.fortunaball.entities.mailing.Advice;
import com.example.fortunaball.entities.mailing.ChatAdvice;
import com.example.fortunaball.entities.mailing.ChatJoke;
import com.example.fortunaball.entities.mailing.ChatMeme;
import com.example.fortunaball.entities.mailing.Joke;
import com.example.fortunaball.entities.mailing.Meme;
import com.example.fortunaball.services.mailing.AdviceService;
import com.example.fortunaball.services.mailing.ChatAdviceService;
import com.example.fortunaball.services.mailing.ChatJokeService;
import com.example.fortunaball.services.mailing.ChatMemeService;
import com.example.fortunaball.services.mailing.JokeService;
import com.example.fortunaball.services.mailing.MemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FortunaBallMailingService {

    private static final String ERROR_MESSAGE = "Out of %s! Need to refill!";

    @Autowired
    private ChatAdviceService chatAdviceService;

    @Autowired
    private AdviceService adviceService;

    @Autowired
    private ChatJokeService chatJokeService;

    @Autowired
    private JokeService jokeService;

    @Autowired
    private ChatMemeService chatMemeService;

    @Autowired
    private MemeService memeService;


    public Advice getRandomAdvice(final long chatId) {
        final List<ChatAdvice> validAdvices = chatAdviceService.getValidAdvicesByChatIdAndIsNotUsed(chatId);
        final List<Long> adviceIds = validAdvices.stream().map(ChatAdvice::getAdviceId).collect(Collectors.toList());
        final Optional<Advice> optionalAdvice = Optional.ofNullable(adviceService.findFirstByIdIn(adviceIds));

        return optionalAdvice.orElseThrow(() -> {
            throw new UnsupportedOperationException(String.format(ERROR_MESSAGE, "advices"));
        });
    }

    public Joke getRandomJoke(final long chatId) {
        final List<ChatJoke> validJokes = chatJokeService.getValidJokesByChatIdAndIsNotUsed(chatId);
        final List<Long> jokeIds = validJokes.stream().map(ChatJoke::getJokeId).collect(Collectors.toList());
        final Optional<Joke> optionalJoke = Optional.ofNullable(jokeService.findFirstByIdIn(jokeIds));

        return optionalJoke.orElseThrow(() -> {
            throw new UnsupportedOperationException(String.format(ERROR_MESSAGE, "jokes"));
        });
    }

    public Meme getRandomMeme(final long chatId) {
        final List<ChatMeme> validMemes = chatMemeService.getValidMemesByChatIdAndIsNotUsed(chatId);
        final List<Long> memeIds = validMemes.stream().map(ChatMeme::getMemeId).collect(Collectors.toList());
        final Optional<Meme> optionalMeme = Optional.ofNullable(memeService.findFirstByIdIn(memeIds));

        return optionalMeme.orElseThrow(() -> {
            throw new UnsupportedOperationException(String.format(ERROR_MESSAGE, "memes"));
        });
    }

    public void setAdviceIsUsedForChatId(final long chatId, final long adviceId) {
        chatAdviceService.setAdviceIsUsedForChatId(chatId, adviceId);
    }

    public void setJokeIsUsedForChatId(final long chatId, final long jokeId) {
        chatJokeService.setJokeIsUsedForChatId(chatId, jokeId);
    }

    public void setMemeIsUsedForChatId(final long chatId, final long memeId) {
        chatMemeService.setMemeIsUsedForChatId(chatId, memeId);
    }
}
