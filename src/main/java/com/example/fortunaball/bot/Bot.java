package com.example.fortunaball.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Bot extends TelegramLongPollingBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(Bot.class);

    @Autowired
    private MessageService messageService;

    @Value("${bot.username}")
    private String botUserName;

    @Value("${bot.token}")
    private String token;

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(final Update update) {
        processMessage(update);
    }

    private void processMessage(final Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            final SendMessage message = messageService.processMessage(update);
            try {
                execute(message);
            } catch (final TelegramApiException e) {
                LOGGER.error("During message response an exception caught with message: {}", e.getMessage());
            }
        }
    }

    //todo add button to enable dissable Advice mailing
    //todo add button to enable dissable Memes mailing
    //todo add button to enable dissable Holidays mailing
    //todo add statistics of mailing types

}
