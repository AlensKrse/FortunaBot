package com.example.fortunaball.bot;

import com.example.fortunaball.entities.ChatFeature;
import com.example.fortunaball.entities.mailing.Advice;
import com.example.fortunaball.entities.mailing.ChatAdvice;
import com.example.fortunaball.entities.mailing.ChatJoke;
import com.example.fortunaball.entities.mailing.ChatMeme;
import com.example.fortunaball.entities.mailing.Joke;
import com.example.fortunaball.entities.mailing.Meme;
import com.example.fortunaball.services.ChatFeatureService;
import com.example.fortunaball.services.mailing.AdviceService;
import com.example.fortunaball.services.mailing.ChatAdviceService;
import com.example.fortunaball.services.mailing.ChatJokeService;
import com.example.fortunaball.services.mailing.ChatMemeService;
import com.example.fortunaball.services.mailing.JokeService;
import com.example.fortunaball.services.mailing.MemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataFillingService {

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


    @Transactional(rollbackFor = Exception.class)
    public void addMailingDataToChatId(final long chatId) {
        createChatFeature(chatId);
        createChatAdvices(chatId);
        createChatJokes(chatId);
        createChatMemes(chatId);
    }

    private void createChatFeature(final long chatId) {
        final ChatFeature chatFeature = new ChatFeature();
        chatFeature.setChatId(chatId);
        chatFeature.setAdvicesActive(Boolean.TRUE);
        chatFeature.setJokesActive(Boolean.TRUE);
        chatFeature.setMemesActive(Boolean.TRUE);
        chatFeatureService.saveChatFeature(chatFeature);
    }

    private void createChatAdvices(final long chatId) {
        final List<Advice> advices = adviceService.getAllAdvices();
        final List<ChatAdvice> chatAdvices = advices.stream().map(advice -> {
            final ChatAdvice chatAdvice = new ChatAdvice();
            chatAdvice.setChatId(chatId);
            chatAdvice.setAdviceId(advice.getId());
            chatAdvice.setUsed(Boolean.FALSE);

            return chatAdvice;
        }).collect(Collectors.toList());
        chatAdviceService.saveAllChatAdvices(chatAdvices);
    }

    private void createChatJokes(final long chatId) {
        final List<Joke> jokes = jokeService.getAllJokes();
        final List<ChatJoke> chatJokes = jokes.stream().map(joke -> {
            final ChatJoke chatJoke = new ChatJoke();
            chatJoke.setChatId(chatId);
            chatJoke.setJokeId(joke.getId());
            chatJoke.setUsed(Boolean.FALSE);

            return chatJoke;
        }).collect(Collectors.toList());
        chatJokeService.saveAllChatJokes(chatJokes);
    }

    private void createChatMemes(final long chatId) {
        final List<Meme> memes = memeService.getAllMemes();
        final List<ChatMeme> chatMemes = memes.stream().map(meme -> {
            final ChatMeme chatMeme = new ChatMeme();
            chatMeme.setChatId(chatId);
            chatMeme.setMemeId(meme.getId());
            chatMeme.setUsed(Boolean.FALSE);

            return chatMeme;
        }).collect(Collectors.toList());
        chatMemeService.saveAllChatMemes(chatMemes);
    }
}
