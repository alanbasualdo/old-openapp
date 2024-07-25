package com.opencars.netgo.users.service;

import com.opencars.netgo.users.entity.Charge;
import com.opencars.netgo.users.repository.ChargeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ChargeService {

    @Autowired
    ChargeRepository chargeRepository;

    public boolean existsById(int id){
        return chargeRepository.existsById(id);
    }

    public boolean existsByName(String position){
        return chargeRepository.existsByName(position);
    }

    public Optional<Charge> getOne(int id){
        return chargeRepository.findById(id);
    }

    public List<Charge> getAll(){
        return chargeRepository.findAllOrderByName();
    }

    public List<Charge> getByName(String name){
        return chargeRepository.findByCoincidenceInName(name);
    }

    public Page<Charge> getAllCharges(Pageable pageable){
        return chargeRepository.findAll(pageable);
    }

    public void deleteById(int id){
        chargeRepository.deleteById(id);
    }

    public void save(Charge charge){
        chargeRepository.save(charge);
    }
}
