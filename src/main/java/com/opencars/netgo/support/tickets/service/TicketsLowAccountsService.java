package com.opencars.netgo.support.tickets.service;

import com.opencars.netgo.support.tickets.entity.TicketsLowAccounts;
import com.opencars.netgo.support.tickets.repository.TicketsLowAccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TicketsLowAccountsService {

    @Autowired
    TicketsLowAccountsRepository ticketsLowAccountsRepository;

    public void save(TicketsLowAccounts lowAccount){
        ticketsLowAccountsRepository.save(lowAccount);
    }

    public List<TicketsLowAccounts> getAll(){ return ticketsLowAccountsRepository.findAll(); }

    public Optional<TicketsLowAccounts> getOne(int id){
        return ticketsLowAccountsRepository.findById(id);
    }
}
