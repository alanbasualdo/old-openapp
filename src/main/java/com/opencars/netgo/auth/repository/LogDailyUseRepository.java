package com.opencars.netgo.auth.repository;

import com.opencars.netgo.auth.entity.LogDailyUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogDailyUseRepository extends JpaRepository<LogDailyUse, Long> {

}
