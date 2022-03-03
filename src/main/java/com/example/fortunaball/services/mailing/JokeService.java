package com.example.fortunaball.services.mailing;

import com.example.fortunaball.data.JokeData;
import com.example.fortunaball.entities.mailing.Joke;
import com.example.fortunaball.repositories.mailing.JokeRepository;
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
public class JokeService {

    @Autowired
    private JokeRepository jokeRepository;

    @Autowired
    private ChatJokeService chatJokeService;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public Joke findFirstByIdIn(final List<Long> jokeIds) {
        return jokeRepository.findFirstByIdIn(jokeIds);
    }

    @Transactional(readOnly = true)
    public List<Joke> getAllJokes() {
        return jokeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<JokeData> getAllJokeData() {
        return jokeRepository.findAll().stream().map(joke -> modelMapper.map(joke, JokeData.class)).sorted(Comparator.comparing(JokeData::getId).reversed()).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public JokeData saveJokeData(final JokeData jokeData) {
        final String newText = Validate.notBlank(jokeData.getText(), "Text is undefined");
        final List<String> allJokeTexts = jokeRepository.findAll().stream().map(Joke::getText).collect(Collectors.toList());
        allJokeTexts.add(newText);
        final List<String> finalTexts = new ArrayList<>(new HashSet<>(allJokeTexts));
        if (allJokeTexts.size() != finalTexts.size()) {
            return modelMapper.map(jokeRepository.findByText(newText), JokeData.class);
        } else {
            final Joke joke = new Joke();
            joke.setText(newText);

            return modelMapper.map(jokeRepository.save(joke), JokeData.class);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public JokeData updateJokeData(final long jokeId, final JokeData jokeData) {
        final Optional<Joke> optionalAdvice = jokeRepository.findById(jokeId);
        optionalAdvice.ifPresentOrElse(joke -> {
            final String newText = Validate.notBlank(jokeData.getText(), "Text is undefined");
            final List<String> allJokeTexts = jokeRepository.findAll().stream().map(Joke::getText).collect(Collectors.toList());
            allJokeTexts.add(newText);
            final List<String> finalTexts = new ArrayList<>(new HashSet<>(allJokeTexts));
            if (allJokeTexts.size() == finalTexts.size()) {
                joke.setText(newText);
                jokeRepository.save(joke);
            }
        }, () -> {
            throw new UnsupportedOperationException(String.format("Joke not found by id: %d", jokeId));
        });

        return modelMapper.map(jokeRepository.getById(jokeId), JokeData.class);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteJokeData(final long jokeId) {
        final Optional<Joke> optionalAdvice = jokeRepository.findById(jokeId);
        optionalAdvice.ifPresentOrElse(joke -> {
            chatJokeService.deleteByJokeId(jokeId);
            jokeRepository.delete(joke);
        }, () -> {
            throw new UnsupportedOperationException(String.format("Joke not found by id: %d", jokeId));
        });

        return Boolean.TRUE;
    }

}
