package com.opencars.netgo.sales.viaticos.service;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.sales.viaticos.entity.TableContable;
import com.opencars.netgo.sales.viaticos.repository.TableContableRepository;
import com.opencars.netgo.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TableContableService {

    @Autowired
    TableContableRepository tableContableRepository;

    public void deleteById(int id){
        tableContableRepository.deleteById(id);
    }

    public List<TableContable> list(){
        return tableContableRepository.findAll();
    }

    public List<Branch> getBranchsForAnalyst(User analyst){
        return tableContableRepository.findBranchsForAnalyst(analyst);
    }

    public boolean existByBranch(Branch branch){
        return tableContableRepository.existsByBranch(branch);
    }

    public Optional<TableContable> getOne(int id){
        return tableContableRepository.findById(id);
    }

    public Optional<TableContable> getByBranch(Branch branch){
        return tableContableRepository.findByBranch(branch);
    }

    public void save(TableContable tableContable){
        tableContableRepository.save(tableContable);
    }
}
