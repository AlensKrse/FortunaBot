package com.example.fortunaball.entities.mailing;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "chatadvices")
public class ChatAdvice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Long chatId;

    @Column
    private Long adviceId;

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

    public Long getAdviceId() {
        return adviceId;
    }

    public void setAdviceId(final Long adviceId) {
        this.adviceId = adviceId;
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
        final ChatAdvice that = (ChatAdvice) o;
        return Objects.equals(id, that.id) && Objects.equals(chatId, that.chatId) && Objects.equals(adviceId, that.adviceId) && Objects.equals(isUsed, that.isUsed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, adviceId, isUsed);
    }

    @Override
    public String toString() {
        return "ChatAdvice{" + "id=" + id + ", chatId=" + chatId + ", adviceId=" + adviceId + ", isUsed=" + isUsed + '}';
    }
}
