package com.opencars.netgo.sales.proveedores.service;

import com.opencars.netgo.sales.proveedores.entity.ConceptsProviders;
import com.opencars.netgo.sales.proveedores.repository.ConceptsProvidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ConceptsProvidersService {

    @Autowired
    ConceptsProvidersRepository conceptsProvidersRepository;

    public List<ConceptsProviders> list(){
        return conceptsProvidersRepository.findAll();
    }

    public void deleteById(int id){
        conceptsProvidersRepository.deleteById(id);
    }

    public Optional<ConceptsProviders> getOne(int id){
        return conceptsProvidersRepository.findById(id);
    }

    public void save(ConceptsProviders conceptsProviders){
        conceptsProvidersRepository.save(conceptsProviders);
    }
}
