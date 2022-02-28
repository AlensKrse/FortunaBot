package com.example.fortunaball.repositories.mailing;

import com.example.fortunaball.entities.mailing.ChatJoke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatJokeRepository extends JpaRepository<ChatJoke, Long> {

    List<ChatJoke> findByChatIdAndIsUsedFalse(long chatId);

    Optional<ChatJoke> findByJokeIdAndChatId(long jokeId, long chatId);

    void deleteByJokeId(long jokeId);
}
