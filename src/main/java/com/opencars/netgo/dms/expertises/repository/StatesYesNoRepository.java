package com.opencars.netgo.dms.expertises.repository;

import com.opencars.netgo.dms.expertises.entity.StatesYesNo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatesYesNoRepository extends JpaRepository<StatesYesNo, Integer> {
}
