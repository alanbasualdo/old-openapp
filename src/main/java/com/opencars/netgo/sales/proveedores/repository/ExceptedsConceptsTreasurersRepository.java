package com.opencars.netgo.sales.proveedores.repository;

import com.opencars.netgo.sales.proveedores.entity.ExceptedsConceptsTreasurers;
import com.opencars.netgo.sales.proveedores.entity.SubcateorizedConcepts;
import com.opencars.netgo.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExceptedsConceptsTreasurersRepository extends JpaRepository<ExceptedsConceptsTreasurers, Integer> {

    @Query("SELECT r.treasurer FROM ExceptedsConceptsTreasurers r where r.concept = :concept")
    User findTreasurerForConcept(SubcateorizedConcepts concept);

    boolean existsByConceptId(int id);
}
