package com.opencars.netgo.dms.expertises.service;

import com.opencars.netgo.dms.expertises.entity.StatesYesNoDoesNotCorrespond;
import com.opencars.netgo.dms.expertises.repository.StatesYesNoDoesNotCorrespondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StatesYesNoDoesNotCorrespondService {

    @Autowired
    StatesYesNoDoesNotCorrespondRepository statesYesNoDoesNotCorrespondRepository;

    public List<StatesYesNoDoesNotCorrespond> list(){
        return statesYesNoDoesNotCorrespondRepository.findAll();
    }
    public Optional<StatesYesNoDoesNotCorrespond> getOne(int id){
        return statesYesNoDoesNotCorrespondRepository.findById(id);
    }
    public List<StatesYesNoDoesNotCorrespond> findAll(){
        return statesYesNoDoesNotCorrespondRepository.findAll();
    }
    public void save(StatesYesNoDoesNotCorrespond state){
        statesYesNoDoesNotCorrespondRepository.save(state);
    }
}
