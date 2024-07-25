package com.opencars.netgo.dms.expertises.service;

import com.opencars.netgo.dms.expertises.entity.StatesFem;
import com.opencars.netgo.dms.expertises.repository.StatesFemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StatesFemService {

    @Autowired
    StatesFemRepository statesFemRepository;

    public List<StatesFem> list(){
        return statesFemRepository.findAll();
    }
    public Optional<StatesFem> getOne(int id){
        return statesFemRepository.findById(id);
    }
    public List<StatesFem> findAll(){
        return statesFemRepository.findAll();
    }
    public void save(StatesFem state){
        statesFemRepository.save(state);
    }
}
