package com.example.fortunaball.repositories.mailing;

import com.example.fortunaball.entities.mailing.Advice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdviceRepository extends JpaRepository<Advice, Long> {

    Advice findFirstByIdIn(List<Long> adviceIds);
}
