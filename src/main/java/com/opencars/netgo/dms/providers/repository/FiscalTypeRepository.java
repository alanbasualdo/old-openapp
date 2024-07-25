package com.opencars.netgo.dms.providers.repository;

import com.opencars.netgo.dms.providers.entity.FiscalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FiscalTypeRepository extends JpaRepository<FiscalType, Integer> {

    boolean existsByNombre(String nombre);
}
