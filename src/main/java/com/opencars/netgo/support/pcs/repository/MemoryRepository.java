package com.opencars.netgo.support.pcs.repository;

import com.opencars.netgo.support.pcs.entity.Memory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoryRepository extends JpaRepository<Memory, Integer> {

    @Query("SELECT r FROM Memory r where r.value like %:name%")
    List<Memory> findByCoincidenceInName (String name);
}
