package com.opencars.netgo.dms.expertises.repository;

import com.opencars.netgo.dms.expertises.entity.StatesYesNoDoesNotCorrespond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatesYesNoDoesNotCorrespondRepository extends JpaRepository<StatesYesNoDoesNotCorrespond, Integer> {
}
