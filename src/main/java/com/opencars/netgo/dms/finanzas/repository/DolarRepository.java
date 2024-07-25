package com.opencars.netgo.dms.finanzas.repository;

import com.opencars.netgo.dms.finanzas.entity.Dolar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DolarRepository extends JpaRepository<Dolar, Integer> {

    List<Dolar> findById(int id);

    @Query("SELECT r FROM Dolar r where r.id = :id")
    Optional<Dolar> findByIdOPtional(int id);
}
