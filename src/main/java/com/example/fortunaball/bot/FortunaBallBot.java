package com.example.fortunaball.bot;

import com.example.fortunaball.entities.Chat;
import com.example.fortunaball.entities.ChatFeature;
import com.example.fortunaball.enums.MailingType;
import com.example.fortunaball.services.ChatFeatureService;
import com.example.fortunaball.services.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FortunaBallBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(FortunaBallBot.class);

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatFeatureService chatFeatureService;

    @Autowired
    private FortunaBallAnswerService fortuneBallAnswerService;

    @Autowired
    private FortunaBallMailingDistributorService fortunaBallMailingDistributorService;

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
        if (update.hasMessage() && update.getMessage().hasText()) {
            LOGGER.info("On update received method started, Update object: {}", update);
            final Message message = update.getMessage();
            final String userName = getUserName(message);
            final long chatId = message.getChatId();
            final String requestText = message.getText().trim();
            final SendMessage responseMessage = getResponseMessage(userName, chatId, requestText);
            try {
                execute(responseMessage);
            } catch (TelegramApiException e) {
                LOGGER.error("During on update received method an exception caught: {}", e.getMessage());
            }
        }


    }

    public void sendMailingMessage(final MailingType mailingType) {
        final List<Long> activeChatIds = chatService.getAllActiveChats().stream().map(Chat::getId).collect(Collectors.toList());
        final List<ChatFeature> chatFeatures = fortunaBallMailingDistributorService.getChatFeatures(mailingType, activeChatIds);
        chatFeatures.forEach(chatFeature -> {
            final long chatId = chatFeature.getChatId();
            final SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            final Pair<Long, String> mailingPair = fortunaBallMailingDistributorService.getMailingPair(mailingType, chatId);
            final long mailingId = mailingPair.getFirst();
            final String text = mailingPair.getSecond();
            message.setText(text);
            try {
                execute(message);
                fortunaBallMailingDistributorService.setMailingMessageIsUsed(mailingType, chatId, mailingId);
            } catch (TelegramApiException e) {
                LOGGER.error("During daily advice method an exception caught: {}", e.getMessage());
            }
        });
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
                fortunaBallMailingDistributorService.addMailingDataToChatId(chatId);
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
            final String answer = fortuneBallAnswerService.getFortuneBallAnswer();
            responseMessage.setText(answer);
        }
        return responseMessage;
    }

    private boolean isInvalidQuestion(final String requestText) {
        return requestText.charAt(requestText.length() - 1) != '?';
    }

    //todo add day advice, every day
    //todo add button giveMeAdvice
    //todo add statistic for each chatId

}
