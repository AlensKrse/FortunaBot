package com.example.fortunaball.repositories;

import com.example.fortunaball.entities.ChatFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatFeaturesRepository extends JpaRepository<ChatFeature, Long> {

    List<ChatFeature> findByAdvicesActiveTrueAndChatIdIn(List<Long> chatIds);

    List<ChatFeature> findByJokesActiveTrueAndChatIdIn(List<Long> chatIds);

    List<ChatFeature> findByMemesActiveTrueAndChatIdIn(List<Long> chatIds);
}
