package com.opencars.netgo.support.pcs.service;

import com.opencars.netgo.support.pcs.entity.Brands;
import com.opencars.netgo.support.pcs.repository.BrandsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BrandsService {

    @Autowired
    BrandsRepository brandsRepository;

    public boolean existsById(int id){
        return brandsRepository.existsById(id);
    }

    public Optional<Brands> getOne(int id){
        return brandsRepository.findById(id);
    }

    public List<Brands> getAll(){
        return brandsRepository.findAll();
    }

    public void save(Brands brands){
        brandsRepository.save(brands);
    }

    public List<Brands> getByName(String name){
        return brandsRepository.findByCoincidenceInName(name);
    }
}
