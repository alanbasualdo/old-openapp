package com.opencars.netgo.news.welcomes.service;

import com.opencars.netgo.news.welcomes.dto.ListNames;
import com.opencars.netgo.news.welcomes.entity.Welcome;
import com.opencars.netgo.news.welcomes.repository.WelcomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class WelcomeService {

    @Autowired
    WelcomeRepository welcomeRepository;

    public boolean existsById(int id){
        return welcomeRepository.existsById(id);
    }

    public List<Welcome> getById(int id){
        return welcomeRepository.findById(id);
    }

    public void deleteById(int id){
        welcomeRepository.deleteById(id);
    }

    public Optional<Welcome> getOne(int id){
        return welcomeRepository.findWelcomeById(id);
    }

    public List<Welcome> getCurrents(){

        LocalDate date = LocalDate.now();
        List<Welcome> list = welcomeRepository.findByShortDateAndPublished(date, 1);

        return list;

    }

    public List<ListNames> getPublished(){

        List<ListNames> list = welcomeRepository.findAllPublished();

        return list;

    }

    public List<ListNames> getNotPublished(){

        List<ListNames> list = welcomeRepository.findAllNotPublished();

        return list;

    }

    public List<Welcome> getWelcomesSummary(){

        LocalDateTime currentDate = LocalDateTime.now();

        List<Welcome> list = welcomeRepository.findListBetweenDates(currentDate.minusDays(7), currentDate);

        return list;

    }

    public int countWelcomesNotPublished(){

        int count = welcomeRepository.countWelcomesNotPublished();

        return count;

    }

    public int countWelcomesPublished(){

        LocalDate currentDate = LocalDate.now();

        int count = welcomeRepository.countWelcomesPublished(currentDate);

        return count;

    }

    public void save(Welcome welcome){
        welcomeRepository.save(welcome);
    }
}
