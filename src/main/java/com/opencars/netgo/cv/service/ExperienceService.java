package com.opencars.netgo.cv.service;

import com.opencars.netgo.cv.entity.Experience;
import com.opencars.netgo.cv.repository.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExperienceService {

    @Autowired
    ExperienceRepository experienceRepository;

    public boolean existsById(int id){
        return experienceRepository.existsById(id);
    }

    public Optional<Experience> getOne(int id){
        return experienceRepository.findById(id);
    }

    public List<Experience> getAll(){
        return experienceRepository.findAll();
    }

    public void save(Experience experience){
        experienceRepository.save(experience);
    }

    public void deleteById(int id){
        experienceRepository.deleteById(id);
    }

}
