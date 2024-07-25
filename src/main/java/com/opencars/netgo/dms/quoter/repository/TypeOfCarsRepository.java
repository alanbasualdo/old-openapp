package com.opencars.netgo.dms.quoter.repository;

import com.opencars.netgo.dms.quoter.entity.TypeOfCars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeOfCarsRepository extends JpaRepository<TypeOfCars, Integer> {

}
