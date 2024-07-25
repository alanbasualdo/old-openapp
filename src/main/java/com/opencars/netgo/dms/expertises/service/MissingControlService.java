package com.opencars.netgo.dms.expertises.service;

import com.opencars.netgo.dms.expertises.entity.Missing;
import com.opencars.netgo.dms.expertises.entity.MissingControl;
import com.opencars.netgo.dms.expertises.repository.MissingControlRepository;
import com.opencars.netgo.dms.quoter.entity.TypeOfCars;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MissingControlService {

    @Autowired
    MissingControlRepository missingControlRepository;

    public boolean existsById(int id){
        return missingControlRepository.existsById(id);
    }

    public Optional<MissingControl> getOne(int id){
        return missingControlRepository.findById(id);
    }

    public Long getPriceByMissingAndTypeOfCars(Missing missing, TypeOfCars typeOfCar){
        return missingControlRepository.findPriceByMissingAndTypeOfCars(missing, typeOfCar);
    }

    public void deleteById(int id){
        missingControlRepository.deleteById(id);
    }

    public List<MissingControl> getAll(){
        return missingControlRepository.findAll();
    }

    public void save(MissingControl missing){
        missingControlRepository.save(missing);
    }
}
