package com.opencars.netgo.sales.proveedores.service;

import com.opencars.netgo.sales.proveedores.entity.AuthorizersAmounts;
import com.opencars.netgo.sales.proveedores.entity.SubcateorizedConcepts;
import com.opencars.netgo.sales.proveedores.repository.AuthorizersAmountsRepository;
import com.opencars.netgo.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorizersAmountsService {

    @Autowired
    AuthorizersAmountsRepository authorizersAmountsRepository;

    public Optional<AuthorizersAmounts> getAuthorizerForSubConceptAndAmount(double amount, SubcateorizedConcepts concept){
        return authorizersAmountsRepository.findAuthorizerForSubConceptAndAmount(amount, concept);
    }

    public boolean existsByAuthorizer(User authorizer){
        return authorizersAmountsRepository.existsByAuthorizer(authorizer);
    }

    public void deleteById(int id){
        authorizersAmountsRepository.deleteById(id);
    }

    public List<AuthorizersAmounts> list(){
        return authorizersAmountsRepository.findAll();
    }

    public List<User> listAuthorizers(){

        List<AuthorizersAmounts> list = list();
        List<User> listUsers = new ArrayList<>();

        for (AuthorizersAmounts authorizersAmounts : list) {
            listUsers.add(authorizersAmounts.getAuthorizer());
        }
        return listUsers;
    }

    public Optional<AuthorizersAmounts> getOne(int id){
        return authorizersAmountsRepository.findById(id);
    }

    public void save(AuthorizersAmounts authorizersAmounts){
        authorizersAmountsRepository.save(authorizersAmounts);
    }
}
