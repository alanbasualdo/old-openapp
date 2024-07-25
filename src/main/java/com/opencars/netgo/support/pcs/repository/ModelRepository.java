package com.opencars.netgo.support.pcs.repository;

import com.opencars.netgo.support.pcs.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {

    @Query("SELECT r FROM Model r where r.model like %:name%")
    List<Model> findByCoincidenceInName (String name);

}
