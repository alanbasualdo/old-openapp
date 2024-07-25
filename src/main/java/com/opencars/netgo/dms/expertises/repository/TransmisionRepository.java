package com.opencars.netgo.dms.expertises.repository;

import com.opencars.netgo.dms.expertises.entity.Transmision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransmisionRepository extends JpaRepository<Transmision, Integer> {
}
