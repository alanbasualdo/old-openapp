package com.opencars.netgo.dms.expertises.service;

import com.opencars.netgo.dms.expertises.entity.Transmision;
import com.opencars.netgo.dms.expertises.repository.TransmisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransmisionService {

    @Autowired
    TransmisionRepository transmisionRepository;

    public List<Transmision> list(){
        return transmisionRepository.findAll();
    }
    public Optional<Transmision> getOne(int id){
        return transmisionRepository.findById(id);
    }
    public List<Transmision> findAll(){
        return transmisionRepository.findAll();
    }
    public void save(Transmision transmision){
        transmisionRepository.save(transmision);
    }
}
