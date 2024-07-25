package com.opencars.netgo.support.mobiles.service;

import com.opencars.netgo.support.mobiles.entity.BrandsMobiles;
import com.opencars.netgo.support.mobiles.entity.Plan;
import com.opencars.netgo.support.mobiles.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlanService {

    @Autowired
    PlanRepository planRepository;

    public boolean existsById(int id){
        return planRepository.existsById(id);
    }

    public Optional<Plan> getOne(int id){
        return planRepository.findById(id);
    }

    public List<Plan> getAll(){
        return planRepository.findAll();
    }

    public void save(Plan plan){
        planRepository.save(plan);
    }

    public List<Plan> getByName(String name){
        return planRepository.findByCoincidenceInName(name);
    }
}
