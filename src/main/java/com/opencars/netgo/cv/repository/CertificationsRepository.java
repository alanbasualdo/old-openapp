package com.opencars.netgo.cv.repository;

import com.opencars.netgo.cv.entity.Certifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificationsRepository extends JpaRepository<Certifications, Integer> {

    @Query("SELECT r FROM Certifications r where r.course LIKE %:coincidence%")
    List<Certifications> findByCoincidence(String coincidence);

}