package com.opencars.netgo.support.mobiles.service;

import com.opencars.netgo.support.mobiles.entity.BrandsMobiles;
import com.opencars.netgo.support.mobiles.repository.BrandsMobilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BrandsMobilesService {

    @Autowired
    BrandsMobilesRepository brandsMobilesRepository;

    public boolean existsById(int id){
        return brandsMobilesRepository.existsById(id);
    }

    public Optional<BrandsMobiles> getOne(int id){
        return brandsMobilesRepository.findById(id);
    }

    public List<BrandsMobiles> getAll(){
        return brandsMobilesRepository.findAll();
    }

    public void save(BrandsMobiles brands){
        brandsMobilesRepository.save(brands);
    }

    public List<BrandsMobiles> getByName(String name){
        return brandsMobilesRepository.findByCoincidenceInName(name);
    }
}
