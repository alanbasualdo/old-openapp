package com.opencars.netgo.dms.expertises.service;

import com.opencars.netgo.dms.expertises.entity.States;
import com.opencars.netgo.dms.expertises.repository.StatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StatesService {

    @Autowired
    StatesRepository statesRepository;

    public List<States> list(){
        return statesRepository.findAll();
    }
    public Optional<States> getOne(int id){
        return statesRepository.findById(id);
    }
    public List<States> findAll(){
        return statesRepository.findAll();
    }
    public void save(States state){
        statesRepository.save(state);
    }
}
