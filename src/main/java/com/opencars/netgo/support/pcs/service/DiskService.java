package com.opencars.netgo.support.pcs.service;

import com.opencars.netgo.support.pcs.entity.Disk;
import com.opencars.netgo.support.pcs.repository.DiskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DiskService {

    @Autowired
    DiskRepository diskRepository;

    public boolean existsById(int id){
        return diskRepository.existsById(id);
    }

    public Optional<Disk> getOne(int id){
        return diskRepository.findById(id);
    }

    public List<Disk> getAll(){
        return diskRepository.findAll();
    }

    public void save(Disk disk){
        diskRepository.save(disk);
    }

    public List<Disk> getByName(String name){
        return diskRepository.findByCoincidenceInName(name);
    }
}
