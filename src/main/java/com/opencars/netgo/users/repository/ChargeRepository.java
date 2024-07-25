package com.opencars.netgo.users.repository;

import com.opencars.netgo.users.entity.Charge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargeRepository extends JpaRepository<Charge, Integer> {

    boolean existsByName(String name);

    @Query("SELECT r FROM Charge r order by r.name ASC")
    List<Charge> findAllOrderByName();
    @Query("SELECT r FROM Charge r where r.name like %:name%")
    List<Charge> findByCoincidenceInName(String name);
}
