package com.opencars.netgo.sales.proveedores.service;

import com.opencars.netgo.sales.proveedores.entity.Accounts;
import com.opencars.netgo.sales.proveedores.entity.SubcateorizedConcepts;
import com.opencars.netgo.sales.proveedores.repository.AccountsRepository;
import com.opencars.netgo.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountsService {

    @Autowired
    AccountsRepository accountsRepository;

    public List<Accounts> list(){
        return accountsRepository.findAll();
    }

    public void deleteById(int id){
        accountsRepository.deleteById(id);
    }

    public Optional<Accounts> getOne(int id){
        return accountsRepository.findById(id);
    }

    public void save(Accounts accounts){
        accountsRepository.save(accounts);
    }

    public Optional<Accounts> getAuthorizerForSubConcept(SubcateorizedConcepts concept){
        return accountsRepository.findAuthorizerForConcept(concept);
    }

    public boolean existsByOwner(User owner){
        return accountsRepository.existsByOwner(owner);
    }
}
