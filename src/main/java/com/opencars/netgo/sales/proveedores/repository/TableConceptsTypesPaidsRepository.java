package com.opencars.netgo.sales.proveedores.repository;

import com.opencars.netgo.sales.proveedores.entity.ConceptsProviders;
import com.opencars.netgo.sales.proveedores.entity.SubcateorizedConcepts;
import com.opencars.netgo.sales.proveedores.entity.TableConceptsTypesPaids;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TableConceptsTypesPaidsRepository extends JpaRepository<TableConceptsTypesPaids, Integer> {

    boolean existsByConcept(SubcateorizedConcepts concept);

    Optional<TableConceptsTypesPaids> findByConcept(SubcateorizedConcepts concept);
}
