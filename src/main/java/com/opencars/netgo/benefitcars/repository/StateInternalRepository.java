package com.opencars.netgo.benefitcars.repository;

import com.opencars.netgo.benefitcars.entity.StateInternal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateInternalRepository extends JpaRepository<StateInternal, Integer> {

}
