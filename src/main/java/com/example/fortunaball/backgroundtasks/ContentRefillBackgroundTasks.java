package com.example.fortunaball.backgroundtasks;

import com.example.fortunaball.bot.DataFillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ContentRefillBackgroundTasks {

    @Autowired
    private DataFillingService dataFillingService;

    @Scheduled(cron = "0 0 6 * * ?", zone = "CET")
    public void refreshContent() {
        dataFillingService.refreshContent();
    }
}
