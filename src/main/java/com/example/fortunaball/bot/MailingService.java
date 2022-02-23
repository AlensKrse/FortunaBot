package com.example.fortunaball.bot;

import com.example.fortunaball.entities.Chat;
import com.example.fortunaball.entities.ChatFeature;
import com.example.fortunaball.enums.MailingType;
import com.example.fortunaball.services.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MailingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailingService.class);

    @Autowired
    private ChatService chatService;

    @Autowired
    private MailingDistributorService mailingDistributorService;

    @Autowired
    private Bot bot;

    public void sendMailingMessage(final MailingType mailingType) {
        final List<Long> activeChatIds = chatService.getAllActiveChats().stream().map(Chat::getId).collect(Collectors.toList());
        final List<ChatFeature> chatFeatures = mailingDistributorService.getChatFeatures(mailingType, activeChatIds);
        chatFeatures.forEach(chatFeature -> {
            final long chatId = chatFeature.getChatId();
            final SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            final Pair<Long, String> mailingPair = mailingDistributorService.getMailingPair(mailingType, chatId);
            final long mailingId = mailingPair.getFirst();
            final String text = mailingPair.getSecond();
            message.setText(text);
            try {
                bot.execute(message);
                mailingDistributorService.setMailingMessageIsUsed(mailingType, chatId, mailingId);
            } catch (final TelegramApiException e) {
                LOGGER.error("During sending mailing message an exception caught with message: {}", e.getMessage());
            }
        });
    }

}
