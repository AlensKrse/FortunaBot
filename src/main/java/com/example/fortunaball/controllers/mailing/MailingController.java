package com.example.fortunaball.controllers.mailing;

import com.example.fortunaball.bot.DataFillingService;
import com.example.fortunaball.data.AdviceData;
import com.example.fortunaball.data.JokeData;
import com.example.fortunaball.data.MemeData;
import com.example.fortunaball.services.mailing.AdviceService;
import com.example.fortunaball.services.mailing.JokeService;
import com.example.fortunaball.services.mailing.MemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(MailingUris.ROOT)
public class MailingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailingController.class);

    @Autowired
    private AdviceService adviceService;

    @Autowired
    private JokeService jokeService;

    @Autowired
    private MemeService memeService;

    @Autowired
    private DataFillingService dataFillingService;

    @GetMapping(MailingUris.ADVICES)
    public List<AdviceData> getAdvices() {
        LOGGER.info("Request to get all advices, time: {}", new Date());
        return adviceService.getAllAdvicesData();
    }

    @PostMapping(MailingUris.ADVICES)
    public AdviceData saveAdvice(final @RequestBody AdviceData adviceData) {
        LOGGER.info("Request to save new advice with text: {}, time: {}", adviceData.getText(), new Date());
        return adviceService.saveAdviceData(adviceData);
    }

    @PutMapping(MailingUris.ADVICE)
    public AdviceData updateAdvice(@PathVariable final long adviceId, final @RequestBody AdviceData adviceData) {
        LOGGER.info("Request to update existing advice with id: {} and new text: {}, time: {}", adviceId, adviceData.getText(), new Date());
        return adviceService.updateAdviceData(adviceId, adviceData);
    }

    @DeleteMapping(MailingUris.ADVICE)
    public Boolean deleteAdvice(@PathVariable final long adviceId) {
        LOGGER.info("Request to delete advice with id: {}, time: {}", adviceId, new Date());
        return adviceService.deleteAdviceData(adviceId);
    }

    @GetMapping(MailingUris.JOKES)
    public List<JokeData> getJokes() {
        LOGGER.info("Request to get all jokes, time: {}", new Date());
        return jokeService.getAllJokeData();
    }

    @PostMapping(MailingUris.JOKES)
    public JokeData saveJoke(final @RequestBody JokeData jokeData) {
        LOGGER.info("Request to save new joke with text: {}, time: {}", jokeData.getText(), new Date());
        return jokeService.saveJokeData(jokeData);
    }

    @PutMapping(MailingUris.JOKE)
    public JokeData updateJoke(@PathVariable final long jokeId, final @RequestBody JokeData jokeData) {
        LOGGER.info("Request to update existing joke with id: {}, and new text: {}, time: {}", jokeId, jokeData.getText(), new Date());
        return jokeService.updateJokeData(jokeId, jokeData);
    }

    @DeleteMapping(MailingUris.JOKE)
    public Boolean deleteJoke(@PathVariable final long jokeId) {
        LOGGER.info("Request to delete joke with id: {}, time: {}", jokeId, new Date());
        return jokeService.deleteJokeData(jokeId);
    }

    @GetMapping(MailingUris.MEMES)
    public List<MemeData> getMemes() {
        LOGGER.info("Request to get all memes, time: {}", new Date());
        return memeService.getAllMemeData();
    }

    @PostMapping(MailingUris.MEMES)
    public MemeData saveMeme(final @RequestBody MemeData memeData) {
        LOGGER.info("Request to save new meme with text: {}, time: {}", memeData.getText(), new Date());
        return memeService.saveMemeData(memeData);
    }

    @PutMapping(MailingUris.MEME)
    public MemeData updateMeme(@PathVariable final long memeId, final @RequestBody MemeData memeData) {
        LOGGER.info("Request to update existing meme with id: {}, and new text: {}, time: {}", memeId, memeData.getText(), new Date());
        return memeService.updateMemeData(memeId, memeData);
    }

    @DeleteMapping(MailingUris.MEME)
    public Boolean deleteMeme(@PathVariable final long memeId) {
        LOGGER.info("Request to delete meme with id: {}, time: {}", memeId, new Date());
        return memeService.deleteMemeData(memeId);
    }

    @PostMapping(MailingUris.REFRESH_DATA)
    public Boolean refreshData() {
        LOGGER.info("Request to refresh data, time: {}", new Date());
        return dataFillingService.refreshContent();
    }
}
