package com.opencars.netgo.dms.expertises.repository;

import com.opencars.netgo.dms.expertises.entity.States;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatesRepository extends JpaRepository<States, Integer> {
}
