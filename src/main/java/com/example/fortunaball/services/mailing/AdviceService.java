package com.example.fortunaball.services.mailing;

import com.example.fortunaball.entities.mailing.Advice;
import com.example.fortunaball.repositories.mailing.AdviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdviceService {

    @Autowired
    private AdviceRepository adviceRepository;

    @Transactional(readOnly = true)
    public Advice findFirstByIdIn(final List<Long> adviceIds) {
        return adviceRepository.findFirstByIdIn(adviceIds);
    }

    @Transactional(readOnly = true)
    public List<Advice> getAllAdvices() {
        return adviceRepository.findAll();
    }
}
