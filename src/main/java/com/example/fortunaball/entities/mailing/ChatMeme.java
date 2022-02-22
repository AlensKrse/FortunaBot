package com.example.fortunaball.entities.mailing;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "chatmemes")
public class ChatMeme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Long chatId;

    @Column
    private Long memeId;

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

    public Long getMemeId() {
        return memeId;
    }

    public void setMemeId(final Long memeId) {
        this.memeId = memeId;
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
        final ChatMeme chatMeme = (ChatMeme) o;
        return Objects.equals(id, chatMeme.id) && Objects.equals(chatId, chatMeme.chatId) && Objects.equals(memeId, chatMeme.memeId) && Objects.equals(isUsed, chatMeme.isUsed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, memeId, isUsed);
    }

    @Override
    public String toString() {
        return "ChatMeme{" + "id=" + id + ", chatId=" + chatId + ", memeId=" + memeId + ", isUsed=" + isUsed + '}';
    }
}
