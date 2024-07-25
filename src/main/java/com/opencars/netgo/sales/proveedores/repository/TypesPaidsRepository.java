package com.opencars.netgo.sales.proveedores.repository;

import com.opencars.netgo.sales.proveedores.entity.TypesPaids;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypesPaidsRepository extends JpaRepository<TypesPaids, Integer>{

    @Query("SELECT r FROM TypesPaids r where r.type like %:type%")
    List<TypesPaids> findByCoincidence(String type);

}