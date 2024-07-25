package com.opencars.netgo.sales.proveedores.service;

import com.opencars.netgo.sales.proveedores.entity.AuthorizationTypes;
import com.opencars.netgo.sales.proveedores.repository.AuthorizationTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorizationTypesService {

    @Autowired
    AuthorizationTypesRepository authorizationTypesRepository;

    public void deleteById(int id){
        authorizationTypesRepository.deleteById(id);
    }

    public List<AuthorizationTypes> list(){
        return authorizationTypesRepository.findAll();
    }

    public Optional<AuthorizationTypes> getOne(int id){
        return authorizationTypesRepository.findById(id);
    }

    public void save(AuthorizationTypes authorizationTypes){
        authorizationTypesRepository.save(authorizationTypes);
    }

}
