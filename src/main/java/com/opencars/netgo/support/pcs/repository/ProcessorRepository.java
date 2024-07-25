package com.opencars.netgo.support.pcs.repository;

import com.opencars.netgo.support.pcs.entity.Processor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessorRepository extends JpaRepository<Processor, Integer> {

    @Query("SELECT r FROM Processor r where r.model like %:name%")
    List<Processor> findByCoincidenceInName (String name);

}
