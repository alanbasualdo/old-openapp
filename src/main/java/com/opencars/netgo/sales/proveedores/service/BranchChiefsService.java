package com.opencars.netgo.sales.proveedores.service;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.sales.proveedores.entity.BranchChiefs;
import com.opencars.netgo.sales.proveedores.repository.BranchChiefsRepository;
import com.opencars.netgo.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BranchChiefsService {

    @Autowired
    BranchChiefsRepository branchChiefsRepository;

    public Optional<BranchChiefs> getChiefForBranch(Branch branch){
        return branchChiefsRepository.findChiefByBranch(branch.getId());
    }

    public boolean existsByChief(User chief){
        return branchChiefsRepository.existsByChief(chief);
    }

    public void deleteById(int id){
        branchChiefsRepository.deleteById(id);
    }

    public List<BranchChiefs> list(){
        return branchChiefsRepository.findAll();
    }

    public Optional<BranchChiefs> getOne(int id){
        return branchChiefsRepository.findById(id);
    }

    public void save(BranchChiefs branchChiefs){
        branchChiefsRepository.save(branchChiefs);
    }


}
