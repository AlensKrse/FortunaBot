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

    @Autowired
    private ChatRepository chatRepository;

    @Transactional(rollbackFor = Exception.class)
    public Chat saveChat(final long chatId) {
        LOGGER.info("Save new chat with id: {}", chatId);
        final Chat chat = new Chat();
        chat.setId(chatId);

        return chatRepository.save(chat);
    }

    @Transactional(rollbackFor = Exception.class)
    public Optional<Chat> getChat(final long chatId) {
        LOGGER.info("Request for chat with id: {}", chatId);

        return chatRepository.findById(chatId);
    }

    @Transactional(readOnly = true)
    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteChat(final long chatId) {
        LOGGER.info("Delete chat by id: {}", chatId);
        chatRepository.deleteById(chatId);
    }
}
