package com.opencars.netgo.dms.quoter.service;

import com.opencars.netgo.dms.quoter.entity.TypeOfCars;
import com.opencars.netgo.dms.quoter.repository.TypeOfCarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TypeOfCarsService {

    @Autowired
    TypeOfCarsRepository typeOfCarsRepository;

    public List<TypeOfCars> list(){
        return typeOfCarsRepository.findAll();
    }

    public Optional<TypeOfCars> getOne(int id){
        return typeOfCarsRepository.findById(id);
    }

    public void deleteById(int id){
        typeOfCarsRepository.deleteById(id);
    }

    public void save(TypeOfCars typeOfCars){
        typeOfCarsRepository.save(typeOfCars);
    }
}
