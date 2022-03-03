package com.example.fortunaball.services.mailing;

import com.example.fortunaball.data.MemeData;
import com.example.fortunaball.entities.mailing.Meme;
import com.example.fortunaball.repositories.mailing.MemeRepository;
import org.apache.commons.lang3.Validate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
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
        final String newText = Validate.notBlank(memeData.getText(), "Text is undefined");
        final List<String> allMemeTexts = memeRepository.findAll().stream().map(Meme::getText).collect(Collectors.toList());
        allMemeTexts.add(newText);
        final List<String> finalTexts = new ArrayList<>(new HashSet<>(allMemeTexts));
        if (allMemeTexts.size() != finalTexts.size()) {
            return modelMapper.map(memeRepository.findByText(newText), MemeData.class);
        } else {
            final Meme meme = new Meme();
            meme.setText(newText);

            return modelMapper.map(memeRepository.save(meme), MemeData.class);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public MemeData updateMemeData(final long memeId, final MemeData memeData) {
        final Optional<Meme> optionalAdvice = memeRepository.findById(memeId);
        optionalAdvice.ifPresentOrElse(meme -> {
            final String newText = Validate.notBlank(memeData.getText(), "Text is undefined");
            final List<String> allMemeTexts = memeRepository.findAll().stream().map(Meme::getText).collect(Collectors.toList());
            allMemeTexts.add(newText);
            final List<String> finalTexts = new ArrayList<>(new HashSet<>(allMemeTexts));
            if (allMemeTexts.size() == finalTexts.size()) {
                meme.setText(newText);
                memeRepository.save(meme);
            }
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
