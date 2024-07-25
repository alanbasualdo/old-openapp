package com.opencars.netgo.dms.expertises.service;

import com.opencars.netgo.dms.expertises.dto.DataToExport;
import com.opencars.netgo.dms.expertises.entity.ExpertiseV2;
import com.opencars.netgo.dms.expertises.repository.ExpertiseV2Repository;
import com.opencars.netgo.dms.quoter.entity.Cotization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExpertiseV2Service {

    @Autowired
    ExpertiseV2Repository expertiseV2Repository;

    public List<ExpertiseV2> list(){
        return expertiseV2Repository.findAll();
    }

    public Optional<ExpertiseV2> getOne(Long id){
        return expertiseV2Repository.findById(id);
    }

    public void delete(Long id){
        expertiseV2Repository.deleteById(id);
    }

    public List<ExpertiseV2> getByDomain(String domain){
        return expertiseV2Repository.findByPatent(domain);
    }

    public List<DataToExport> getDataToExport(LocalDate initDate, LocalDate endDate){
        return expertiseV2Repository.findDataToExport(initDate, endDate);
    }

    public List<ExpertiseV2> getByModel(String model){
        return expertiseV2Repository.findByUnit(model);
    }

    public List<ExpertiseV2> getByClient(String name){
        return expertiseV2Repository.findByNameClient(name);
    }

    public Page<ExpertiseV2> findAll(Pageable pageable){
        return expertiseV2Repository.findAll(pageable);
    }

    public boolean existsById(Long id){
        return expertiseV2Repository.existsById(id);
    }

    public boolean existsByCotization(Cotization cotization){
        return expertiseV2Repository.existsByCotization(cotization);
    }

    public void deleteById(Long id){
        expertiseV2Repository.deleteById(id);
    }

    public void save(ExpertiseV2 expertise){
        expertiseV2Repository.save(expertise);
    }
}
