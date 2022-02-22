package com.example.fortunaball.services.mailing;

import com.example.fortunaball.entities.mailing.Joke;
import com.example.fortunaball.repositories.mailing.JokeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JokeService {

    @Autowired
    private JokeRepository jokeRepository;

    @Transactional(readOnly = true)
    public Joke findFirstByIdIn(final List<Long> jokeIds) {
        return jokeRepository.findFirstByIdIn(jokeIds);
    }

    @Transactional(readOnly = true)
    public List<Joke> getAllJokes() {
        return jokeRepository.findAll();
    }
}
