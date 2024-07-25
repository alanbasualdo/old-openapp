package com.opencars.netgo.dms.quoter.service;

import com.opencars.netgo.dms.quoter.entity.ModelsCars;
import com.opencars.netgo.dms.quoter.repository.ModelsCarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ModelsCarsService {

    @Autowired
    ModelsCarsRepository modelsCarsRepository;

    public List<ModelsCars> list(){
        return modelsCarsRepository.findAll();
    }

    public Optional<ModelsCars> getOne(int id){
        return modelsCarsRepository.findById(id);
    }

    public Optional<ModelsCars> getPercentForModel(String description){
        return modelsCarsRepository.findPercentForModel(description);
    }

    public void deleteById(int id){
        modelsCarsRepository.deleteById(id);
    }

    public void save(ModelsCars modelsCars){
        modelsCarsRepository.save(modelsCars);
    }
}
