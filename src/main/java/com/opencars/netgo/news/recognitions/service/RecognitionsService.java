package com.opencars.netgo.news.recognitions.service;

import com.opencars.netgo.news.recognitions.dto.RecognitionsList;
import com.opencars.netgo.news.recognitions.entity.Recognition;
import com.opencars.netgo.news.recognitions.repository.RecognitionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RecognitionsService {

    @Autowired
    RecognitionsRepository recognitionsRepository;

    public boolean existsByTitle(String title){
        return recognitionsRepository.existsByTitle(title);
    }

    public boolean existsById(int id){
        return recognitionsRepository.existsById(id);
    }

    public void deleteById(int id){
        recognitionsRepository.deleteById(id);
    }

    public Optional<Recognition> getOne(int id){
        return recognitionsRepository.findById(id);
    }

    public List<Recognition> getByTitle(String title){
        return recognitionsRepository.findByTitle(title);
    }

    public List<Recognition> getCurrents(){

        LocalDate date = LocalDate.now();
        List<Recognition> list = recognitionsRepository.findByShortDate(date);

        return list;

    }

    public List<RecognitionsList> getAllByPublishedState(int published){

        List<RecognitionsList> list = recognitionsRepository.findAllByPublishedState(published);

        return list;

    }

    public List<RecognitionsList> getRecognitionsSummary(){

        LocalDateTime currentDate = LocalDateTime.now();

        List<RecognitionsList> list = recognitionsRepository.findListBetweenDates(currentDate.minusDays(7), currentDate);

        return list;

    }

    public int countRecognitionsByDate(){

        LocalDate currentDate = LocalDate.now();

        int count = recognitionsRepository.countRecognitionsByDate(currentDate);

        return count;

    }

    public int countRecognitionsNotPublisheds(){

        int count = recognitionsRepository.countRecognitionsNotPublisheds();

        return count;

    }

    public void save(Recognition recognition){
        recognitionsRepository.save(recognition);
    }
}
