package com.example.fortunaball.bot;

import com.example.fortunaball.entities.Chat;
import com.example.fortunaball.services.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;
import java.util.Optional;

@Service
public class MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private ChatService chatService;

    @Autowired
    private DataFillingService dataFillingService;

    @Autowired
    private FortunaBallAnswerService fortunaBallAnswerService;

    public SendMessage processMessage(final Update update) {
        LOGGER.info("On update received method started, Update object: {}", update);
        final Message message = update.getMessage();
        final String userName = getUserName(message);
        final long chatId = message.getChatId();
        final String requestText = message.getText().trim();

        return getResponseMessage(userName, chatId, requestText);
    }

    private String getUserName(final Message message) {
        final org.telegram.telegrambots.meta.api.objects.Chat messageChat = message.getChat();

        final String userName;
        if (Objects.isNull(messageChat.getUserName()) || messageChat.getUserName().isEmpty()) {
            final Optional<String> firstName = Optional.ofNullable(messageChat.getFirstName());
            final Optional<String> lastName = Optional.ofNullable(messageChat.getLastName());

            userName = firstName.orElse("") + " " + lastName.orElse("");
        } else {
            userName = messageChat.getUserName();
        }

        return userName;
    }

    private SendMessage getResponseMessage(final String userName, final long chatId, final String requestText) {
        final SendMessage responseMessage = new SendMessage();
        responseMessage.setChatId(String.valueOf(chatId));

        final Optional<Chat> optionalChat = chatService.getChat(chatId);
        if (requestText.equals("/start")) {
            optionalChat.ifPresentOrElse(chat -> {
                chatService.setActive(chatId);
                responseMessage.setText("Мне кажется мы с Вами уже знакомы...");
            }, () -> {
                chatService.saveChat(chatId);
                dataFillingService.addMailingDataToChatId(chatId);
                final String welcomeText = String.format("Добро пожаловать %s! Шар фортуны ожидает Ваших вопросов.", userName);
                responseMessage.setText(welcomeText);
            });
        } else if (requestText.equals("/stop")) {
            optionalChat.ifPresentOrElse(chat -> {
                chatService.setInActive(chatId);
                final String farewellText = String.format("Надеюсь увидеть Вас снова %s!", userName);
                responseMessage.setText(farewellText);
            }, () -> responseMessage.setText("Я не могу остановить то, что не начато..."));
        } else if (isInvalidQuestion(requestText)) {
            responseMessage.setText("По моему это не вопрос, попробуйте еще раз в формате '...?'");
        } else {
            optionalChat.ifPresentOrElse(chat -> { //todo Предложить подключить бота
            }, () -> chatService.saveChat(chatId));
            final String answer = fortunaBallAnswerService.getFortuneBallAnswer();
            responseMessage.setText(answer);
        }
        return responseMessage;
    }

    private boolean isInvalidQuestion(final String requestText) {
        return requestText.charAt(requestText.length() - 1) != '?';
    }
}
