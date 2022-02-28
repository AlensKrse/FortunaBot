package com.example.fortunaball.services.mailing;

import com.example.fortunaball.data.MemeData;
import com.example.fortunaball.entities.mailing.Meme;
import com.example.fortunaball.repositories.mailing.MemeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemeService {

    @Autowired
    private MemeRepository memeRepository;

    @Autowired
    private ChatMemeService chatMemeService;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public Meme findFirstByIdIn(final List<Long> memeIds) {
        return memeRepository.findFirstByIdIn(memeIds);
    }

    @Transactional(readOnly = true)
    public List<Meme> getAllMemes() {
        return memeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<MemeData> getAllMemeData() {
        return memeRepository.findAll().stream().map(meme -> modelMapper.map(meme, MemeData.class)).sorted(Comparator.comparing(MemeData::getId).reversed()).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public MemeData saveMemeData(final MemeData memeData) {
        final Meme meme = new Meme();
        meme.setText(memeData.getText());

        return modelMapper.map(memeRepository.save(meme), MemeData.class);
    }

    @Transactional(rollbackFor = Exception.class)
    public MemeData updateMemeData(final long memeId, final MemeData memeData) {
        final Optional<Meme> optionalAdvice = memeRepository.findById(memeId);
        optionalAdvice.ifPresentOrElse(meme -> {
            meme.setText(memeData.getText());
            memeRepository.save(meme);
        }, () -> {
            throw new UnsupportedOperationException(String.format("Meme not found by id: %d", memeId));
        });

        return modelMapper.map(memeRepository.getById(memeId), MemeData.class);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteMemeData(final long memeId) {
        final Optional<Meme> optionalAdvice = memeRepository.findById(memeId);
        optionalAdvice.ifPresentOrElse(meme -> {
            chatMemeService.deleteByMemeId(memeId);
            memeRepository.delete(meme);
        }, () -> {
            throw new UnsupportedOperationException(String.format("Meme not found by id: %d", memeId));
        });

        return Boolean.TRUE;
    }
}
