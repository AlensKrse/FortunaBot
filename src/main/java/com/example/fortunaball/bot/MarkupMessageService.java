package com.example.fortunaball.bot;

import com.example.fortunaball.entities.ChatFeature;
import com.example.fortunaball.services.ChatFeatureService;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarkupMessageService {

    private static final String HOLIDAYS = "Праздники";
    private static final String ADVICES = "Советы";
    private static final String MEMES = "Мемы";

    @Autowired
    private ChatFeatureService chatFeatureService;

    @Transactional(readOnly = true)
    public InlineKeyboardMarkup getInlineKeyboardMarkup() {
        final InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        final InlineKeyboardButton holidayButton = new InlineKeyboardButton();
        final InlineKeyboardButton adviceButton = new InlineKeyboardButton();
        final InlineKeyboardButton memeButton = new InlineKeyboardButton();
        holidayButton.setText(HOLIDAYS);
        holidayButton.setCallbackData(HOLIDAYS);
        adviceButton.setText(ADVICES);
        adviceButton.setCallbackData(ADVICES);
        memeButton.setText(MEMES);
        memeButton.setCallbackData(MEMES);

        final List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(holidayButton);
        keyboardButtonsRow.add(adviceButton);
        keyboardButtonsRow.add(memeButton);
        final List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    @Transactional(rollbackFor = Exception.class)
    public SendMessage processMarkupMessage(final Update update) {
        final CallbackQuery callbackQuery = update.getCallbackQuery();
        final long chatId = Validate.notNull(callbackQuery.getMessage().getChatId(), "Chat id is undefined");
        final String callbackQueryData = Validate.notNull(callbackQuery.getData(), "Data is undefined");

        final SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));

        final ChatFeature chatFeature = chatFeatureService.getByChatId(chatId);
        if (callbackQueryData.contains(HOLIDAYS)) {
            final boolean jokesActive = chatFeature.getJokesActive();
            chatFeature.setJokesActive(!jokesActive);
            if (jokesActive) {
                sendMessage.setText("Вы отключили праздники!");
            } else {
                sendMessage.setText("Вы подключили праздники!");
            }
        } else if (callbackQueryData.contains(ADVICES)) {
            final boolean advicesActive = chatFeature.getAdvicesActive();
            chatFeature.setAdvicesActive(!advicesActive);
            if (advicesActive) {
                sendMessage.setText("Вы отключили советы!");
            } else {
                sendMessage.setText("Вы подключили советы!");
            }
        } else if (callbackQueryData.contains(MEMES)) {
            final boolean memesActive = chatFeature.getMemesActive();
            chatFeature.setMemesActive(!memesActive);
            if (memesActive) {
                sendMessage.setText("Вы отключили мемы!");
            } else {
                sendMessage.setText("Вы подключили мемы!");
            }
        } else {
            final String errorMessage = String.format("During callback query the data is undefined by chat id: %d", chatId);
            throw new UnsupportedOperationException(errorMessage);
        }
        chatFeatureService.saveChatFeature(chatFeature);

        return sendMessage;
    }
}
