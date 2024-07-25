package com.opencars.netgo.dms.expertises.service;

import com.opencars.netgo.dms.expertises.entity.Traccion;
import com.opencars.netgo.dms.expertises.repository.TraccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TraccionService {

    @Autowired
    TraccionRepository traccionRepository;

    public List<Traccion> list(){
        return traccionRepository.findAll();
    }
    public Optional<Traccion> getOne(int id){
        return traccionRepository.findById(id);
    }
    public List<Traccion> findAll(){
        return traccionRepository.findAll();
    }
    public void save(Traccion traccion){
        traccionRepository.save(traccion);
    }
}
