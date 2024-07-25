package com.opencars.netgo.dms.quoter.repository;

import com.opencars.netgo.dms.quoter.entity.MileageTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MileageRepository extends JpaRepository<MileageTable, Integer> {

    @Query("SELECT r FROM MileageTable r where :kmAverage BETWEEN r.initKm AND r.endKm")
    Optional<MileageTable> findPercentForKmAverage(long kmAverage);
}
