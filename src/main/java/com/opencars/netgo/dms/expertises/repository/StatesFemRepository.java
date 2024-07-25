package com.opencars.netgo.dms.expertises.repository;

import com.opencars.netgo.dms.expertises.entity.StatesFem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatesFemRepository extends JpaRepository<StatesFem, Integer> {
}
