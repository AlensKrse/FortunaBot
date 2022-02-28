package com.example.fortunaball.services.mailing;

import com.example.fortunaball.entities.mailing.ChatAdvice;
import com.example.fortunaball.repositories.mailing.ChatAdviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ChatAdviceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatAdviceService.class);

    @Autowired
    private ChatAdviceRepository chatAdviceRepository;

    @Transactional(readOnly = true)
    public List<ChatAdvice> getValidAdvicesByChatIdAndIsNotUsed(final long chatId) {
        return chatAdviceRepository.findByChatIdAndIsUsedFalse(chatId);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ChatAdvice> saveAllChatAdvices(final List<ChatAdvice> chatAdvices) {
        return chatAdviceRepository.saveAll(chatAdvices);
    }

    @Transactional(rollbackFor = Exception.class)
    public void setAdviceIsUsedForChatId(final long chatId, final long adviceId) {
        final Optional<ChatAdvice> optionalChatAdvice = chatAdviceRepository.findByAdviceIdAndChatId(adviceId, chatId);
        optionalChatAdvice.ifPresentOrElse(chatAdvice -> {
            chatAdvice.setUsed(Boolean.TRUE);
            chatAdviceRepository.save(chatAdvice);
        }, () -> {
            LOGGER.error("Chat advice not found after mailing message sending by chat id: {} and advice id: {}", chatId, adviceId);
            throw new UnsupportedOperationException(String.format("Chat advice not found after mailing message sending by chat id: %d and advice id: %d", chatId, adviceId));
        });
    }

    @Transactional(readOnly = true)
    public List<ChatAdvice> getAll() {
        return chatAdviceRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public ChatAdvice save(final ChatAdvice chatAdvice) {
        return chatAdviceRepository.save(chatAdvice);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByAdviceId(final long adviceId) {
        chatAdviceRepository.deleteByAdviceId(adviceId);
    }
}
