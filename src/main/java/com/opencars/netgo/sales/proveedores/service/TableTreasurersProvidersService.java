package com.opencars.netgo.sales.proveedores.service;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.sales.proveedores.entity.TableTreasurersProviders;
import com.opencars.netgo.sales.proveedores.repository.TableTreasurersProvidersRepository;
import com.opencars.netgo.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TableTreasurersProvidersService {

    @Autowired
    TableTreasurersProvidersRepository tableTreasurersProvidersRepository;

    public void deleteById(int id){
        tableTreasurersProvidersRepository.deleteById(id);
    }

    public List<TableTreasurersProviders> list(){
        return tableTreasurersProvidersRepository.findAll();
    }

    public List<Branch> getBranchsForTreasurer(User treasurer){
        return tableTreasurersProvidersRepository.findBranchsForTreasurer(treasurer);
    }

    public boolean existByBranch(Branch branch){
        return tableTreasurersProvidersRepository.existsByBranch(branch);
    }

    public Optional<TableTreasurersProviders> getOne(int id){
        return tableTreasurersProvidersRepository.findById(id);
    }

    public Optional<TableTreasurersProviders> getByBranch(Branch branch){
        return tableTreasurersProvidersRepository.findByBranch(branch);
    }

    public void save(TableTreasurersProviders tableTreasurersProviders){
        tableTreasurersProvidersRepository.save(tableTreasurersProviders);
    }
}
