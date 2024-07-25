package com.opencars.netgo.support.servers.service;

import com.opencars.netgo.support.servers.entity.Server;
import com.opencars.netgo.support.servers.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ServerService {

    @Autowired
    ServerRepository serverRepository;

    public Optional<Server> getOne(int id){
        return serverRepository.findById(id);
    }

    public Page<Server> getAll(Pageable pageable){
        return serverRepository.findAll(pageable);
    }

    public List<Server> getList(){
        return serverRepository.findAll();
    }

    public void deleteById(int id){
        serverRepository.deleteById(id);
    }

    public List<Server> getByCoincidence(String coincidence){
        return serverRepository.findByCoincidenceInName(coincidence);
    }

    public void save(Server server){
        serverRepository.save(server);
    }
}
