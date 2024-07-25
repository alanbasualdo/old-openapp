package com.opencars.netgo.dms.expertises.service;

import com.opencars.netgo.dms.expertises.entity.Missing;
import com.opencars.netgo.dms.expertises.repository.MissingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MissingService {

    @Autowired
    MissingRepository missingRepository;

    public boolean existsById(int id){
        return missingRepository.existsById(id);
    }

    public Optional<Missing> getOne(int id){
        return missingRepository.findById(id);
    }

    public List<Missing> getAll(){
        return missingRepository.findAll();
    }

    public void deleteById(int id){
        missingRepository.deleteById(id);
    }

    public void save(Missing missing){
        missingRepository.save(missing);
    }
}
