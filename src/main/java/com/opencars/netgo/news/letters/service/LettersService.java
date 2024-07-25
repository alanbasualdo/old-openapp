package com.opencars.netgo.news.letters.service;

import com.opencars.netgo.news.letters.dto.LetterList;
import com.opencars.netgo.news.letters.dto.LetterListNotPublished;
import com.opencars.netgo.news.letters.dto.LettersSummary;
import com.opencars.netgo.news.letters.entity.Letter;
import com.opencars.netgo.news.letters.repository.LettersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LettersService {

    @Autowired
    LettersRepository lettersRepository;


    public List<Letter> getByShortDate(LocalDate date, int published){
        return lettersRepository.findByShortDateAndPublished(date, published);
    }

    public void deleteById(int id){
        lettersRepository.deleteById(id);
    }
    public boolean existsById(int id){
        return lettersRepository.existsById(id);
    }

    public Optional<Letter> getOne(int id){
        return lettersRepository.findById(id);
    }

    public List<Letter> getCurrents(){

        LocalDate date = LocalDate.now();
        List<Letter> list = lettersRepository.findByShortDateAndPublished(date, 1);

        return list;

    }

    public List<LetterList> getAllPublished(){

        List<LetterList> list = lettersRepository.findAllPublished();

        return list;

    }

    public List<LetterListNotPublished> getAllNotPublished(){

        List<LetterListNotPublished> list = lettersRepository.findAllNotPublished();

        return list;

    }

    public List<LettersSummary> getLettersSummary(){

        LocalDateTime currentDate = LocalDateTime.now();

        List<LettersSummary> list = lettersRepository.findListBetweenDates(currentDate.minusDays(7), currentDate);

        return list;

    }

    public int countLettersByDate(){

        LocalDate currentDate = LocalDate.now();

        int count = lettersRepository.countLettersByDate(currentDate);

        return count;

    }

    public int countLettersNotPublished(){

        int count = lettersRepository.countLettersNotPublished();

        return count;

    }

    public void save(Letter letter){
        lettersRepository.save(letter);
    }
}
