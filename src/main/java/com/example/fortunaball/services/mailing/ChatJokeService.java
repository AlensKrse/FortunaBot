package com.example.fortunaball.services.mailing;

import com.example.fortunaball.entities.mailing.ChatJoke;
import com.example.fortunaball.repositories.mailing.ChatJokeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ChatJokeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatJokeService.class);

    @Autowired
    private ChatJokeRepository chatJokeRepository;

    @Transactional(readOnly = true)
    public List<ChatJoke> getValidJokesByChatIdAndIsNotUsed(final long chatId) {
        return chatJokeRepository.findByChatIdAndIsUsedFalse(chatId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void setJokeIsUsedForChatId(final long chatId, final long jokeId) {
        final Optional<ChatJoke> optionalChatJoke = chatJokeRepository.findByIdAndChatId(jokeId, chatId);
        optionalChatJoke.ifPresentOrElse(chatJoke -> {
            chatJoke.setUsed(Boolean.TRUE);
            chatJokeRepository.save(chatJoke);
        }, () -> {
            LOGGER.error("Chat joke not found after mailing message sending by chat id: {} and joke id: {}", chatId, jokeId);
            throw new UnsupportedOperationException(String.format("Chat joke not found after mailing message sending by chat id: %d and joke id: %d", chatId, jokeId));
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ChatJoke> saveAllChatJokes(final List<ChatJoke> chatJokes) {
        return chatJokeRepository.saveAll(chatJokes);
    }
}
