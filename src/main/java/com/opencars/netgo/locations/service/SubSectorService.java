package com.opencars.netgo.locations.service;

import com.opencars.netgo.locations.entity.Sector;
import com.opencars.netgo.locations.entity.SubSector;
import com.opencars.netgo.locations.repository.SubSectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubSectorService {

    @Autowired
    SubSectorRepository subSectorRepository;

    public boolean existsById(int id){
        return subSectorRepository.existsById(id);
    }

    public Optional<SubSector> getOne(int id){
        return subSectorRepository.findById(id);
    }

    public List<SubSector> getBySector(Sector sector){
        return subSectorRepository.findBySector(sector);
    }

    public Optional<SubSector> getByName(String name){
        return subSectorRepository.findByName(name);
    }

    public List<SubSector> getAll(){
        return subSectorRepository.findAll();
    }

    public void save(SubSector subSector){
        subSectorRepository.save(subSector);
    }

    public int countSubsector(Sector sector){

        int count = subSectorRepository.countSubsector(sector);

        return count;

    }

}
