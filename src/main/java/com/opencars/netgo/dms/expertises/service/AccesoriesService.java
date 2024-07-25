package com.opencars.netgo.dms.expertises.service;

import com.opencars.netgo.dms.expertises.entity.Accesories;
import com.opencars.netgo.dms.expertises.repository.AccesoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccesoriesService {

    @Autowired
    AccesoriesRepository accesoriesRepository;

    public List<Accesories> list(){
        return accesoriesRepository.findAll();
    }

    public boolean existsById(int id){
        return accesoriesRepository.existsById(id);
    }

    public Optional<Accesories> getOne(int id){
        return accesoriesRepository.findById(id);
    }

    public void deleteById(int id){
        accesoriesRepository.deleteById(id);
    }

    public List<Accesories> findAll(){
        return accesoriesRepository.findAll();
    }
    public void save(Accesories accesorie){
        accesoriesRepository.save(accesorie);
    }
}
