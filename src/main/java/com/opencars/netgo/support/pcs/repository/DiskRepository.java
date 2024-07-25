package com.opencars.netgo.support.pcs.repository;

import com.opencars.netgo.support.pcs.entity.Disk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiskRepository extends JpaRepository<Disk, Integer> {

    @Query("SELECT r FROM Disk r where r.value like %:name%")
    List<Disk> findByCoincidenceInName (String name);

}
