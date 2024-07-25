package com.opencars.netgo.dms.providers.service;

import com.opencars.netgo.dms.providers.entity.ProvidersSales;
import com.opencars.netgo.dms.providers.repository.ProvidersSalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.AssertTrue;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProvidersSalesService {

    @Autowired
    ProvidersSalesRepository providersSalesRepository;

    public Optional<ProvidersSales> getOne(Long id){
        return providersSalesRepository.findById(id);
    }
    public boolean existsById(Long id){
        return providersSalesRepository.existsById(id);
    }

    public boolean existsByCuit(Long cuit){

        return providersSalesRepository.existsByCuit(cuit);
    }

    public Page<ProvidersSales> getAll(Pageable pageable){
        return providersSalesRepository.findAllByOrderByIdDesc(pageable);
    }
    public List<ProvidersSales> getByName(String name){
        return providersSalesRepository.findProviderByCoincidence(name);
    }

    public List<ProvidersSales> getByCuit(Long cuit){
        return providersSalesRepository.findProviderByCoincidenceInCuit(cuit);
    }

    public void save(ProvidersSales providersSales){
        providersSalesRepository.save(providersSales);
    }
}
