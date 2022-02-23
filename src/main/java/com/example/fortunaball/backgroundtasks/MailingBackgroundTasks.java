package com.example.fortunaball.backgroundtasks;

import com.example.fortunaball.bot.MailingService;
import com.example.fortunaball.enums.MailingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MailingBackgroundTasks {

    @Autowired
    private MailingService mailingService;

    @Scheduled(cron = "0 0 12 * * ?", zone = "CET")
    public void sendDailyAdvice() {
        mailingService.sendMailingMessage(MailingType.ADVICE);
    }

    @Scheduled(cron = "0 0 9,14,19 * * *", zone = "CET")
    public void sendJoke() {
        mailingService.sendMailingMessage(MailingType.JOKE);
    }

    @Scheduled(cron = "0 0 11,17,21 * * *", zone = "CET")
    public void sendMeme() {
        mailingService.sendMailingMessage(MailingType.MEME);
    }

}
