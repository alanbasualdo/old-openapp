package com.opencars.netgo.auth.service;

import com.opencars.netgo.auth.entity.LogDailyUse;
import com.opencars.netgo.auth.repository.LogDailyUseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LogDailyUseService {

    @Autowired
    LogDailyUseRepository logDailyUseRepository;

    public void save(LogDailyUse logDailyUse){
        logDailyUseRepository.save(logDailyUse);
    }

}
