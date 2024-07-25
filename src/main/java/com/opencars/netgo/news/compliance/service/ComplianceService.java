package com.opencars.netgo.news.compliance.service;

import com.opencars.netgo.news.compliance.entity.Compliance;
import com.opencars.netgo.news.compliance.repository.ComplianceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ComplianceService {

    @Autowired
    ComplianceRepository complianceRepository;

    public Optional<Compliance> getOne(int id){
        return complianceRepository.findById(id);
    }

    public Page<Compliance> getAll(Pageable pageable){
        return complianceRepository.findAll(pageable);
    }

    public List<Compliance> getAllNotPaginated(){
        return complianceRepository.findAll();
    }

    public List<Compliance> getByTitleCoincidence(String title){
        return complianceRepository.findComplianceByCoincidence(title);
    }

    public void save(Compliance compliance){
        complianceRepository.save(compliance);
    }
}
