package com.opencars.netgo.benefitcars.repository;

import com.opencars.netgo.benefitcars.entity.StateToColaborator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateToColaboratorRepository extends JpaRepository<StateToColaborator, Integer> {

}
