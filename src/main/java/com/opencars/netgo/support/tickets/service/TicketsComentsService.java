package com.opencars.netgo.support.tickets.service;

import com.opencars.netgo.support.tickets.entity.Tickets;
import com.opencars.netgo.support.tickets.entity.TicketsComents;
import com.opencars.netgo.support.tickets.repository.TicketsComentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TicketsComentsService {

    @Autowired
    TicketsComentsRepository ticketsComentsRepository;

    public Optional<TicketsComents> getOne(Long id){
        return ticketsComentsRepository.findById(id);
    }

    public void save(TicketsComents comment){
        ticketsComentsRepository.save(comment);
    }
}
