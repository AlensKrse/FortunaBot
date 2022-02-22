package com.example.fortunaball.services.mailing;

import com.example.fortunaball.entities.mailing.Meme;
import com.example.fortunaball.repositories.mailing.MemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemeService {

    @Autowired
    private MemeRepository memeRepository;

    @Transactional(readOnly = true)
    public Meme findFirstByIdIn(final List<Long> memeIds) {
        return memeRepository.findFirstByIdIn(memeIds);
    }

    @Transactional(readOnly = true)
    public List<Meme> getAllMemes() {
        return memeRepository.findAll();
    }
}
