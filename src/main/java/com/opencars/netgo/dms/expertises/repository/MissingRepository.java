package com.opencars.netgo.dms.expertises.repository;

import com.opencars.netgo.dms.expertises.entity.Missing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissingRepository extends JpaRepository<Missing, Integer> {
}
