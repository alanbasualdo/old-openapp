package com.opencars.netgo.cv.repository;

import com.opencars.netgo.cv.entity.Hobbies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HobbiesRepository extends JpaRepository<Hobbies, Integer> {

    @Query("SELECT r FROM Hobbies r where r.hobbie LIKE %:coincidence%")
    List<Hobbies> findByCoincidence(String coincidence);
}
