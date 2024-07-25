package com.opencars.netgo.support.mobiles.service;

import com.opencars.netgo.support.mobiles.entity.BrandsMobiles;
import com.opencars.netgo.support.mobiles.entity.LinesMobiles;
import com.opencars.netgo.support.mobiles.repository.LinesMobilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LinesMobilesService {

    @Autowired
    LinesMobilesRepository linesMobilesRepository;

    public boolean existsById(int id){
        return linesMobilesRepository.existsById(id);
    }

    public Optional<LinesMobiles> getOne(int id){
        return linesMobilesRepository.findById(id);
    }

    public Page<LinesMobiles> getAll(Pageable pageable){
        return linesMobilesRepository.findAll(pageable);
    }

    public void save(LinesMobiles linesMobiles){
        linesMobilesRepository.save(linesMobiles);
    }

    public List<LinesMobiles> getByLine(String line){
        return linesMobilesRepository.findByCoincidenceInNumber(line);
    }

}
