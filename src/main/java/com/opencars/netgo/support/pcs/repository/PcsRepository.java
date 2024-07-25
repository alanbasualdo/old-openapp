package com.opencars.netgo.support.pcs.repository;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.support.pcs.entity.Pcs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PcsRepository extends JpaRepository<Pcs, Integer> {

    List<Pcs> findAll();
    boolean existsBySn(String sn);
    @Query("SELECT r FROM Pcs r where r.user.name like %:name%")
    List<Pcs> findPcByNameOfUser(String name);
    @Query("SELECT r FROM Pcs r where r.sn like %:sn%")
    List<Pcs> findPcByCoincidenceInSN(String sn);
    @Query("SELECT r FROM Pcs r where r.assigned like :state")
    List<Pcs> findByAssignedState(String state);
    Page<Pcs> findByAssigned(String state, Pageable pageable);
    List<Pcs> findByBranch(Branch branch);
}
