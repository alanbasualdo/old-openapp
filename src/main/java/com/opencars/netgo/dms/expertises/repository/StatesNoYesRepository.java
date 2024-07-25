package com.opencars.netgo.dms.expertises.repository;

import com.opencars.netgo.dms.expertises.entity.StatesNoYes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatesNoYesRepository extends JpaRepository<StatesNoYes, Integer> {
}
