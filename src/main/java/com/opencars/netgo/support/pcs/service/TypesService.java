package com.opencars.netgo.support.pcs.service;

import com.opencars.netgo.support.pcs.entity.PcTypes;
import com.opencars.netgo.support.pcs.repository.TypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TypesService {

    @Autowired
    TypesRepository typesRepository;

    public boolean existsById(int id){
        return typesRepository.existsById(id);
    }

    public Optional<PcTypes> getOne(int id){
        return typesRepository.findById(id);
    }

    public List<PcTypes> getAll(){
        return typesRepository.findAll();
    }

    public void save(PcTypes types){
        typesRepository.save(types);
    }

    public List<PcTypes> getByName(String name){
        return typesRepository.findByCoincidenceInName(name);
    }

}
