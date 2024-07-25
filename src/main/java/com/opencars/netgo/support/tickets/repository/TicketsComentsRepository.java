package com.opencars.netgo.support.tickets.repository;

import com.opencars.netgo.support.tickets.entity.TicketsComents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketsComentsRepository extends JpaRepository<TicketsComents, Long> {

}
