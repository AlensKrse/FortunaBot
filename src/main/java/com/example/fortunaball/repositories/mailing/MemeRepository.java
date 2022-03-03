package com.example.fortunaball.repositories.mailing;

import com.example.fortunaball.entities.mailing.Meme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemeRepository extends JpaRepository<Meme, Long> {

    Meme findFirstByIdIn(List<Long> memeIds);

    Meme findByText(String text);
}
