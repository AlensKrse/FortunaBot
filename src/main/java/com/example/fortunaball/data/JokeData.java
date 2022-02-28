package com.example.fortunaball.data;

import java.util.Objects;

public class JokeData {

    private Long id;
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final JokeData jokeData = (JokeData) o;
        return Objects.equals(id, jokeData.id) && Objects.equals(text, jokeData.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }

    @Override
    public String toString() {
        return "JokeData{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
