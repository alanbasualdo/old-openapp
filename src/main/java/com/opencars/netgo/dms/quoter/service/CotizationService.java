package com.opencars.netgo.dms.quoter.service;

import com.opencars.netgo.dms.quoter.entity.Cotization;
import com.opencars.netgo.dms.quoter.repository.CotizationRepository;
import com.opencars.netgo.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CotizationService {

    @Autowired
    CotizationRepository cotizationRepository;

    public List<Cotization> getByQuotedBy(User quotedBy){
        return cotizationRepository.findByQuotedBy(quotedBy);
    }

    public List<String> getDistinctPatents(String patent){
        return cotizationRepository.findDistinctPatents(patent);
    }

    public Optional<Cotization> getLastCotizationForDomain(String domain){
        return cotizationRepository.findLastCotizationForDomain(domain);
    }

    public boolean haveCotizationForDomain(String domain){
        return cotizationRepository.findLastCotizationForDomain(domain).isPresent();
    }

    public List<Cotization> getByModel(String model){
        return cotizationRepository.findByUnit(model);
    }
    public List<Cotization> getByClient(String name){
        return cotizationRepository.findByNameClient(name);
    }
    public Page<Cotization> list(Pageable pageable){
        return cotizationRepository.findAll(pageable);
    }
    public Optional<Cotization> getOne(Long id){
        return cotizationRepository.findById(id);
    }
    public void deleteById(Long id){
        cotizationRepository.deleteById(id);
    }
    public void save(Cotization cotization){
        cotizationRepository.save(cotization);
    }
}
