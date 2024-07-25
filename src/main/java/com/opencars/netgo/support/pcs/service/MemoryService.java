package com.opencars.netgo.support.pcs.service;

import com.opencars.netgo.support.pcs.entity.Memory;
import com.opencars.netgo.support.pcs.repository.MemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemoryService {

    @Autowired
    MemoryRepository memoryRepository;

    public boolean existsById(int id){
        return memoryRepository.existsById(id);
    }

    public Optional<Memory> getOne(int id){
        return memoryRepository.findById(id);
    }

    public List<Memory> getAll(){
        return memoryRepository.findAll();
    }

    public void save(Memory memory){
        memoryRepository.save(memory);
    }

    public List<Memory> getByName(String name){
        return memoryRepository.findByCoincidenceInName(name);
    }

}
