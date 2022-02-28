package com.example.fortunaball.data;

import java.util.Objects;

public class MemeData {

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
        final MemeData memeData = (MemeData) o;
        return Objects.equals(id, memeData.id) && Objects.equals(text, memeData.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }

    @Override
    public String toString() {
        return "MemeData{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
