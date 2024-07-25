package com.opencars.netgo.sales.viaticos.service;

import com.opencars.netgo.sales.viaticos.entity.ConceptsCompras;
import com.opencars.netgo.sales.viaticos.repository.ConceptsComprasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ConceptsComprasService {

    @Autowired
    ConceptsComprasRepository conceptsComprasRepository;

    public List<ConceptsCompras> list(){
        Sort sort = Sort.by(Sort.Direction.ASC, "concept");
        return conceptsComprasRepository.findAll(sort);
    }

    public void deleteById(int id){
        conceptsComprasRepository.deleteById(id);
    }

    public Optional<ConceptsCompras> getOne(int id){
        return conceptsComprasRepository.findById(id);
    }

    public void save(ConceptsCompras conceptsCompras){
        conceptsComprasRepository.save(conceptsCompras);
    }
}
