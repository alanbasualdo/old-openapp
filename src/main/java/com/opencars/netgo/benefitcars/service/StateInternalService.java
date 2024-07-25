package com.opencars.netgo.benefitcars.service;

import com.opencars.netgo.benefitcars.entity.StateInternal;
import com.opencars.netgo.benefitcars.repository.StateInternalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StateInternalService {

    @Autowired
    StateInternalRepository stateInternalRepository;

    public boolean existsById(int id){
        return stateInternalRepository.existsById(id);
    }

    public Optional<StateInternal> getOne(int id){
        return stateInternalRepository.findById(id);
    }

    public List<StateInternal> getAll(){
        return stateInternalRepository.findAll();
    }

    public void save(StateInternal stateInternal){
        stateInternalRepository.save(stateInternal);
    }
}
