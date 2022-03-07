package com.example.fortunaball.controllers.mailing;

public class MailingUris {

    public static final String ROOT = "/mailing";
    public static final String ADVICES = "/advices";
    public static final String ADVICE = ADVICES + "/{adviceId}";
    public static final String JOKES = "/jokes";
    public static final String JOKE = JOKES + "/{jokeId}";
    public static final String MEMES = "/memes";
    public static final String MEME = MEMES + "/{memeId}";
    public static final String REFRESH_DATA = "/data-refresh";
    public static final String ALL_STATISTICS = "/statistics/all";
    public static final String ACTIVE_STATISTICS = "/statistics/all-active";

    private MailingUris() {
    }
}
