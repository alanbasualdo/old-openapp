package com.opencars.netgo.dms.quoter.repository;

import com.opencars.netgo.dms.quoter.entity.YearsPercents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface YearsPercentsRepository extends JpaRepository<YearsPercents, Integer> {

    @Query("SELECT r FROM YearsPercents r where r.year = :year")
    Optional<YearsPercents> findByYear(int year);
}
