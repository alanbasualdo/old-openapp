package com.opencars.netgo.sales.proveedores.repository;

import com.opencars.netgo.sales.proveedores.entity.GarajeChiefs;
import com.opencars.netgo.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GarajeChiefsRepository extends JpaRepository<GarajeChiefs, Integer> {

    @Query("SELECT r FROM GarajeChiefs r where r.branch.id = :branch")
    Optional<GarajeChiefs> findChiefByBranch(int branch);

    boolean existsByChief(User chief);
}
