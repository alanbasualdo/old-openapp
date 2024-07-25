package com.opencars.netgo.sales.viaticos.service;

import com.opencars.netgo.sales.viaticos.entity.AuthorizerSuperior;
import com.opencars.netgo.sales.viaticos.repository.AuthorizerSuperiorRepository;
import com.opencars.netgo.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorizerSuperiorService {

    @Autowired
    AuthorizerSuperiorRepository authorizerSuperiorRepository;

    public boolean isSuperior(User colaborator){
        return authorizerSuperiorRepository.existsByColaborator(colaborator);
    }

    public boolean existsByColaborator(User colaborator){
        return authorizerSuperiorRepository.existsByColaborator(colaborator);
    }

    public List<AuthorizerSuperior> list(){
        return authorizerSuperiorRepository.findAll();
    }

    public List<User> listAuthorizers(){

        List<AuthorizerSuperior> list = list();
        List<User> listUsers = new ArrayList<>();

        for (AuthorizerSuperior authorizerSuperior : list) {
            listUsers.add(authorizerSuperior.getColaborator());
        }
        return listUsers;
    }

    public Optional<AuthorizerSuperior> getOne(int id){
        return authorizerSuperiorRepository.findById(id);
    }

    public void save(AuthorizerSuperior authorizerSuperior) {
        authorizerSuperiorRepository.save(authorizerSuperior);
    }
}
