package com.opencars.netgo.dms.quoter.service;

import com.opencars.netgo.dms.quoter.entity.MileageYears;
import com.opencars.netgo.dms.quoter.repository.MileageYearsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MileageYearsService {

    @Autowired
    MileageYearsRepository mileageYearsRepository;

    public List<MileageYears> list(){
        return mileageYearsRepository.findAll();
    }

    public Optional<MileageYears> getOne(int id){
        return mileageYearsRepository.findById(id);
    }

    public Optional<MileageYears> getPercentForYearsAndKm(int year, long km){
        return mileageYearsRepository.findPercentForYear(year, km);
    }

    public void deleteById(int id){
        mileageYearsRepository.deleteById(id);
    }

    public void save(MileageYears mileageYears){
        mileageYearsRepository.save(mileageYears);
    }
}
