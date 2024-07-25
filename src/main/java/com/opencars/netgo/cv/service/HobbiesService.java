package com.opencars.netgo.cv.service;

import com.opencars.netgo.cv.entity.Hobbies;
import com.opencars.netgo.cv.repository.HobbiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HobbiesService {

    @Autowired
    HobbiesRepository hobbiesRepository;

    public boolean existsById(int id){
        return hobbiesRepository.existsById(id);
    }

    public Optional<Hobbies> getOne(int id){
        return hobbiesRepository.findById(id);
    }

    public List<Hobbies> getAll(){
        return hobbiesRepository.findAll();
    }

    public void save(Hobbies hobbies){
        hobbiesRepository.save(hobbies);
    }

    public void deleteById(int id){
        hobbiesRepository.deleteById(id);
    }


}
