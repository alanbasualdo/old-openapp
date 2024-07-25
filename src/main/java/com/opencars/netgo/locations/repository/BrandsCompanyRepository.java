package com.opencars.netgo.locations.repository;

import com.opencars.netgo.locations.entity.BrandsCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandsCompanyRepository extends JpaRepository<BrandsCompany, Integer> {
}
