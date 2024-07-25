package com.opencars.netgo.sales.proveedores.repository;

import com.opencars.netgo.sales.proveedores.entity.ExceptedsConcepts;
import com.opencars.netgo.sales.proveedores.entity.SubcateorizedConcepts;
import com.opencars.netgo.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ExceptedsConceptsRepository extends JpaRepository<ExceptedsConcepts, Integer> {

    @Query("SELECT r.analyst FROM ExceptedsConcepts r where r.concept = :concept")
    User findAnalystForConcept(SubcateorizedConcepts concept);

    boolean existsByConceptId(int id);

}
