package com.opencars.netgo.dms.quoter.service;

import com.opencars.netgo.dms.quoter.entity.Series;
import com.opencars.netgo.dms.quoter.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SeriesService {

    @Autowired
    SeriesRepository seriesRepository;

    public List<Series> list(){
        return seriesRepository.findAll();
    }
    public List<Series> listByBrand(int codia){
        return seriesRepository.findByBrandCoincidence(codia);
    }

    public Optional<Series> getOne(int id){
        return seriesRepository.findById(id);
    }

    public void save(Series series){
        seriesRepository.save(series);
    }
}
