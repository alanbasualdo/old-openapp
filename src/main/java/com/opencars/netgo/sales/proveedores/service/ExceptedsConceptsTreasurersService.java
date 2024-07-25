package com.opencars.netgo.sales.proveedores.service;

import com.opencars.netgo.sales.proveedores.entity.ExceptedsConceptsTreasurers;
import com.opencars.netgo.sales.proveedores.entity.SubcateorizedConcepts;
import com.opencars.netgo.sales.proveedores.repository.ExceptedsConceptsTreasurersRepository;
import com.opencars.netgo.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExceptedsConceptsTreasurersService {

    @Autowired
    ExceptedsConceptsTreasurersRepository exceptedsConceptsTreasurersRepository;

    public List<ExceptedsConceptsTreasurers> list(){
        return exceptedsConceptsTreasurersRepository.findAll();
    }

    public boolean existsByConceptId(int id){
        return exceptedsConceptsTreasurersRepository.existsByConceptId(id);
    }

    public void deleteById(int id){
        exceptedsConceptsTreasurersRepository.deleteById(id);
    }

    public Optional<ExceptedsConceptsTreasurers> getOne(int id){
        return exceptedsConceptsTreasurersRepository.findById(id);
    }

    public void save(ExceptedsConceptsTreasurers exceptedsConcepts){
        exceptedsConceptsTreasurersRepository.save(exceptedsConcepts);
    }

    public User getTreasurerForConcept(SubcateorizedConcepts concept){
        return exceptedsConceptsTreasurersRepository.findTreasurerForConcept(concept);
    }
}
