package com.opencars.netgo.sales.viaticos.repository;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.sales.viaticos.entity.TableContable;
import com.opencars.netgo.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TableContableRepository extends JpaRepository<TableContable, Integer>{

    boolean existsByBranch(Branch branch);

    Optional<TableContable> findByBranch(Branch branch);

    @Query("SELECT r.branch FROM TableContable r where r.analyst = :analyst")
    List<Branch> findBranchsForAnalyst(User analyst);
}
