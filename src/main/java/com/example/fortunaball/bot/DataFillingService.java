package com.example.fortunaball.bot;

import com.example.fortunaball.entities.Chat;
import com.example.fortunaball.entities.ChatFeature;
import com.example.fortunaball.entities.mailing.Advice;
import com.example.fortunaball.entities.mailing.ChatAdvice;
import com.example.fortunaball.entities.mailing.ChatJoke;
import com.example.fortunaball.entities.mailing.ChatMeme;
import com.example.fortunaball.entities.mailing.Joke;
import com.example.fortunaball.entities.mailing.Meme;
import com.example.fortunaball.services.ChatFeatureService;
import com.example.fortunaball.services.ChatService;
import com.example.fortunaball.services.mailing.AdviceService;
import com.example.fortunaball.services.mailing.ChatAdviceService;
import com.example.fortunaball.services.mailing.ChatJokeService;
import com.example.fortunaball.services.mailing.ChatMemeService;
import com.example.fortunaball.services.mailing.JokeService;
import com.example.fortunaball.services.mailing.MemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DataFillingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataFillingService.class);

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

    @Autowired
    private ChatService chatService;


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

    public void refreshContent() {
        LOGGER.info("All data content refresh started, time: {} ", new Date());
        final List<Chat> activeChats = chatService.getAllChats();
        saveNewChatAdvices(activeChats);
        saveNewChatJokes(activeChats);
        saveNewChatMemes(activeChats);
    }

    private void saveNewChatAdvices(final List<Chat> activeChats) {
        final Set<Long> chatAdviceIds = chatAdviceService.getAll().stream().map(ChatAdvice::getAdviceId).collect(Collectors.toSet());
        final Set<Long> adviceIds = adviceService.getAllAdvices().stream().map(Advice::getId).collect(Collectors.toSet());
        adviceIds.removeAll(chatAdviceIds);

        adviceIds.forEach(adviceId -> activeChats.forEach(chat -> {
            final long chatId = chat.getId();
            final ChatAdvice chatAdvice = new ChatAdvice();
            chatAdvice.setChatId(chatId);
            chatAdvice.setAdviceId(adviceId);
            chatAdvice.setUsed(Boolean.FALSE);
            chatAdviceService.save(chatAdvice);
        }));
    }

    private void saveNewChatJokes(final List<Chat> activeChats) {
        final Set<Long> chatJokeIds = chatJokeService.getAll().stream().map(ChatJoke::getJokeId).collect(Collectors.toSet());
        final Set<Long> jokeIds = jokeService.getAllJokes().stream().map(Joke::getId).collect(Collectors.toSet());
        jokeIds.removeAll(chatJokeIds);

        jokeIds.forEach(jokeId -> activeChats.forEach(chat -> {
            final long chatId = chat.getId();
            final ChatJoke chatJoke = new ChatJoke();
            chatJoke.setChatId(chatId);
            chatJoke.setJokeId(jokeId);
            chatJoke.setUsed(Boolean.FALSE);
            chatJokeService.save(chatJoke);
        }));
    }

    private void saveNewChatMemes(final List<Chat> activeChats) {
        final Set<Long> chatMemeIds = chatMemeService.getAll().stream().map(ChatMeme::getChatId).collect(Collectors.toSet());
        final Set<Long> memeIds = memeService.getAllMemes().stream().map(Meme::getId).collect(Collectors.toSet());
        memeIds.removeAll(chatMemeIds);

        memeIds.forEach(memeId -> activeChats.forEach(chat -> {
            final long chatId = chat.getId();
            final ChatMeme chatMeme = new ChatMeme();
            chatMeme.setChatId(chatId);
            chatMeme.setMemeId(memeId);
            chatMeme.setUsed(Boolean.FALSE);
            chatMemeService.save(chatMeme);
        }));
    }
}
