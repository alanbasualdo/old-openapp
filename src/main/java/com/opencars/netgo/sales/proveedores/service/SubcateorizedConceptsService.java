package com.opencars.netgo.sales.proveedores.service;

import com.opencars.netgo.sales.proveedores.entity.SubcateorizedConcepts;
import com.opencars.netgo.sales.proveedores.repository.SubcateorizedConceptsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubcateorizedConceptsService {

    @Autowired
    SubcateorizedConceptsRepository subcateorizedConceptsRepository;

    public List<SubcateorizedConcepts> list(){
        Sort sort = Sort.by(Sort.Direction.ASC, "subConcept");
        return subcateorizedConceptsRepository.findAll(sort);
    }

    public void deleteById(int id){
        subcateorizedConceptsRepository.deleteById(id);
    }

    public Optional<SubcateorizedConcepts> getOne(int id){
        return subcateorizedConceptsRepository.findById(id);
    }

    public void save(SubcateorizedConcepts subcateorizedConcepts){
        subcateorizedConceptsRepository.save(subcateorizedConcepts);
    }

}
