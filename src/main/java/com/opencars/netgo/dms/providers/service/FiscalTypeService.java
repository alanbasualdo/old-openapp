package com.opencars.netgo.dms.providers.service;

import com.opencars.netgo.dms.providers.entity.FiscalType;
import com.opencars.netgo.dms.providers.repository.FiscalTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FiscalTypeService {

    @Autowired
    FiscalTypeRepository fiscalTypeRepository;

    public List<FiscalType> list(){
        return fiscalTypeRepository.findAll();
    }
    public Optional<FiscalType> getOne(int id){
        return fiscalTypeRepository.findById(id);
    }
    public boolean existsById(int id){
        return fiscalTypeRepository.existsById(id);
    }

    public boolean existsByNombre(String nombre){

        return fiscalTypeRepository.existsByNombre(nombre);
    }
    public void save(FiscalType fiscalType){
        fiscalTypeRepository.save(fiscalType);
    }
}
