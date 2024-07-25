package com.opencars.netgo.auth.service;

import com.opencars.netgo.auth.entity.Rol;
import com.opencars.netgo.auth.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public boolean existsById(int id){
        return rolRepository.existsById(id);
    }

    public Optional<Rol> getOne(int id){
        return rolRepository.findById(id);
    }

    public List<Rol> getAll(){
        return rolRepository.findAll();
    }

    public List<Rol> getAllByType(String type){
        return rolRepository.findAllByType(type);
    }

    public List<Rol> getByNameAndType(String name, String type){
        return rolRepository.findRolByCoincidenceInNameAndType(name, type);
    }

    public List<Rol> getByName(String name){
        return rolRepository.findRolByCoincidenceInName(name);
    }

    public Optional<Rol> getByRolName(String rolName){
        return rolRepository.findByRolName(rolName);
    }

    public void save(Rol rol){
        rolRepository.save(rol);
    }
}
