package com.opencars.netgo.support.networks.service;

import com.opencars.netgo.support.networks.entity.Networks;
import com.opencars.netgo.support.networks.repository.NetworksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NetworksService {

    @Autowired
    NetworksRepository networksRepository;

    public boolean existsById(int id){
        return networksRepository.existsById(id);
    }
    public Optional<Networks> getOne(int id){
        return networksRepository.findById(id);
    }
    public List<Networks> getAll(){
        return networksRepository.findAll();
    }
    public void save(Networks networks){
        networksRepository.save(networks);
    }
    public List<Networks> getByCoincidence(String networks){
        return networksRepository.findByCoincidence(networks);
    }
    public List<Networks> getByBranch(int id){
        return networksRepository.findByBranch(id);
    }

}
