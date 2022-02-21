package com.example.fortunaball.enums;

import java.util.Arrays;
import java.util.Optional;

public enum FortunaType {

    POSITIVE_ANSWER(0),
    HESITANTLY_POSITIVE_ANSWER(1),
    NEUTRAL_ANSWER(2),
    NEGATIVE_ANSWER(3);

    private final int id;

    FortunaType(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Optional<FortunaType> getTypeById(final long id) {
        return Arrays.stream(values()).filter(item -> item.getId() == id).findFirst();
    }


    
    
}
