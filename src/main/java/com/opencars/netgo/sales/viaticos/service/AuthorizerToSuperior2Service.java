package com.opencars.netgo.sales.viaticos.service;

import com.opencars.netgo.sales.viaticos.entity.AuthorizerToSuperior2;
import com.opencars.netgo.sales.viaticos.repository.AuthorizerToSuperior2Repository;
import com.opencars.netgo.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorizerToSuperior2Service {

    @Autowired
    AuthorizerToSuperior2Repository authorizerToSuperior2Repository;

    public List<AuthorizerToSuperior2> list(){
        return authorizerToSuperior2Repository.findAll();
    }

    public List<User> listColaborators(){
        return authorizerToSuperior2Repository.findColaborators();
    }

    public List<User> listAuthorizers(){

        List<AuthorizerToSuperior2> list = list();
        List<User> listUsers = new ArrayList<>();

        for (AuthorizerToSuperior2 authorizerToSuperior : list) {
            listUsers.add(authorizerToSuperior.getColaborator());
        }
        return listUsers;
    }

    public boolean existsByColaborator(User colaborator){
        return authorizerToSuperior2Repository.existsByColaborator(colaborator);
    }

    public Optional<AuthorizerToSuperior2> getOne(int id){
        return authorizerToSuperior2Repository.findById(id);
    }

    public void deleteById(int id){
        authorizerToSuperior2Repository.deleteById(id);
    }

    public void save(AuthorizerToSuperior2 authorizerToSuperior) {
        authorizerToSuperior2Repository.save(authorizerToSuperior);
    }
}
