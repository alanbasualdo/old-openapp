package com.opencars.netgo.support.mobiles.service;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.support.mobiles.entity.Mobiles;
import com.opencars.netgo.support.mobiles.repository.MobilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MobilesService {

    @Autowired
    MobilesRepository mobilesRepository;

    public boolean existsById(int id){
        return mobilesRepository.existsById(id);
    }

    public Optional<Mobiles> getOne(int id){
        return mobilesRepository.findById(id);
    }

    public List<Mobiles> getAll(){
        return mobilesRepository.findAll();
    }

    public void save(Mobiles mobiles){
        mobilesRepository.save(mobiles);
    }

    public boolean existsBySn(String sn){
        return mobilesRepository.existsBySn(sn);
    }

    public Optional<Mobiles> getBySn(String sn){
        return mobilesRepository.findBySn(sn);
    }

    public List<Mobiles> getByNameUser(String name){
        return mobilesRepository.findByNameOfUser(name);
    }

    public List<Mobiles> getByCoincidenceInObservation(String coincidence){
        return mobilesRepository.findByCoincidenceInObservation(coincidence);
    }

    public List<Mobiles> getByUserId(int id){
        return mobilesRepository.findByUserId(id);
    }

    public List<Mobiles> getByUse(){
        return mobilesRepository.findByUse();
    }

    public List<Mobiles> getByAssignState(String state){
        return mobilesRepository.findByAssignedState(state);
    }

    public Page<Mobiles> getAll(Pageable pageable){
        return mobilesRepository.findAll(pageable);
    }

    public Page<Mobiles> getMobilesAssignedsPaginated(String state, Pageable pageable){
        return mobilesRepository.findByAssigned(state, pageable);
    }

    public List<Mobiles> getByBranch(Branch branch){
        return mobilesRepository.findByBranch(branch);
    }

    public List<Mobiles> getMobilesList(){
        return mobilesRepository.findAll();
    }

}
