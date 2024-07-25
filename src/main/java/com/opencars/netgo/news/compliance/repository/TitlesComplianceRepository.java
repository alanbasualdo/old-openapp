package com.opencars.netgo.news.compliance.repository;

import com.opencars.netgo.news.compliance.entity.Titles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitlesComplianceRepository extends JpaRepository<Titles, Integer> {
}
