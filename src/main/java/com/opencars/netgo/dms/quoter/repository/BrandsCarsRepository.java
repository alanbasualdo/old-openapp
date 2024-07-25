package com.opencars.netgo.dms.quoter.repository;

import com.opencars.netgo.dms.quoter.entity.BrandsCars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandsCarsRepository extends JpaRepository<BrandsCars, Integer> {


}
