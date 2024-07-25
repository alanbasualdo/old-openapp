package com.opencars.netgo.dms.quoter.repository;

import com.opencars.netgo.dms.quoter.entity.TypeOfSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeOfSaleRepository extends JpaRepository<TypeOfSale, Integer> {

}
