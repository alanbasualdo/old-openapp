package com.opencars.netgo.locations.repository;

import com.opencars.netgo.locations.entity.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Integer> {

    @Query("SELECT r FROM Sector r where r.name like %:name%")
    List<Sector> findByCoincidenceInName (String name);

    List<Sector> findAllByOrderByName ();

}
