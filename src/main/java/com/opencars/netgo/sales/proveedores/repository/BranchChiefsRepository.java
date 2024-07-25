package com.opencars.netgo.sales.proveedores.repository;

import com.opencars.netgo.sales.proveedores.entity.BranchChiefs;
import com.opencars.netgo.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchChiefsRepository extends JpaRepository<BranchChiefs, Integer> {

    @Query("SELECT r FROM BranchChiefs r where r.branch.id = :branch")
    Optional<BranchChiefs> findChiefByBranch(int branch);

    boolean existsByChief(User chief);
}
