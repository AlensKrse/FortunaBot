package com.example.fortunaball.services.mailing;

import com.example.fortunaball.data.AdviceData;
import com.example.fortunaball.entities.mailing.Advice;
import com.example.fortunaball.repositories.mailing.AdviceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdviceService {

    @Autowired
    private AdviceRepository adviceRepository;

    @Autowired
    private ChatAdviceService chatAdviceService;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public Advice findFirstByIdIn(final List<Long> adviceIds) {
        return adviceRepository.findFirstByIdIn(adviceIds);
    }

    @Transactional(readOnly = true)
    public List<Advice> getAllAdvices() {
        return adviceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<AdviceData> getAllAdvicesData() {
        return adviceRepository.findAll().stream().map(advice -> modelMapper.map(advice, AdviceData.class)).sorted(Comparator.comparing(AdviceData::getId).reversed()).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public AdviceData saveAdviceData(final AdviceData adviceData) {
        final Advice advice = new Advice();
        advice.setText(adviceData.getText());

        return modelMapper.map(adviceRepository.save(advice), AdviceData.class);
    }

    @Transactional(rollbackFor = Exception.class)
    public AdviceData updateAdviceData(final long adviceId, final AdviceData adviceData) {
        final Optional<Advice> optionalAdvice = adviceRepository.findById(adviceId);
        optionalAdvice.ifPresentOrElse(advice -> {
            advice.setText(adviceData.getText());
            adviceRepository.save(advice);
        }, () -> {
            throw new UnsupportedOperationException(String.format("Advice not found by id: %d", adviceId));
        });

        return modelMapper.map(adviceRepository.getById(adviceId), AdviceData.class);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteAdviceData(final long adviceId) {
        final Optional<Advice> optionalAdvice = adviceRepository.findById(adviceId);
        optionalAdvice.ifPresentOrElse(advice -> {
            chatAdviceService.deleteByAdviceId(adviceId);
            adviceRepository.delete(advice);
        }, () -> {
            throw new UnsupportedOperationException(String.format("Advice not found by id: %d", adviceId));
        });

        return Boolean.TRUE;
    }
}
