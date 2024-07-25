package com.opencars.netgo.dms.clients.service;

import com.opencars.netgo.dms.clients.entity.Clients;
import com.opencars.netgo.dms.clients.repository.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientsService {

    @Autowired
    ClientsRepository clientsRepository;

    public List<Clients> list(){
        return clientsRepository.findAll();
    }
    public Optional<Clients> getOne(Long id){
        return clientsRepository.findById(id);
    }
    public List<Clients> findAll(){
        return clientsRepository.findAll();
    }
    public boolean existsById(Long id){
        return clientsRepository.existsById(id);
    }

    public boolean existsByDocumento(Long documento){

        return clientsRepository.existsByDocumento(documento);
    }
    public boolean existsByTelefono(String telefono){

        return clientsRepository.existsByTelefono(telefono);
    }
    public boolean existsByCorreo(String correo){

        return clientsRepository.existsByCorreo(correo);
    }
    public Page<Clients> getAllClients(Pageable pageable){
        return clientsRepository.findAllByOrderByIdDesc(pageable);
    }

    public List<Clients> getByName(String name){
        return clientsRepository.findClientByCoincidence(name);
    }
    public void save(Clients client){
        clientsRepository.save(client);
    }

}
