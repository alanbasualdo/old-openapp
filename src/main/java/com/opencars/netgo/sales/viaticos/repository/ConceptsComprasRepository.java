package com.opencars.netgo.sales.viaticos.repository;

import com.opencars.netgo.sales.viaticos.entity.ConceptsCompras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConceptsComprasRepository extends JpaRepository<ConceptsCompras, Integer> {

}
