package com.example.fortunaball.repositories.mailing;

import com.example.fortunaball.entities.mailing.ChatAdvice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatAdviceRepository extends JpaRepository<ChatAdvice, Long> {

    List<ChatAdvice> findByChatIdAndIsUsedFalse(long chatId);

    Optional<ChatAdvice> findByIdAndChatId(long id, long chatId);
}
