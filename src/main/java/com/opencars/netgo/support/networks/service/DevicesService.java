package com.opencars.netgo.support.networks.service;

import com.opencars.netgo.support.networks.entity.Devices;
import com.opencars.netgo.support.networks.repository.DevicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DevicesService {

    @Autowired
    DevicesRepository devicesRepository;

    public boolean existsById(int id){
        return devicesRepository.existsById(id);
    }

    public Optional<Devices> getOne(int id){
        return devicesRepository.findById(id);
    }

    public List<Devices> getAll(){
        return devicesRepository.findAll();
    }

    public void save(Devices devices){
        devicesRepository.save(devices);
    }

    public List<Devices> getByCoincidence(String device){
        return devicesRepository.findByCoincidence(device);
    }
}
