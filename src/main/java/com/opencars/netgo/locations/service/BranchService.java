package com.opencars.netgo.locations.service;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.locations.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BranchService {

    @Autowired
    BranchRepository branchRepository;

    public boolean existsById(int id){
        return branchRepository.existsById(id);
    }

    public Optional<Branch> getOne(int id){
        return branchRepository.findById(id);
    }

    public List<Branch> getAll(){
        return branchRepository.findAll();
    }

    public List<Branch> getByCity(String name){
        return  branchRepository.findByCoincidenceInCity(name);
    }

    public void save(Branch branch){
        branchRepository.save(branch);
    }

}
