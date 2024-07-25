package com.opencars.netgo.support.pcs.service;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.support.pcs.entity.Pcs;
import com.opencars.netgo.support.pcs.repository.PcsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PcsService {

    @Autowired
    PcsRepository pcsRepository;

    public boolean existsBySn(String sn){
        return pcsRepository.existsBySn(sn);
    }

    public boolean existsById(int id){
        return pcsRepository.existsById(id);
    }

    public Optional<Pcs> getOne(int id){
        return pcsRepository.findById(id);
    }

    public List<Pcs> getByNameUser(String name){
        return pcsRepository.findPcByNameOfUser(name);
    }

    public List<Pcs> getBySN(String sn){
        return pcsRepository.findPcByCoincidenceInSN(sn);
    }

    public List<Pcs> getByAssignState(String state){
        return pcsRepository.findByAssignedState(state);
    }

    public Page<Pcs> getAll(Pageable pageable){
        return pcsRepository.findAll(pageable);
    }

    public Page<Pcs> getPcsAssignedsPaginated(String state, Pageable pageable){
        return pcsRepository.findByAssigned(state, pageable);
    }

    public List<Pcs> getPcsByBranch(Branch branch){
        return pcsRepository.findByBranch(branch);
    }

    public List<Pcs> getPcList(){
        return pcsRepository.findAll();
    }

    public void save(Pcs pcs){
        pcsRepository.save(pcs);
    }
}
