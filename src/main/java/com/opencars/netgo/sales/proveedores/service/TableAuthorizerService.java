package com.opencars.netgo.sales.proveedores.service;

import com.opencars.netgo.sales.proveedores.entity.TableAuthorizer;
import com.opencars.netgo.sales.proveedores.repository.TableAuthorizerRepository;
import com.opencars.netgo.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TableAuthorizerService {

    @Autowired
    TableAuthorizerRepository tableAuthorizerRepository;

    public Optional<TableAuthorizer> getAuthorizerForAmount(double amount){
        return tableAuthorizerRepository.findAuthorizerForAmount(amount);
    }

    public boolean existsByAuthorizer(User authorizer){
        return tableAuthorizerRepository.existsByAuthorizer(authorizer);
    }

    public Double findAmountMin(){
        return tableAuthorizerRepository.findAmountMin();
    }

    public void deleteById(int id){
        tableAuthorizerRepository.deleteById(id);
    }

    public List<TableAuthorizer> list(){
        return tableAuthorizerRepository.findAll();
    }

    public List<User> listAuthorizers(){

        List<TableAuthorizer> list = list();
        List<User> listUsers = new ArrayList<>();

        for (TableAuthorizer tableAuthorizer : list) {
            listUsers.add(tableAuthorizer.getAuthorizer());
        }
        return listUsers;
    }

    public Optional<TableAuthorizer> getOne(int id){
        return tableAuthorizerRepository.findById(id);
    }

    public void save(TableAuthorizer tableAuthorizer){
        tableAuthorizerRepository.save(tableAuthorizer);
    }

}
