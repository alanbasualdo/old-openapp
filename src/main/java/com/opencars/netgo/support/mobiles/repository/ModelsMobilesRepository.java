package com.opencars.netgo.support.mobiles.repository;

import com.opencars.netgo.support.mobiles.entity.ModelsMobiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelsMobilesRepository extends JpaRepository<ModelsMobiles, Integer> {

    @Query("SELECT r FROM ModelsMobiles r where r.model like %:name%")
    List<ModelsMobiles> findByCoincidenceInName (String name);
}
