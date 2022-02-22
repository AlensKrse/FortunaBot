package com.example.fortunaball.services;

import com.example.fortunaball.entities.ChatFeature;
import com.example.fortunaball.repositories.ChatFeaturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatFeatureService {

    @Autowired
    private ChatFeaturesRepository chatFeaturesRepository;

    @Transactional(readOnly = true)
    public ChatFeature saveChatFeature(final ChatFeature chatFeature) {
        return chatFeaturesRepository.save(chatFeature);
    }

    @Transactional(readOnly = true)
    public List<ChatFeature> findAllWithAdviceActive(final List<Long> chatIds) {
        return chatFeaturesRepository.findByAdvicesActiveTrueAndChatIdIn(chatIds);
    }

    @Transactional(readOnly = true)
    public List<ChatFeature> findAllWithJokesActive(final List<Long> chatIds) {
        return chatFeaturesRepository.findByJokesActiveTrueAndChatIdIn(chatIds);
    }

    @Transactional(readOnly = true)
    public List<ChatFeature> findAllWithMemesActive(final List<Long> chatIds) {
        return chatFeaturesRepository.findByMemesActiveTrueAndChatIdIn(chatIds);
    }
}
