package com.opencars.netgo.sales.proveedores.repository;

import com.opencars.netgo.sales.proveedores.entity.SubcateorizedConcepts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcateorizedConceptsRepository extends JpaRepository<SubcateorizedConcepts, Integer> {
}
