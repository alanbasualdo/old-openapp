package com.opencars.netgo.dms.expertises.service;

import com.opencars.netgo.dms.expertises.entity.Fuel;
import com.opencars.netgo.dms.expertises.repository.FuelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FuelService {

    @Autowired
    FuelRepository fuelRepository;

    public List<Fuel> list(){
        return fuelRepository.findAll();
    }
    public Optional<Fuel> getOne(int id){
        return fuelRepository.findById(id);
    }
    public List<Fuel> findAll(){
        return fuelRepository.findAll();
    }
    public void save(Fuel fuel){
        fuelRepository.save(fuel);
    }
}
