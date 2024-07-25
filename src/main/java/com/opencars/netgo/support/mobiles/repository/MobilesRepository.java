package com.opencars.netgo.support.mobiles.repository;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.support.mobiles.entity.Mobiles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MobilesRepository extends JpaRepository<Mobiles, Integer> {

    List<Mobiles> findAll();
    boolean existsBySn(String sn);
    Optional<Mobiles> findBySn(String sn);
    @Query("SELECT r FROM Mobiles r where r.user.name like %:name%")
    List<Mobiles> findByNameOfUser(String name);
    @Query("SELECT r FROM Mobiles r where r.observation like %:coincidence%")
    List<Mobiles> findByCoincidenceInObservation(String coincidence);
    @Query("SELECT r FROM Mobiles r where r.user.id = :id")
    List<Mobiles> findByUserId(int id);
    @Query("SELECT r FROM Mobiles r where r.observation != ''")
    List<Mobiles> findByUse();
    @Query("SELECT r FROM Mobiles r where r.assigned like :state")
    List<Mobiles> findByAssignedState(String state);
    Page<Mobiles> findByAssigned(String state, Pageable pageable);
    List<Mobiles> findByBranch(Branch branch);
}
