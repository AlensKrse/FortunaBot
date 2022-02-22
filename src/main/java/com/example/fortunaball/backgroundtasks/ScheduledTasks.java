package com.example.fortunaball.backgroundtasks;

import com.example.fortunaball.bot.FortunaBallBot;
import com.example.fortunaball.enums.MailingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private FortunaBallBot fortunaBallBot;

    @Scheduled(cron = "0 0 12 * * ?", zone = "CET")
    public void sendDailyAdvice() {
        fortunaBallBot.sendMailingMessage(MailingType.ADVICE);
    }

    @Scheduled(cron = "30 * * * * *", zone = "CET")
    public void sendHourlyJoke() {
        fortunaBallBot.sendMailingMessage(MailingType.JOKE);
    }

    @Scheduled(cron = "0 0 11,17,21 * * *", zone = "CET")
    public void sendHourlyMeme() {
        fortunaBallBot.sendMailingMessage(MailingType.MEME);
    }

}
