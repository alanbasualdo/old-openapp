package com.opencars.netgo.cv.repository;

import com.opencars.netgo.cv.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<Education, Integer> {

    @Query("SELECT r FROM Education r where r.title LIKE %:coincidence%")
    List<Education> findByCoincidence(String coincidence);

}
