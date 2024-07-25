package com.opencars.netgo.sales.viaticos.service;

import com.opencars.netgo.sales.viaticos.entity.StatesCompras;
import com.opencars.netgo.sales.viaticos.repository.StatesComprasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StatesComprasService {

    @Autowired
    StatesComprasRepository statesComprasRepository;

    public List<StatesCompras> list(){
        return statesComprasRepository.findAll();
    }

    public void deleteById(int id){
        statesComprasRepository.deleteById(id);
    }

    public Optional<StatesCompras> getOne(int id){
        return statesComprasRepository.findById(id);
    }

    public void save(StatesCompras statesCompras){
        statesComprasRepository.save(statesCompras);
    }
}
