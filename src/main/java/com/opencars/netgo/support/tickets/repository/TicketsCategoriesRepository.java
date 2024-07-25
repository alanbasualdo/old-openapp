package com.opencars.netgo.support.tickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.opencars.netgo.support.tickets.entity.TicketsCategories;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketsCategoriesRepository extends JpaRepository<TicketsCategories, Integer> {

    @Query("SELECT r FROM TicketsCategories r where r.category like %:name%")
    List<TicketsCategories> findByCoincidenceInName (String name);

}
