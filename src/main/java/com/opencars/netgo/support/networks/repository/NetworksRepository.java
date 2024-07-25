package com.opencars.netgo.support.networks.repository;

import com.opencars.netgo.support.networks.entity.Networks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NetworksRepository extends JpaRepository<Networks, Integer> {

    @Query("SELECT r FROM Networks r where r.network like %:network%")
    List<Networks> findByCoincidence(String network);
    @Query("SELECT r FROM Networks r where r.branch.id = :id")
    List<Networks> findByBranch(int id);
}
