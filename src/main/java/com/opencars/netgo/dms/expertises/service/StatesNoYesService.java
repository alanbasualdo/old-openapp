package com.opencars.netgo.dms.expertises.service;

import com.opencars.netgo.dms.expertises.entity.StatesNoYes;
import com.opencars.netgo.dms.expertises.repository.StatesNoYesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StatesNoYesService {

    @Autowired
    StatesNoYesRepository statesNoYesRepository;

    public List<StatesNoYes> list(){
        return statesNoYesRepository.findAll();
    }
    public Optional<StatesNoYes> getOne(int id){
        return statesNoYesRepository.findById(id);
    }
    public List<StatesNoYes> findAll(){
        return statesNoYesRepository.findAll();
    }
    public void save(StatesNoYes state){
        statesNoYesRepository.save(state);
    }
}
