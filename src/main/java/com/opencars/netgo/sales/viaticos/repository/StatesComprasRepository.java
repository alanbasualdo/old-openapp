package com.opencars.netgo.sales.viaticos.repository;

import com.opencars.netgo.sales.viaticos.entity.StatesCompras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatesComprasRepository extends JpaRepository<StatesCompras, Integer> {

}
