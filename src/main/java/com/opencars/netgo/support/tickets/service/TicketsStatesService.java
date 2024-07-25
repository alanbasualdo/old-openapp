package com.opencars.netgo.support.tickets.service;

import com.opencars.netgo.support.tickets.entity.TicketsStates;
import com.opencars.netgo.support.tickets.repository.TicketsStatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TicketsStatesService {

    @Autowired
    TicketsStatesRepository ticketsStatesRepository;

    public boolean existsById(int id){
        return ticketsStatesRepository.existsById(id);
    }

    public Optional<TicketsStates> getOne(int id){
        return ticketsStatesRepository.findById(id);
    }

    public List<TicketsStates> getByName(String name){
        return ticketsStatesRepository.findByCoincidenceInName(name);
    }

    public void save(TicketsStates state){
        ticketsStatesRepository.save(state);
    }

    public List<TicketsStates> getAll(){ return ticketsStatesRepository.findAll(); }
}
