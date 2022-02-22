package com.example.fortunaball.enums;

import java.util.Arrays;
import java.util.Optional;

public enum MailingType {

    ADVICE(0),
    JOKE(1),
    MEME(2);

    private final int id;

    MailingType(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Optional<MailingType> getTypeById(final long id) {
        return Arrays.stream(values()).filter(item -> item.getId() == id).findFirst();
    }
}
