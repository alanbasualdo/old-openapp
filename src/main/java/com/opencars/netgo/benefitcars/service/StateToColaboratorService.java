package com.opencars.netgo.benefitcars.service;

import com.opencars.netgo.benefitcars.entity.StateToColaborator;
import com.opencars.netgo.benefitcars.repository.StateToColaboratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StateToColaboratorService {

    @Autowired
    StateToColaboratorRepository stateToColaboratorRepository;

    public boolean existsById(int id){
        return stateToColaboratorRepository.existsById(id);
    }

    public Optional<StateToColaborator> getOne(int id){
        return stateToColaboratorRepository.findById(id);
    }

    public List<StateToColaborator> getAll(){
        return stateToColaboratorRepository.findAll();
    }

    public void save(StateToColaborator stateToColaborator){
        stateToColaboratorRepository.save(stateToColaborator);
    }
}
