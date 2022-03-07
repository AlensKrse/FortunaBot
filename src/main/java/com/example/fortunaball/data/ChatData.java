package com.example.fortunaball.data;

import java.util.Objects;

public class ChatData {

    private Long id;
    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ChatData chatData = (ChatData) o;
        return Objects.equals(id, chatData.id) && Objects.equals(active, chatData.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, active);
    }

    @Override
    public String toString() {
        return "ChatData{" +
                "id=" + id +
                ", active=" + active +
                '}';
    }
}
