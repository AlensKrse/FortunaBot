package com.example.fortunaball.bot;

import com.example.fortunaball.entities.Chat;
import com.example.fortunaball.entities.ChatFeature;
import com.example.fortunaball.entities.mailing.Advice;
import com.example.fortunaball.entities.mailing.ChatAdvice;
import com.example.fortunaball.entities.mailing.ChatJoke;
import com.example.fortunaball.entities.mailing.ChatMeme;
import com.example.fortunaball.entities.mailing.Joke;
import com.example.fortunaball.entities.mailing.Meme;
import com.example.fortunaball.enums.MailingType;
import com.example.fortunaball.repositories.ChatFeaturesRepository;
import com.example.fortunaball.repositories.ChatRepository;
import com.example.fortunaball.repositories.mailing.AdviceRepository;
import com.example.fortunaball.repositories.mailing.ChatAdviceRepository;
import com.example.fortunaball.repositories.mailing.ChatJokeRepository;
import com.example.fortunaball.repositories.mailing.ChatMemeRepository;
import com.example.fortunaball.repositories.mailing.JokeRepository;
import com.example.fortunaball.repositories.mailing.MemeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Transactional
class BotTest {

    private static final long CHAT_ID = 123456L;

    @Autowired
    private MailingService mailingService;

    @Autowired
    private ChatFeaturesRepository chatFeaturesRepository;

    @Autowired
    private ChatAdviceRepository chatAdviceRepository;

    @Autowired
    private ChatJokeRepository chatJokeRepository;

    @Autowired
    private ChatMemeRepository chatMemeRepository;

    @Autowired
    private AdviceRepository adviceRepository;

    @Autowired
    private JokeRepository jokeRepository;

    @Autowired
    private MemeRepository memeRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Test
    void testSuccessfulPathSendAdviceMessage() {
        final Chat chat = new Chat();
        chat.setId(CHAT_ID);
        chat.setActive(Boolean.TRUE);
        chatRepository.save(chat);

        final ChatFeature chatFeature = new ChatFeature();
        chatFeature.setChatId(CHAT_ID);
        chatFeature.setAdvicesActive(true);
        chatFeaturesRepository.save(chatFeature);

        final Advice advice = new Advice();
        advice.setText("Advice Test Text");
        final Advice savedAdvice = adviceRepository.save(advice);

        final ChatAdvice chatAdvice = new ChatAdvice();
        chatAdvice.setChatId(CHAT_ID);
        chatAdvice.setAdviceId(savedAdvice.getId());
        chatAdvice.setUsed(false);
        chatAdviceRepository.save(chatAdvice);

        mailingService.sendMailingMessage(MailingType.ADVICE);
    }

    @Test
    void testSuccessfulPathSendJokeMessage() {
        final Chat chat = new Chat();
        chat.setId(CHAT_ID);
        chat.setActive(Boolean.TRUE);
        chatRepository.save(chat);

        final ChatFeature chatFeature = new ChatFeature();
        chatFeature.setChatId(CHAT_ID);
        chatFeature.setJokesActive(true);
        chatFeaturesRepository.save(chatFeature);

        final Joke joke = new Joke();
        joke.setText("Joke Test Text");
        final Joke savedJoke = jokeRepository.save(joke);

        final ChatJoke chatJoke = new ChatJoke();
        chatJoke.setChatId(CHAT_ID);
        chatJoke.setJokeId(savedJoke.getId());
        chatJoke.setUsed(false);
        chatJokeRepository.save(chatJoke);

        mailingService.sendMailingMessage(MailingType.JOKE);
    }

    @Test
    void testSuccessfulPathSendMemeMessage() {
        final Chat chat = new Chat();
        chat.setId(CHAT_ID);
        chat.setActive(Boolean.TRUE);
        chatRepository.save(chat);

        final ChatFeature chatFeature = new ChatFeature();
        chatFeature.setChatId(CHAT_ID);
        chatFeature.setMemesActive(true);
        chatFeaturesRepository.save(chatFeature);

        final Meme meme = new Meme();
        meme.setText("Meme Test Text");
        final Meme savedMeme = memeRepository.save(meme);

        final ChatMeme chatMeme = new ChatMeme();
        chatMeme.setChatId(CHAT_ID);
        chatMeme.setMemeId(savedMeme.getId());
        chatMeme.setUsed(false);
        chatMemeRepository.save(chatMeme);

        mailingService.sendMailingMessage(MailingType.MEME);
    }

}