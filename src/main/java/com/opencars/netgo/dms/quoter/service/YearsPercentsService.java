package com.opencars.netgo.dms.quoter.service;

import com.opencars.netgo.dms.quoter.entity.YearsPercents;
import com.opencars.netgo.dms.quoter.repository.YearsPercentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class YearsPercentsService {

    @Autowired
    YearsPercentsRepository yearsPercentsRepository;

    public List<YearsPercents> list(){
        return yearsPercentsRepository.findAll();
    }

    public Optional<YearsPercents> getOne(int id){
        return yearsPercentsRepository.findById(id);
    }

    public Optional<YearsPercents> getByYear(int year){
        return yearsPercentsRepository.findByYear(year);
    }

    public void deleteById(int id){
        yearsPercentsRepository.deleteById(id);
    }
    public void save(YearsPercents yearsPercents){
        yearsPercentsRepository.save(yearsPercents);
    }
}
