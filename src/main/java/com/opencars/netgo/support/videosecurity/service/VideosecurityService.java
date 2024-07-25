package com.opencars.netgo.support.videosecurity.service;

import com.opencars.netgo.support.videosecurity.entity.Videosecurity;
import com.opencars.netgo.support.videosecurity.repository.VideosecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VideosecurityService {

    @Autowired
    VideosecurityRepository videosecurityRepository;

    public Optional<Videosecurity> getOne(int id){
        return videosecurityRepository.findById(id);
    }

    public List<Videosecurity> getAll(){
        return videosecurityRepository.findAll();
    }

    public List<Videosecurity> getList(){
        return videosecurityRepository.findAll();
    }

    public void deleteById(int id){
        videosecurityRepository.deleteById(id);
    }

    public List<Videosecurity> getByBranch(int branch){
        return videosecurityRepository.findByBranch(branch);
    }

    public void save(Videosecurity videosecurity){
        videosecurityRepository.save(videosecurity);
    }
}
