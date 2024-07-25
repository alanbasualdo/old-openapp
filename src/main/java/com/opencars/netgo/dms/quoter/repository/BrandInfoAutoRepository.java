package com.opencars.netgo.dms.quoter.repository;

import com.opencars.netgo.dms.quoter.entity.BrandInfoAuto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandInfoAutoRepository extends JpaRepository<BrandInfoAuto, Integer> {

}
