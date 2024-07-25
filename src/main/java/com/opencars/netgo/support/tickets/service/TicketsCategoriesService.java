package com.opencars.netgo.support.tickets.service;

import com.opencars.netgo.support.tickets.entity.TicketsCategories;
import com.opencars.netgo.support.tickets.repository.TicketsCategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TicketsCategoriesService {

    @Autowired
    TicketsCategoriesRepository ticketsCategoriesRepository;

    public boolean existsById(int id){
        return ticketsCategoriesRepository.existsById(id);
    }

    public Optional<TicketsCategories> getOne(int id){
        return ticketsCategoriesRepository.findById(id);
    }

    public List<TicketsCategories> getByName(String name){
        return ticketsCategoriesRepository.findByCoincidenceInName(name);
    }

    public void save(TicketsCategories category){
        ticketsCategoriesRepository.save(category);
    }

    public List<TicketsCategories> getAll(){ return ticketsCategoriesRepository.findAll(); }
}
