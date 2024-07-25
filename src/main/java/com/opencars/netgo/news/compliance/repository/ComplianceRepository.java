package com.opencars.netgo.news.compliance.repository;

import com.opencars.netgo.news.compliance.entity.Compliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplianceRepository extends JpaRepository<Compliance, Integer> {

    @Query("SELECT r FROM Compliance r where r.title like %:title%")
    List<Compliance> findComplianceByCoincidence(String title);

}
