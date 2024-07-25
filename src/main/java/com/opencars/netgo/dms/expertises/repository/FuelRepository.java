package com.opencars.netgo.dms.expertises.repository;

import com.opencars.netgo.dms.expertises.entity.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelRepository extends JpaRepository<Fuel, Integer> {
}
