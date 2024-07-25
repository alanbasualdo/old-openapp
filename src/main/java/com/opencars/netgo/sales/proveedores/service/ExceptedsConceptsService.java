package com.opencars.netgo.sales.proveedores.service;

import com.opencars.netgo.sales.proveedores.entity.ExceptedsConcepts;
import com.opencars.netgo.sales.proveedores.entity.SubcateorizedConcepts;
import com.opencars.netgo.sales.proveedores.repository.ExceptedsConceptsRepository;
import com.opencars.netgo.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExceptedsConceptsService {

    @Autowired
    ExceptedsConceptsRepository exceptedsConceptsRepository;

    public List<ExceptedsConcepts> list(){
        return exceptedsConceptsRepository.findAll();
    }

    public boolean existsByConceptId(int id){
        return exceptedsConceptsRepository.existsByConceptId(id);
    }


    public void deleteById(int id){
        exceptedsConceptsRepository.deleteById(id);
    }

    public Optional<ExceptedsConcepts> getOne(int id){
        return exceptedsConceptsRepository.findById(id);
    }

    public void save(ExceptedsConcepts exceptedsConcepts){
        exceptedsConceptsRepository.save(exceptedsConcepts);
    }

    public User getAnalystForConcept(SubcateorizedConcepts concept){
        return exceptedsConceptsRepository.findAnalystForConcept(concept);
    }
}
