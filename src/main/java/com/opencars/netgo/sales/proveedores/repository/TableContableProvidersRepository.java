package com.opencars.netgo.sales.proveedores.repository;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.sales.proveedores.entity.TableContableProviders;
import com.opencars.netgo.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TableContableProvidersRepository extends JpaRepository<TableContableProviders, Integer> {

    boolean existsByBranch(Branch branch);

    Optional<TableContableProviders> findByBranch(Branch branch);

    @Query("SELECT r.branch FROM TableContableProviders r where r.analyst = :analyst")
    List<Branch> findBranchsForAnalyst(User analyst);
}
