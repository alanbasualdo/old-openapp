package com.opencars.netgo.dms.calidad.service;

import com.opencars.netgo.dms.calidad.entity.ReportRegister;
import com.opencars.netgo.dms.calidad.repository.ReportCalidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReportCalidadService {

    @Autowired
    ReportCalidadRepository reportCalidadRepository;

    public Optional<ReportRegister> getByIdOptional(int id){
        return reportCalidadRepository.findByIdOPtional(id);
    }

    public Optional<ReportRegister> getOne(int id){
        return reportCalidadRepository.findReportById(id);
    }
    public List<ReportRegister> getById(int id){
        return reportCalidadRepository.findById(id);
    }

    public void save(ReportRegister register){
        reportCalidadRepository.save(register);
    }
}
