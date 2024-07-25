package com.opencars.netgo.dms.expertises.repository;

import com.opencars.netgo.dms.expertises.entity.Accesories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccesoriesRepository extends JpaRepository<Accesories, Integer> {
}
