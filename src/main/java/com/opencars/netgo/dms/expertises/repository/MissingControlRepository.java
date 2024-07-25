package com.opencars.netgo.dms.expertises.repository;

import com.opencars.netgo.dms.expertises.entity.Missing;
import com.opencars.netgo.dms.expertises.entity.MissingControl;
import com.opencars.netgo.dms.quoter.entity.TypeOfCars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MissingControlRepository extends JpaRepository<MissingControl, Integer> {

    @Query("SELECT r.price FROM MissingControl r where r.missing = :missing and r.typeOfCars = :typeOfCar")
    Long findPriceByMissingAndTypeOfCars(Missing missing, TypeOfCars typeOfCar);
}
