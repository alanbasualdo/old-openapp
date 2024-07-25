package com.opencars.netgo.cv.service;

import com.opencars.netgo.cv.entity.Education;
import com.opencars.netgo.cv.repository.EducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EducationService {

    @Autowired
    EducationRepository educationRepository;

    public boolean existsById(int id){
        return educationRepository.existsById(id);
    }

    public Optional<Education> getOne(int id){
        return educationRepository.findById(id);
    }

    public List<Education> getAll(){
        return educationRepository.findAll();
    }

    public void save(Education education){
        educationRepository.save(education);
    }

    public void deleteById(int id){
        educationRepository.deleteById(id);
    }

}
