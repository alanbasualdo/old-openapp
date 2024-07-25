package com.opencars.netgo.locations.repository;

import com.opencars.netgo.locations.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    @Query("SELECT r FROM Company r where r.name like %:name%")
    List<Company> findByCoincidenceInName (String name);
}
