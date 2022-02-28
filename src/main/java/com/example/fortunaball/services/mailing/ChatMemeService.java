package com.example.fortunaball.services.mailing;

import com.example.fortunaball.entities.mailing.ChatMeme;
import com.example.fortunaball.repositories.mailing.ChatMemeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ChatMemeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatMemeService.class);

    @Autowired
    private ChatMemeRepository chatMemeRepository;

    @Transactional(readOnly = true)
    public List<ChatMeme> getValidMemesByChatIdAndIsNotUsed(final long chatId) {
        return chatMemeRepository.findByChatIdAndIsUsedFalse(chatId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void setMemeIsUsedForChatId(final long chatId, final long memeId) {
        final Optional<ChatMeme> optionalChatMeme = chatMemeRepository.findByMemeIdAndChatId(memeId, chatId);
        optionalChatMeme.ifPresentOrElse(chatMeme -> {
            chatMeme.setUsed(Boolean.TRUE);
            chatMemeRepository.save(chatMeme);
        }, () -> {
            LOGGER.error("Chat meme not found after mailing message sending by chat id: {} and meme id: {}", chatId, memeId);
            throw new UnsupportedOperationException(String.format("Chat meme not found after mailing message sending by chat id: %d and meme id: %d", chatId, memeId));
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ChatMeme> saveAllChatMemes(final List<ChatMeme> chatMemes) {
        return chatMemeRepository.saveAll(chatMemes);
    }

    @Transactional(readOnly = true)
    public List<ChatMeme> getAll() {
        return chatMemeRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public ChatMeme save(final ChatMeme chatMeme) {
        return chatMemeRepository.save(chatMeme);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByMemeId(final long memeId) {
        chatMemeRepository.deleteByMemeId(memeId);
    }
}
