package com.opencars.netgo.sales.proveedores.service;

import com.opencars.netgo.sales.proveedores.entity.TypesPaids;
import com.opencars.netgo.sales.proveedores.repository.TypesPaidsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TypesPaidsService {

    @Autowired
    TypesPaidsRepository typesPaidsRepository;

    public boolean existsById(int id){
        return typesPaidsRepository.existsById(id);
    }

    public Optional<TypesPaids> getOne(int id){
        return typesPaidsRepository.findById(id);
    }

    public List<TypesPaids> getAll(){
        return typesPaidsRepository.findAll();
    }

    public void save(TypesPaids typesPaids){
        typesPaidsRepository.save(typesPaids);
    }

    public List<TypesPaids> getByCoincidence(String typesPaids){
        return typesPaidsRepository.findByCoincidence(typesPaids);
    }

}
