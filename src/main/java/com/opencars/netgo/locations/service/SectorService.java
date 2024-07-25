package com.opencars.netgo.locations.service;

import com.opencars.netgo.locations.entity.Sector;
import com.opencars.netgo.locations.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SectorService {

    @Autowired
    SectorRepository sectorRepository;

    public boolean existsById(int id){
        return sectorRepository.existsById(id);
    }

    public Optional<Sector> getOne(int id){
        return sectorRepository.findById(id);
    }

    public List<Sector> getAll(){
        return sectorRepository.findAllByOrderByName();
    }

    public List<Sector> getByName(String name){
        return sectorRepository.findByCoincidenceInName(name);
    }

    public void save(Sector sector){
        sectorRepository.save(sector);
    }
}
