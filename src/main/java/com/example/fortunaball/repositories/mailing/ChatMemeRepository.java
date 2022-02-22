package com.example.fortunaball.repositories.mailing;

import com.example.fortunaball.entities.mailing.ChatMeme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMemeRepository extends JpaRepository<ChatMeme, Long> {

    List<ChatMeme> findByChatIdAndIsUsedFalse(long chatId);

    Optional<ChatMeme> findByIdAndChatId(long id, long chatId);
}
