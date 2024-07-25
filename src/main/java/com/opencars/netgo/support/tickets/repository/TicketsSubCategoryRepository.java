package com.opencars.netgo.support.tickets.repository;

import com.opencars.netgo.support.tickets.entity.TicketsCategories;
import com.opencars.netgo.support.tickets.entity.TicketsSubcategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketsSubCategoryRepository extends JpaRepository<TicketsSubcategories, Integer> {

    @Query("SELECT r FROM TicketsSubcategories r where r.subCategory like %:name%")
    List<TicketsSubcategories> findByCoincidenceInName (String name);

    @Query("SELECT r FROM TicketsSubcategories r where r.category = :category")
    List<TicketsSubcategories> findByCategory (TicketsCategories category);

}
