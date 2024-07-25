package com.opencars.netgo.dms.quoter.service;

import com.opencars.netgo.dms.quoter.entity.BrandInfoAuto;
import com.opencars.netgo.dms.quoter.repository.BrandInfoAutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BrandInfoAutoService {

    @Autowired
    BrandInfoAutoRepository brandInfoAutoRepository;

    public List<BrandInfoAuto> list(){
        return brandInfoAutoRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public Optional<BrandInfoAuto> getOne(int id){
        return brandInfoAutoRepository.findById(id);
    }

    public void save(BrandInfoAuto brand){
        brandInfoAutoRepository.save(brand);
    }

    public void saveAll(List<BrandInfoAuto> list){
        brandInfoAutoRepository.saveAll(list);
    }

    public void deleteAll(){
        brandInfoAutoRepository.deleteAll();
    }

}
