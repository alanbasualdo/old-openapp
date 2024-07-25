package com.opencars.netgo.support.networks.service;

import com.opencars.netgo.support.networks.entity.DeviceType;
import com.opencars.netgo.support.networks.repository.DeviceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DeviceTypeService {

    @Autowired
    DeviceTypeRepository deviceTypeRepository;

    public boolean existsById(int id){
        return deviceTypeRepository.existsById(id);
    }

    public Optional<DeviceType> getOne(int id){
        return deviceTypeRepository.findById(id);
    }

    public List<DeviceType> getAll(){
        return deviceTypeRepository.findAll();
    }

    public void save(DeviceType deviceType){
        deviceTypeRepository.save(deviceType);
    }

    public List<DeviceType> getByCoincidence(String type){
        return deviceTypeRepository.findByCoincidence(type);
    }

}
