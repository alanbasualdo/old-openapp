package com.opencars.netgo.sales.proveedores.service;

import com.opencars.netgo.sales.proveedores.entity.SubcateorizedConcepts;
import com.opencars.netgo.sales.proveedores.entity.TableConceptsTypesPaids;
import com.opencars.netgo.sales.proveedores.repository.TableConceptsTypesPaidsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TableConceptsTypesPaidsService {

    @Autowired
    TableConceptsTypesPaidsRepository tableConceptsTypesPaidsRepository;

    public void deleteById(int id){
        tableConceptsTypesPaidsRepository.deleteById(id);
    }

    public boolean existsByConcept(SubcateorizedConcepts concept){
        return tableConceptsTypesPaidsRepository.existsByConcept(concept);
    }

    public List<TableConceptsTypesPaids> list(){
        return tableConceptsTypesPaidsRepository.findAll();
    }

    public Optional<TableConceptsTypesPaids> getOne(int id){
        return tableConceptsTypesPaidsRepository.findById(id);
    }

    public Optional<TableConceptsTypesPaids> getByConcept(SubcateorizedConcepts concept){
        return tableConceptsTypesPaidsRepository.findByConcept(concept);
    }

    public void save(TableConceptsTypesPaids tableConceptsTypesPaids){
        tableConceptsTypesPaidsRepository.save(tableConceptsTypesPaids);
    }
}
