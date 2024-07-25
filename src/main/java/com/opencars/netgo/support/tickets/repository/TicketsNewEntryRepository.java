package com.opencars.netgo.support.tickets.repository;

import com.opencars.netgo.support.tickets.entity.TicketsNewEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketsNewEntryRepository extends JpaRepository<TicketsNewEntry, Integer> {
}
