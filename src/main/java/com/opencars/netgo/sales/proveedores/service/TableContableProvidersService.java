package com.opencars.netgo.sales.proveedores.service;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.sales.proveedores.entity.TableContableProviders;
import com.opencars.netgo.sales.proveedores.repository.TableContableProvidersRepository;
import com.opencars.netgo.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TableContableProvidersService {

    @Autowired
    TableContableProvidersRepository tableContableProvidersRepository;

    public void deleteById(int id){
        tableContableProvidersRepository.deleteById(id);
    }

    public List<TableContableProviders> list(){
        return tableContableProvidersRepository.findAll();
    }

    public List<Branch> getBranchsForAnalyst(User analyst){
        return tableContableProvidersRepository.findBranchsForAnalyst(analyst);
    }

    public boolean existByBranch(Branch branch){
        return tableContableProvidersRepository.existsByBranch(branch);
    }

    public Optional<TableContableProviders> getOne(int id){
        return tableContableProvidersRepository.findById(id);
    }

    public Optional<TableContableProviders> getByBranch(Branch branch){
        return tableContableProvidersRepository.findByBranch(branch);
    }

    public void save(TableContableProviders tableContableProviders){
        tableContableProvidersRepository.save(tableContableProviders);
    }
}
