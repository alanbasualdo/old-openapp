package com.opencars.netgo.locations.repository;

import com.opencars.netgo.locations.entity.Sector;
import com.opencars.netgo.locations.entity.SubSector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubSectorRepository extends JpaRepository<SubSector, Integer> {

    List<SubSector> findBySector(Sector sector);
    Optional<SubSector> findByName(String name);

    //Si es 0 no hay subsector para el area pasada por parametro
    @Query("SELECT COUNT(r) FROM SubSector r WHERE r.sector = :sector")
    int countSubsector(Sector sector);

}
