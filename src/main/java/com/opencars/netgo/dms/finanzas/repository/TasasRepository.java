package com.opencars.netgo.dms.finanzas.repository;

import com.opencars.netgo.dms.finanzas.entity.Tasas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TasasRepository extends JpaRepository<Tasas, Integer> {

    List<Tasas> findById(int id);

    @Query("SELECT r FROM Tasas r where r.id = :id")
    Optional<Tasas> findByIdOPtional(int id);
}
