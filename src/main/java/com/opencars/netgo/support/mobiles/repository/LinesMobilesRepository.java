package com.opencars.netgo.support.mobiles.repository;

import com.opencars.netgo.support.mobiles.entity.LinesMobiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinesMobilesRepository extends JpaRepository<LinesMobiles, Integer> {

    @Query("SELECT r FROM LinesMobiles r where r.line like %:line%")
    List<LinesMobiles> findByCoincidenceInNumber(String line);
}
