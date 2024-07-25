package com.opencars.netgo.sales.proveedores.service;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.sales.proveedores.entity.GarajeChiefs;
import com.opencars.netgo.sales.proveedores.repository.GarajeChiefsRepository;
import com.opencars.netgo.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GarajeChiefsService {

    @Autowired
    GarajeChiefsRepository garajeChiefsRepository;

    public Optional<GarajeChiefs> getChiefForGaraje(Branch branch){
        return garajeChiefsRepository.findChiefByBranch(branch.getId());
    }

    public boolean existsByChief(User chief){
        return garajeChiefsRepository.existsByChief(chief);
    }

    public void deleteById(int id){
        garajeChiefsRepository.deleteById(id);
    }

    public List<GarajeChiefs> list(){
        return garajeChiefsRepository.findAll();
    }

    public Optional<GarajeChiefs> getOne(int id){
        return garajeChiefsRepository.findById(id);
    }

    public void save(GarajeChiefs garajeChiefs){
        garajeChiefsRepository.save(garajeChiefs);
    }


}
