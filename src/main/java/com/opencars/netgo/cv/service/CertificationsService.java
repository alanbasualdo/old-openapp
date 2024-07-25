package com.opencars.netgo.cv.service;

import com.opencars.netgo.cv.entity.Certifications;
import com.opencars.netgo.cv.repository.CertificationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CertificationsService {

    @Autowired
    CertificationsRepository certificationsRepository;

    public boolean existsById(int id){
        return certificationsRepository.existsById(id);
    }

    public Optional<Certifications> getOne(int id){
        return certificationsRepository.findById(id);
    }

    public List<Certifications> getAll(){
        return certificationsRepository.findAll();
    }

    public void save(Certifications certifications){
        certificationsRepository.save(certifications);
    }

    public void deleteById(int id){
        certificationsRepository.deleteById(id);
    }

}
