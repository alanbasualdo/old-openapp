package com.opencars.netgo.support.pcs.repository;

import com.opencars.netgo.support.pcs.entity.PcTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypesRepository extends JpaRepository<PcTypes, Integer> {

    @Query("SELECT r FROM PcTypes r where r.type like %:name%")
    List<PcTypes> findByCoincidenceInName (String name);

}
