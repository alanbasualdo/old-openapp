package com.opencars.netgo.news.compliance.service;

import com.opencars.netgo.news.compliance.entity.Subtitle;
import com.opencars.netgo.news.compliance.repository.SubtitleComplianceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubtitleComplianceService {

    @Autowired
    SubtitleComplianceRepository subtitleComplianceRepository;

    public Optional<Subtitle> getOne(int id){
        return subtitleComplianceRepository.findById(id);
    }

    public List<Subtitle> getAll(){
        return subtitleComplianceRepository.findAll();
    }

    public void save(Subtitle subtitle){
        subtitleComplianceRepository.save(subtitle);
    }

}
