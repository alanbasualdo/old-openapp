package com.opencars.netgo.support.pcs.repository;

import com.opencars.netgo.support.pcs.entity.Brands;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandsRepository extends JpaRepository<Brands, Integer> {

    @Query("SELECT r FROM Brands r where r.name like %:name%")
    List<Brands> findByCoincidenceInName (String name);

}
