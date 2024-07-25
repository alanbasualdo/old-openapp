package com.opencars.netgo.dms.expertises.service;

import com.opencars.netgo.dms.expertises.entity.StatesYesNo;
import com.opencars.netgo.dms.expertises.repository.StatesYesNoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StatesYesNoService {

    @Autowired
    StatesYesNoRepository statesYesNoRepository;

    public List<StatesYesNo> list(){
        return statesYesNoRepository.findAll();
    }
    public Optional<StatesYesNo> getOne(int id){
        return statesYesNoRepository.findById(id);
    }
    public List<StatesYesNo> findAll(){
        return statesYesNoRepository.findAll();
    }
    public void save(StatesYesNo state){
        statesYesNoRepository.save(state);
    }
}
