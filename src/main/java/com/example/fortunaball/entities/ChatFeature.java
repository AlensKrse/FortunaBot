package com.example.fortunaball.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "chatfeatures")
public class ChatFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Long chatId;

    @Column
    private Boolean advicesActive;

    @Column
    private Boolean jokesActive;

    @Column
    private Boolean memesActive;

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

    public Boolean getAdvicesActive() {
        return advicesActive;
    }

    public void setAdvicesActive(final Boolean advicesActive) {
        this.advicesActive = advicesActive;
    }

    public Boolean getJokesActive() {
        return jokesActive;
    }

    public void setJokesActive(final Boolean jokesActive) {
        this.jokesActive = jokesActive;
    }

    public Boolean getMemesActive() {
        return memesActive;
    }

    public void setMemesActive(final Boolean memesActive) {
        this.memesActive = memesActive;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ChatFeature that = (ChatFeature) o;
        return Objects.equals(id, that.id) && Objects.equals(chatId, that.chatId) && Objects.equals(advicesActive, that.advicesActive) && Objects.equals(jokesActive, that.jokesActive) && Objects.equals(memesActive, that.memesActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, advicesActive, jokesActive, memesActive);
    }

    @Override
    public String toString() {
        return "ChatFeature{" + "id=" + id + ", chatId=" + chatId + ", advicesActive=" + advicesActive + ", jokesActive=" + jokesActive + ", memesActive=" + memesActive + '}';
    }
}
