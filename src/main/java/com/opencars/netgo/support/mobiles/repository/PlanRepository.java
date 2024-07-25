package com.opencars.netgo.support.mobiles.repository;

import com.opencars.netgo.support.mobiles.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer> {

    @Query("SELECT r FROM Plan r where r.plan like %:plan%")
    List<Plan> findByCoincidenceInName (String plan);
}
