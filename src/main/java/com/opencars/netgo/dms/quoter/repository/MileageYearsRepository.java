package com.opencars.netgo.dms.quoter.repository;

import com.opencars.netgo.dms.quoter.entity.MileageYears;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MileageYearsRepository extends JpaRepository<MileageYears, Integer> {

    @Query("SELECT r FROM MileageYears r where r.year = :year and :km BETWEEN r.initKm AND r.endKm")
    Optional<MileageYears> findPercentForYear(int year, long km);
}
