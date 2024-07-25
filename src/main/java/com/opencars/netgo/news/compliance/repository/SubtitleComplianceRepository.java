package com.opencars.netgo.news.compliance.repository;

import com.opencars.netgo.news.compliance.entity.Subtitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubtitleComplianceRepository extends JpaRepository<Subtitle, Integer> {
}
