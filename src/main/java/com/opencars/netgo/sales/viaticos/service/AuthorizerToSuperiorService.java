package com.opencars.netgo.sales.viaticos.service;

import com.opencars.netgo.sales.viaticos.entity.AuthorizerToSuperior;
import com.opencars.netgo.sales.viaticos.repository.AuthorizerToSuperiorRepository;
import com.opencars.netgo.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorizerToSuperiorService {

    @Autowired
    AuthorizerToSuperiorRepository authorizerToSuperiorRepository;

    public List<AuthorizerToSuperior> list(){
        return authorizerToSuperiorRepository.findAll();
    }

    public List<User> listColaborators(){
        return authorizerToSuperiorRepository.findColaborators();
    }

    public boolean existsByColaborator(User authorizer){
        return authorizerToSuperiorRepository.existsByColaborator(authorizer);
    }

    public List<User> listAuthorizers(){

        List<AuthorizerToSuperior> list = list();
        List<User> listUsers = new ArrayList<>();

        for (AuthorizerToSuperior authorizerToSuperior : list) {
            listUsers.add(authorizerToSuperior.getColaborator());
        }
        return listUsers;
    }

    public Optional<AuthorizerToSuperior> getOne(int id){
        return authorizerToSuperiorRepository.findById(id);
    }

    public void deleteById(int id){
        authorizerToSuperiorRepository.deleteById(id);
    }

    public void save(AuthorizerToSuperior authorizerToSuperior) {
        authorizerToSuperiorRepository.save(authorizerToSuperior);
    }
}
