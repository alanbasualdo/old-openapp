package com.opencars.netgo.sales.viaticos.service;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.sales.viaticos.entity.TableTreasurers;
import com.opencars.netgo.sales.viaticos.repository.TableTreasurersRepository;
import com.opencars.netgo.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TableTreasurersService {

    @Autowired
    TableTreasurersRepository tableTreasurersRepository;

    public void deleteById(int id){
        tableTreasurersRepository.deleteById(id);
    }

    public List<TableTreasurers> list(){
        return tableTreasurersRepository.findAll();
    }

    public List<Branch> getBranchsForTreasurer(User treasurer){
        return tableTreasurersRepository.findBranchsForTreasurer(treasurer);
    }

    public boolean existByBranch(Branch branch){
        return tableTreasurersRepository.existsByBranch(branch);
    }

    public Optional<TableTreasurers> getOne(int id){
        return tableTreasurersRepository.findById(id);
    }

    public Optional<TableTreasurers> getByBranch(Branch branch){
        return tableTreasurersRepository.findByBranch(branch);
    }

    public void save(TableTreasurers tableTreasurers){
        tableTreasurersRepository.save(tableTreasurers);
    }
}
