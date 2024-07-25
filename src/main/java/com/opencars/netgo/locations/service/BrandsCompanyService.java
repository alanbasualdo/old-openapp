package com.opencars.netgo.locations.service;

import com.opencars.netgo.locations.entity.BrandsCompany;
import com.opencars.netgo.locations.repository.BrandsCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BrandsCompanyService {

    @Autowired
    BrandsCompanyRepository brandsCompanyRepository;

    public boolean existsById(int id){
        return brandsCompanyRepository.existsById(id);
    }

    public Optional<BrandsCompany> getOne(int id){
        return brandsCompanyRepository.findById(id);
    }

    public List<BrandsCompany> getAll(){
        return brandsCompanyRepository.findAll();
    }

    public void save(BrandsCompany brandsCompany){
        brandsCompanyRepository.save(brandsCompany);
    }
}
