package com.opencars.netgo.dms.quoter.service;

import com.opencars.netgo.dms.quoter.entity.MileageTable;
import com.opencars.netgo.dms.quoter.repository.MileageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MileageService {

    @Autowired
    MileageRepository mileageRepository;

    public List<MileageTable> list(){
        return mileageRepository.findAll();
    }

    public Optional<MileageTable> getOne(int id){
        return mileageRepository.findById(id);
    }

    public Optional<MileageTable> getKmRange(long kmAverage){
        return mileageRepository.findPercentForKmAverage(kmAverage);
    }

    public void deleteById(int id){
        mileageRepository.deleteById(id);
    }
    public void save(MileageTable mileageTable){
        mileageRepository.save(mileageTable);
    }
}
