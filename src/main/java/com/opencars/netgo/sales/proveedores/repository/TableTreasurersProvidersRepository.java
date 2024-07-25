package com.opencars.netgo.sales.proveedores.repository;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.sales.proveedores.entity.TableTreasurersProviders;
import com.opencars.netgo.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TableTreasurersProvidersRepository extends JpaRepository<TableTreasurersProviders, Integer> {

    boolean existsByBranch(Branch branch);

    Optional<TableTreasurersProviders> findByBranch(Branch branch);

    @Query("SELECT r.branch FROM TableTreasurersProviders r where r.treasurer = :treasurer")
    List<Branch> findBranchsForTreasurer(User treasurer);
}
