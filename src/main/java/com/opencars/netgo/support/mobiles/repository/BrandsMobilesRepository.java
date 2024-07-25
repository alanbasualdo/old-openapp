package com.opencars.netgo.support.mobiles.repository;

import com.opencars.netgo.support.mobiles.entity.BrandsMobiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandsMobilesRepository extends JpaRepository<BrandsMobiles, Integer> {

    @Query("SELECT r FROM BrandsMobiles r where r.name like %:name%")
    List<BrandsMobiles> findByCoincidenceInName (String name);
}
