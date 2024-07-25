package com.opencars.netgo.support.pcs.service;

import com.opencars.netgo.support.pcs.entity.Model;
import com.opencars.netgo.support.pcs.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ModelService {

    @Autowired
    ModelRepository modelRepository;

    public boolean existsById(int id){
        return modelRepository.existsById(id);
    }

    public Optional<Model> getOne(int id){
        return modelRepository.findById(id);
    }

    public List<Model> getAll(){
        return modelRepository.findAll();
    }

    public void save(Model model){
        modelRepository.save(model);
    }

    public List<Model> getByName(String name){
        return modelRepository.findByCoincidenceInName(name);
    }

}
