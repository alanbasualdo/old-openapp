package com.opencars.netgo.locations.service;

import com.opencars.netgo.locations.entity.Company;
import com.opencars.netgo.locations.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    public boolean existsById(int id){
        return companyRepository.existsById(id);
    }

    public Optional<Company> getOne(int id){
        return companyRepository.findById(id);
    }

    public List<Company> getAll(){
        return companyRepository.findAll();
    }

    public  List<Company> getByName(String name){
        return companyRepository.findByCoincidenceInName(name);
    }

    public void save(Company company){
        companyRepository.save(company);
    }
}
