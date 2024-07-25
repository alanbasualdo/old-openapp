package com.opencars.netgo.support.tickets.service;

import com.opencars.netgo.support.tickets.entity.TicketsNewEntry;
import com.opencars.netgo.support.tickets.repository.TicketsNewEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TicketsNewEntryService {

    @Autowired
    TicketsNewEntryRepository ticketsNewEntryRepository;

    public void save(TicketsNewEntry newEntry){
        ticketsNewEntryRepository.save(newEntry);
    }

    public List<TicketsNewEntry> getAll(){ return ticketsNewEntryRepository.findAll(); }

    public Optional<TicketsNewEntry> getOne(int id){
        return ticketsNewEntryRepository.findById(id);
    }

}
