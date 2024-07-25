package com.opencars.netgo.cv.repository;

import com.opencars.netgo.cv.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Integer> {

    @Query("SELECT r FROM Experience r where r.position LIKE %:coincidence%")
    List<Experience> findByCoincidence(String coincidence);

}
