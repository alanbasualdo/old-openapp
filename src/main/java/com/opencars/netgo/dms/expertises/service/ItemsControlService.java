package com.opencars.netgo.dms.expertises.service;

import com.opencars.netgo.dms.expertises.entity.ItemsControl;
import com.opencars.netgo.dms.expertises.repository.ItemsControlRepository;
import com.opencars.netgo.dms.quoter.entity.TypeOfCars;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemsControlService {

    @Autowired
    ItemsControlRepository itemsControlRepository;

    public boolean existsById(int id){
        return itemsControlRepository.existsById(id);
    }

    public Optional<ItemsControl> getOne(int id){
        return itemsControlRepository.findById(id);
    }

    public Long findPriceByItemAndTypeOfCars(String item, TypeOfCars typeOfCar){
        return itemsControlRepository.findPriceByItemAndTypeOfCars(item, typeOfCar);
    }

    public List<ItemsControl> getAll(){
        return itemsControlRepository.findAll();
    }

    public void save(ItemsControl itemsControl){
        itemsControlRepository.save(itemsControl);
    }
}
