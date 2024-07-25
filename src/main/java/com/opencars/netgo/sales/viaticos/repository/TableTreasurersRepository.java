package com.opencars.netgo.sales.viaticos.repository;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.sales.viaticos.entity.TableTreasurers;
import com.opencars.netgo.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TableTreasurersRepository  extends JpaRepository<TableTreasurers, Integer> {

    boolean existsByBranch(Branch branch);

    Optional<TableTreasurers> findByBranch(Branch branch);

    @Query("SELECT r.branch FROM TableTreasurers r where r.treasurer = :treasurer")
    List<Branch> findBranchsForTreasurer(User treasurer);
}
