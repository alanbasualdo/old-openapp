package com.opencars.netgo.support.linesfixeds.service;

import com.opencars.netgo.support.linesfixeds.entity.LineType;
import com.opencars.netgo.support.linesfixeds.repository.LineTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LineTypeService {

    @Autowired
    LineTypeRepository lineTypeRepository;

    public boolean existsById(int id){
        return lineTypeRepository.existsById(id);
    }

    public Optional<LineType> getOne(int id){
        return lineTypeRepository.findById(id);
    }

    public List<LineType> getAll(){
        return lineTypeRepository.findAll();
    }

    public void save(LineType lineType){
        lineTypeRepository.save(lineType);
    }

    public List<LineType> getByCoincidence(String lineType){
        return lineTypeRepository.findByCoincidence(lineType);
    }

}
