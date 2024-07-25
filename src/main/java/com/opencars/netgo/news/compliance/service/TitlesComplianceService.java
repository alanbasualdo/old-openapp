package com.opencars.netgo.news.compliance.service;

import com.opencars.netgo.news.compliance.entity.Titles;
import com.opencars.netgo.news.compliance.repository.TitlesComplianceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TitlesComplianceService {

    @Autowired
    TitlesComplianceRepository titlesComplianceRepository;

    public Optional<Titles> getOne(int id){
        return titlesComplianceRepository.findById(id);
    }

    public List<Titles> getAll(){
        return titlesComplianceRepository.findAll();
    }

    public void save(Titles title){
        titlesComplianceRepository.save(title);
    }
}
