package com.opencars.netgo.dms.quoter.service;

import com.opencars.netgo.dms.quoter.entity.BrandsCars;
import com.opencars.netgo.dms.quoter.repository.BrandsCarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BrandsCarsService {

    @Autowired
    BrandsCarsRepository brandsRepository;

    public List<BrandsCars> list(){
        return brandsRepository.findAll();
    }

    public Optional<BrandsCars> getOne(int id){
        return brandsRepository.findById(id);
    }

    public void save(BrandsCars brand){
        brandsRepository.save(brand);
    }

}
