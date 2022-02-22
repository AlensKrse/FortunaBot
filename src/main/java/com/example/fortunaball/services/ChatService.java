package com.example.fortunaball.services;


import com.example.fortunaball.entities.Chat;
import com.example.fortunaball.repositories.ChatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatService.class);
    public static final String CHAT_NOT_FOUND_ERROR_MESSAGE = "Chat with id: %d not found, can not set status inactive!";

    @Autowired
    private ChatRepository chatRepository;

    @Transactional(rollbackFor = Exception.class)
    public Chat saveChat(final long chatId) {
        LOGGER.info("Save new chat with id: {}", chatId);
        final Chat chat = new Chat();
        chat.setId(chatId);
        chat.setActive(Boolean.TRUE);

        return chatRepository.save(chat);
    }

    @Transactional
    public Chat setActive(final long chatId) {
        LOGGER.info("Set active chat with id: {}", chatId);
        final Optional<Chat> optionalChat = chatRepository.findById(chatId);
        optionalChat.ifPresentOrElse(chat -> {
            chat.setActive(Boolean.TRUE);
            chatRepository.save(chat);
        }, () -> saveChat(chatId));

        return chatRepository.getById(chatId);
    }

    @Transactional
    public Chat setInActive(final long chatId) {
        LOGGER.info("Set inactive chat with id: {}", chatId);
        final Optional<Chat> optionalChat = chatRepository.findById(chatId);
        optionalChat.ifPresentOrElse(chat -> {
            chat.setActive(Boolean.FALSE);
            chatRepository.save(chat);
        }, () -> {
            LOGGER.info("Chat with id: {} not found, can not set status inactive!", chatId);
            throw new UnsupportedOperationException(String.format(CHAT_NOT_FOUND_ERROR_MESSAGE, chatId));
        });

        return chatRepository.findById(chatId).orElseThrow(() -> {
            throw new UnsupportedOperationException(String.format(CHAT_NOT_FOUND_ERROR_MESSAGE, chatId));
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public Optional<Chat> getChat(final long chatId) {
        LOGGER.info("Request for chat with id: {}", chatId);

        return chatRepository.findById(chatId);
    }

    @Transactional(readOnly = true)
    public List<Chat> getAllActiveChats() {
        return chatRepository.findAllByActiveTrue();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteChat(final long chatId) {
        LOGGER.info("Delete chat by id: {}", chatId);
        chatRepository.deleteById(chatId);
    }
}
