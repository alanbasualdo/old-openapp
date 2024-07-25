package com.opencars.netgo.dms.quoter.service;

import com.opencars.netgo.dms.quoter.entity.ModelsInfoAuto;
import com.opencars.netgo.dms.quoter.repository.ModelsInfoAutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ModelsInfoAutoService {

    @Autowired
    ModelsInfoAutoRepository modelsInfoAutoRepository;

    public List<ModelsInfoAuto> list(){
        return modelsInfoAutoRepository.findAll(Sort.by(Sort.Direction.ASC, "description"));
    }
    public List<ModelsInfoAuto> searchByCoincidence(String model){
        return modelsInfoAutoRepository.findByCoincidence(model);
    }

    public boolean existsByCodia(int codia){
        return modelsInfoAutoRepository.existsByCodia(codia);
    }


    public Optional<ModelsInfoAuto> getOne(long id){
        return modelsInfoAutoRepository.findById(id);
    }

    public void save(ModelsInfoAuto model){
        modelsInfoAutoRepository.save(model);
    }

    public void saveAll(List<ModelsInfoAuto> list){
        modelsInfoAutoRepository.saveAll(list);
    }

    public void deleteAll(){
        modelsInfoAutoRepository.deleteAll();
    }
}
