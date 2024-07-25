package com.opencars.netgo.sales.proveedores.service;

import com.opencars.netgo.sales.proveedores.entity.AuthorizerPaidType;
import com.opencars.netgo.sales.proveedores.repository.AuthorizerPaidTypeRepository;
import com.opencars.netgo.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorizerPaidTypeService {

    @Autowired
    AuthorizerPaidTypeRepository authorizerPaidTypeRepository;

    public List<AuthorizerPaidType> list(){
        return authorizerPaidTypeRepository.findAll();
    }

    public boolean existsByAuthorizer(User authorizer){
        return authorizerPaidTypeRepository.existsByColaborator(authorizer);
    }

    public List<User> listAuthorizers(){

        List<AuthorizerPaidType> list = list();
        List<User> listUsers = new ArrayList<>();

        for (AuthorizerPaidType authorizerPaidType : list) {
            listUsers.add(authorizerPaidType.getColaborator());
        }
        return listUsers;
    }

    public Optional<AuthorizerPaidType> getOne(int id){
        return authorizerPaidTypeRepository.findById(id);
    }

    public void save(AuthorizerPaidType authorizerPaidType) {
        authorizerPaidTypeRepository.save(authorizerPaidType);
    }
}
