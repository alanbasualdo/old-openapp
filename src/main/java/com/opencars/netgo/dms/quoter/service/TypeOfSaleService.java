package com.opencars.netgo.dms.quoter.service;

import com.opencars.netgo.dms.quoter.entity.TypeOfSale;
import com.opencars.netgo.dms.quoter.repository.TypeOfSaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TypeOfSaleService {

    @Autowired
    TypeOfSaleRepository typeOfSaleRepository;

    public List<TypeOfSale> list(){
        return typeOfSaleRepository.findAll();
    }

    public Optional<TypeOfSale> getOne(int id){
        return typeOfSaleRepository.findById(id);
    }

    public void deleteById(int id){
        typeOfSaleRepository.deleteById(id);
    }

    public void save(TypeOfSale typeOfSale){
        typeOfSaleRepository.save(typeOfSale);
    }
}
