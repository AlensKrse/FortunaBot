package com.example.fortunaball.entities.mailing;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "chatjokes")
public class ChatJoke {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Long chatId;

    @Column
    private Long jokeId;

    @Column
    private Boolean isUsed;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(final Long chatId) {
        this.chatId = chatId;
    }

    public Long getJokeId() {
        return jokeId;
    }

    public void setJokeId(final Long jokeId) {
        this.jokeId = jokeId;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(final Boolean used) {
        isUsed = used;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ChatJoke chatJoke = (ChatJoke) o;
        return Objects.equals(id, chatJoke.id) && Objects.equals(chatId, chatJoke.chatId) && Objects.equals(jokeId, chatJoke.jokeId) && Objects.equals(isUsed, chatJoke.isUsed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, jokeId, isUsed);
    }

    @Override
    public String toString() {
        return "ChatJoke{" + "id=" + id + ", chatId=" + chatId + ", jokeId=" + jokeId + ", isUsed=" + isUsed + '}';
    }
}
