package com.opencars.netgo.dms.quoter.repository;

import com.opencars.netgo.dms.quoter.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Integer> {
    @Query("SELECT r FROM Series r where r.brand.codia = :codia ORDER BY r.model ASC")
    List<Series> findByBrandCoincidence(int codia);

}
