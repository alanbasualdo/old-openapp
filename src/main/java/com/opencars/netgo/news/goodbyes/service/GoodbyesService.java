package com.opencars.netgo.news.goodbyes.service;

import com.opencars.netgo.news.goodbyes.dto.GoodbyesSummary;
import com.opencars.netgo.news.goodbyes.entity.Goodbyes;
import com.opencars.netgo.news.goodbyes.repository.GoodbyesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GoodbyesService {

    @Autowired
    GoodbyesRepository goodbyesRepository;

    public boolean existsByDate(LocalDate date){
        return goodbyesRepository.existsByDate(date);
    }

    public Optional<Goodbyes> getOne(int id){
        return goodbyesRepository.findById(id);
    }

    public List<Goodbyes> getByName(String name){
        return goodbyesRepository.findByName(name);
    }

    public List<Goodbyes> getCurrents(){

        List<Goodbyes> list;
        LocalDate date = LocalDate.now();
        list = goodbyesRepository.findByDate(date);

        return list;

    }

    public List<GoodbyesSummary> getGoodbyesSummary(){

        LocalDate currentDate = LocalDate.now();

        List<Goodbyes> list = goodbyesRepository.findAll();
        List<GoodbyesSummary> listing = new ArrayList<>();
        LocalDate dateGoodbye;

        for (int i = 0; i < list.size(); i++){
            dateGoodbye = list.get(i).getDate();
            Duration diff = Duration.between(dateGoodbye.atStartOfDay(), currentDate.atStartOfDay());
            long diffDays = diff.toDays();
            if (diffDays <= 7){
                listing.add(new GoodbyesSummary(list.get(i).getId(), list.get(i).getName()));
            }
        }

        //Lo ordeno en forma descendente para que aparezcan primero las últimas
        Collections.reverse(listing);

        return listing;

    }

    public void save(Goodbyes goodbyes){
        goodbyesRepository.save(goodbyes);
    }
}
