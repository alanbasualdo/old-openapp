package com.opencars.netgo.support.pcs.service;

import com.opencars.netgo.support.pcs.entity.Processor;
import com.opencars.netgo.support.pcs.repository.ProcessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProcessorService {

    @Autowired
    ProcessorRepository processorRepository;

    public boolean existsById(int id){
        return processorRepository.existsById(id);
    }

    public Optional<Processor> getOne(int id){
        return processorRepository.findById(id);
    }

    public List<Processor> getAll(){
        return processorRepository.findAll();
    }

    public void save(Processor processor){
        processorRepository.save(processor);
    }

    public List<Processor> getByName(String name){
        return processorRepository.findByCoincidenceInName(name);
    }

}
