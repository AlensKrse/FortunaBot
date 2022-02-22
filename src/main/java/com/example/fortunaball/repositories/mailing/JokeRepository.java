package com.example.fortunaball.repositories.mailing;

import com.example.fortunaball.entities.mailing.Joke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JokeRepository extends JpaRepository<Joke, Long> {

    Joke findFirstByIdIn(List<Long> jokeIds);
}
