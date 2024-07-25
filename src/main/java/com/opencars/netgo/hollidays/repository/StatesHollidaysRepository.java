package com.opencars.netgo.hollidays.repository;

import com.opencars.netgo.hollidays.entity.StatesHollidays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatesHollidaysRepository extends JpaRepository<StatesHollidays, Integer> {


}
