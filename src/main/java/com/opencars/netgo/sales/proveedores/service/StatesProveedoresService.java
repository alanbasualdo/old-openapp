package com.opencars.netgo.sales.proveedores.service;

import com.opencars.netgo.sales.proveedores.entity.StatesProveedores;
import com.opencars.netgo.sales.proveedores.repository.StatesProveedoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StatesProveedoresService {

    @Autowired
    StatesProveedoresRepository statesProveedoresRepository;

    public List<StatesProveedores> list(){
        return statesProveedoresRepository.findAll();
    }

    public void deleteById(int id){
        statesProveedoresRepository.deleteById(id);
    }

    public Optional<StatesProveedores> getOne(int id){
        return statesProveedoresRepository.findById(id);
    }

    public void save(StatesProveedores stateProveedores){
        statesProveedoresRepository.save(stateProveedores);
    }
}
