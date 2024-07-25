package com.opencars.netgo.support.tickets.repository;

import com.opencars.netgo.support.tickets.entity.TicketsStates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketsStatesRepository extends JpaRepository<TicketsStates, Integer> {

    @Query("SELECT r FROM TicketsStates r where r.state like %:name%")
    List<TicketsStates> findByCoincidenceInName (String name);

}
