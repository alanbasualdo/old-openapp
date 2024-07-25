package com.opencars.netgo.dms.quoter.repository;

import com.opencars.netgo.dms.quoter.entity.ModelsCars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModelsCarsRepository extends JpaRepository<ModelsCars, Integer> {

    @Query("SELECT r FROM ModelsCars r where r.description = :description")
    Optional<ModelsCars> findPercentForModel(String description);
}
