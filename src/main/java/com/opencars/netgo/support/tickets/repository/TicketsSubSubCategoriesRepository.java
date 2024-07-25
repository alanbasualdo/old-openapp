package com.opencars.netgo.support.tickets.repository;

import com.opencars.netgo.support.tickets.entity.TicketsSubSubCategories;
import com.opencars.netgo.support.tickets.entity.TicketsSubcategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketsSubSubCategoriesRepository extends JpaRepository<TicketsSubSubCategories, Integer> {

    @Query("SELECT r FROM TicketsSubSubCategories r where r.subSubCategory like %:name%")
    List<TicketsSubSubCategories> findByCoincidenceInName (String name);

    @Query("SELECT r FROM TicketsSubSubCategories r where r.subCategory = :subcategory")
    List<TicketsSubSubCategories> findBySubcategory (TicketsSubcategories subcategory);
}
