package com.opencars.netgo.news.maximuns.service;

import com.opencars.netgo.news.maximuns.entity.Maximuns;
import com.opencars.netgo.news.maximuns.repository.MaximunsRepository;
import com.opencars.netgo.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MaximunsService {

    @Autowired
    MaximunsRepository maximunsRepository;

    public List<Maximuns> getAll(){
        return maximunsRepository.findAll();
    }

    public void save(Maximuns maximun){
        maximunsRepository.save(maximun);
    }

    public Optional<Maximuns> getOne(int id){
        return maximunsRepository.findById(id);
    }

    public List<Maximuns> getOutstanding(int outstanding){
        return maximunsRepository.findByOutstanding(outstanding);
    }
}
