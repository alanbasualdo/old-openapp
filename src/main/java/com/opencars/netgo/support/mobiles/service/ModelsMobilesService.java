package com.opencars.netgo.support.mobiles.service;

import com.opencars.netgo.support.mobiles.entity.ModelsMobiles;
import com.opencars.netgo.support.mobiles.repository.ModelsMobilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ModelsMobilesService {

    @Autowired
    ModelsMobilesRepository modelsMobilesRepository;

    public boolean existsById(int id){
        return modelsMobilesRepository.existsById(id);
    }

    public Optional<ModelsMobiles> getOne(int id){
        return modelsMobilesRepository.findById(id);
    }

    public List<ModelsMobiles> getAll(){
        return modelsMobilesRepository.findAll();
    }

    public void save(ModelsMobiles model){
        modelsMobilesRepository.save(model);
    }

    public List<ModelsMobiles> getByName(String name){
        return modelsMobilesRepository.findByCoincidenceInName(name);
    }
}
