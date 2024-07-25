package com.opencars.netgo.locations.repository;

import com.opencars.netgo.locations.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {

    @Query("SELECT r FROM Branch r where r.city like %:name%")
    List<Branch> findByCoincidenceInCity (String name);
}
