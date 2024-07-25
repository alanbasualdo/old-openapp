package com.opencars.netgo.dms.finanzas.service;

import com.opencars.netgo.dms.finanzas.entity.*;
import com.opencars.netgo.dms.finanzas.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FinanzasService {

    @Autowired
    DolarRepository dolarRepository;

    @Autowired
    RegisterDolarRepository registerDolarRepository;

    @Autowired
    TasasRepository tasasRepository;

    @Autowired
    TNAChequesRegisterRepository tnaChequesRegisterRepository;

    @Autowired
    CardsTasasRegisterRepository cardsTasasRegisterRepository;

    public Optional<Dolar> getByIdOptional(int id){
        return dolarRepository.findByIdOPtional(id);
    }

    public Optional<Tasas> getTNAByIdOptional(int id){
        return tasasRepository.findByIdOPtional(id);
    }

    public Optional<Tasas> getTasaByIdOptional(int id){
        return tasasRepository.findByIdOPtional(id);
    }

    public List<Dolar> getById(int id){
        return dolarRepository.findById(id);
    }

    public boolean existsById(int id){
        return dolarRepository.existsById(id);
    }

    public List<Tasas> getAll(){
        return tasasRepository.findAll();
    }

    public void save(Dolar dolar){
        dolarRepository.save(dolar);
    }

    public void saveRegister(DolarRegister register){
        registerDolarRepository.save(register);
    }

    public void saveRegisterTNA(TNAChequesRegister register){
        tnaChequesRegisterRepository.save(register);
    }

    public void saveRegisterTNACards(CardsTasasRegister register){
        cardsTasasRegisterRepository.save(register);
    }

    public Page<DolarRegister> getAllRegisters(Pageable pageable){
        return registerDolarRepository.findAll(pageable);
    }

    public Page<TNAChequesRegister> getAllTNAChequesRegisters(Pageable pageable){
        return tnaChequesRegisterRepository.findAll(pageable);
    }

    public Page<CardsTasasRegister> getAllCardsTasasRegisters(Pageable pageable){
        return cardsTasasRegisterRepository.findAll(pageable);
    }

    public List<Tasas> getTasaById(int id){
        return tasasRepository.findById(id);
    }

    public void saveTasa(Tasas tasa){
        tasasRepository.save(tasa);
    }

}
