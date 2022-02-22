package com.example.fortunaball.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "chats")
public class Chat {

    @Id
    @Column
    private long id;

    @Column
    private Boolean active;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
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
        final Chat chat = (Chat) o;
        return id == chat.id && Objects.equals(active, chat.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, active);
    }

    @Override
    public String toString() {
        return "Chat{" + "id=" + id + ", active=" + active + '}';
    }
}
