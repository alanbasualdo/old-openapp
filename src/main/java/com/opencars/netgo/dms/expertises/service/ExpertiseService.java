package com.opencars.netgo.dms.expertises.service;

import com.opencars.netgo.dms.expertises.dto.DataToExport;
import com.opencars.netgo.dms.expertises.dto.DataToExportOld;
import com.opencars.netgo.dms.expertises.repository.ExpertiseRepository;
import com.opencars.netgo.dms.expertises.entity.Expertise;
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
public class ExpertiseService {

    @Autowired
    ExpertiseRepository expertiseRepository;

    public List<Expertise> list(){
        return expertiseRepository.findAll();
    }

    public Optional<Expertise> getOne(Long id){
        return expertiseRepository.findById(id);
    }

    public void delete(Long id){
        expertiseRepository.deleteById(id);
    }

    public List<DataToExportOld> getDataToExport(LocalDate initDate, LocalDate endDate){
        return expertiseRepository.findDataToExport(initDate, endDate);
    }

    public List<Expertise> getByDomain(String domain){
        return expertiseRepository.findByPatent(domain);
    }

    public List<Expertise> getByModel(String model){
        return expertiseRepository.findByUnit(model);
    }

    public List<Expertise> getByClient(String name){
        return expertiseRepository.findByNameClient(name);
    }

    public Page<Expertise> findAll(Pageable pageable){
        return expertiseRepository.findAll(pageable);
    }

    public boolean existsById(Long id){
        return expertiseRepository.existsById(id);
    }

    public void deleteById(Long id){
        expertiseRepository.deleteById(id);
    }

    public void save(Expertise expertise){
        expertiseRepository.save(expertise);
    }

}
