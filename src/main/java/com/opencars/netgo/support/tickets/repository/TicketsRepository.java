package com.opencars.netgo.support.tickets.repository;

import com.opencars.netgo.support.tickets.dto.TicketsResume;
import com.opencars.netgo.support.tickets.entity.Tickets;
import com.opencars.netgo.support.tickets.entity.TicketsCategories;
import com.opencars.netgo.support.tickets.entity.TicketsStates;
import com.opencars.netgo.support.tickets.entity.TicketsSubcategories;
import com.opencars.netgo.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketsRepository extends JpaRepository<Tickets, Long> {

    @Query("SELECT new com.opencars.netgo.support.tickets.dto.TicketsResume(r.id, r.title, c.category, s.subCategory, ss.subSubCategory, st, r.openingDate, r.editDate, r.closingDate, a.name) FROM Tickets r INNER JOIN r.category c INNER JOIN r.subCategory s INNER JOIN r.subSubCategory ss INNER JOIN r.state st INNER JOIN r.applicant a WHERE r.applicant.id = :applicantId")
    Page<TicketsResume> findByApplicant(int applicantId, Pageable pageable);

    @Query("SELECT new com.opencars.netgo.support.tickets.dto.TicketsResume(r.id, r.title, c.category, s.subCategory, ss.subSubCategory, st, r.openingDate, r.editDate, r.closingDate, a.name) FROM Tickets r INNER JOIN r.category c INNER JOIN r.subCategory s INNER JOIN r.subSubCategory ss INNER JOIN r.state st INNER JOIN r.applicant a WHERE :observer MEMBER OF r.observers")
    Page<TicketsResume> findByObserver(@Param("observer") User observer, Pageable pageable);

    @Query("SELECT new com.opencars.netgo.support.tickets.dto.TicketsResume(r.id, r.title, c.category, s.subCategory, ss.subSubCategory, st, r.openingDate, r.editDate, r.closingDate, a.name) FROM Tickets r INNER JOIN r.category c INNER JOIN r.subCategory s INNER JOIN r.subSubCategory ss INNER JOIN r.state st INNER JOIN r.applicant a WHERE r.category.id = :category and r.subCategory.id = :subcategory")
    Page<TicketsResume> findByCategoryAndSubcategory(int category, int subcategory, Pageable pageable);

    @Query("SELECT new com.opencars.netgo.support.tickets.dto.TicketsResume(r.id, r.title, c.category, s.subCategory, ss.subSubCategory, st, r.openingDate, r.editDate, r.closingDate, a.name) FROM Tickets r INNER JOIN r.category c INNER JOIN r.subCategory s INNER JOIN r.subSubCategory ss INNER JOIN r.state st INNER JOIN r.applicant a WHERE r.category.id = :category and r.subCategory.id = :subcategory and r.state.id = :state")
    Page<TicketsResume> findByCategoryAndSubcategoryAndState(int category, int subcategory, int state, Pageable pageable);

    @Query("SELECT new com.opencars.netgo.support.tickets.dto.TicketsResume(r.id, r.title, c.category, s.subCategory, ss.subSubCategory, st, r.openingDate, r.editDate, r.closingDate, a.name) FROM Tickets r INNER JOIN r.category c INNER JOIN r.subCategory s INNER JOIN r.subSubCategory ss INNER JOIN r.state st INNER JOIN r.applicant a WHERE r.category.id = :category and r.subCategory.id != 2 and r.subCategory.id != 3")
    Page<TicketsResume> findTicketsSupportIT(int category, Pageable pageable);

    @Query("SELECT new com.opencars.netgo.support.tickets.dto.TicketsResume(r.id, r.title, c.category, s.subCategory, ss.subSubCategory, st, r.openingDate, r.editDate, r.closingDate, a.name) FROM Tickets r INNER JOIN r.category c INNER JOIN r.subCategory s INNER JOIN r.subSubCategory ss INNER JOIN r.state st INNER JOIN r.applicant a WHERE r.category.id = :category and r.state.id = :state and r.subCategory.id != 2 and r.subCategory.id != 3")
    Page<TicketsResume> findTicketsSupportITAndState(int category, int state, Pageable pageable);

    @Query("SELECT new com.opencars.netgo.support.tickets.dto.TicketsResume(r.id, r.title, c.category, s.subCategory, ss.subSubCategory, st, r.openingDate, r.editDate, r.closingDate, a.name) FROM Tickets r INNER JOIN r.category c INNER JOIN r.subCategory s INNER JOIN r.subSubCategory ss INNER JOIN r.state st INNER JOIN r.applicant a WHERE r.category.id = :category and r.applicant.id = :applicant ORDER BY r.editDate DESC")
    List<TicketsResume> findByCategoryAndApplicantCoincidence(int category, int applicant);

    @Query("SELECT new com.opencars.netgo.support.tickets.dto.TicketsResume(r.id, r.title, c.category, s.subCategory, ss.subSubCategory, st, r.openingDate, r.editDate, r.closingDate, a.name) FROM Tickets r INNER JOIN r.category c INNER JOIN r.subCategory s INNER JOIN r.subSubCategory ss INNER JOIN r.state st INNER JOIN r.applicant a WHERE r.category.id = :category and r.id = :id ORDER BY r.editDate DESC")
    List<TicketsResume> findByIdCoincidence(int category, Long id);

    @Query("SELECT new com.opencars.netgo.support.tickets.dto.TicketsResume(r.id, r.title, c.category, s.subCategory, ss.subSubCategory, st, r.openingDate, r.editDate, r.closingDate, a.name) FROM Tickets r INNER JOIN r.category c INNER JOIN r.subCategory s INNER JOIN r.subSubCategory ss INNER JOIN r.state st INNER JOIN r.applicant a WHERE r.resolved.id = :resolved ORDER BY r.editDate DESC")
    List<TicketsResume> findByTechnician(int resolved);

    @Query("SELECT new com.opencars.netgo.support.tickets.dto.TicketsResume(r.id, r.title, c.category, s.subCategory, ss.subSubCategory, st, r.openingDate, r.editDate, r.closingDate, a.name) FROM Tickets r INNER JOIN r.category c INNER JOIN r.subCategory s INNER JOIN r.subSubCategory ss INNER JOIN r.state st INNER JOIN r.applicant a WHERE r.resolved.id = :technician and r.category.id = :category and r.state.id = :state")
    List<TicketsResume> findTicketsByCategoryTechnicianAndState(int technician, int category, int state);

    @Query("SELECT COUNT(r) FROM Tickets r WHERE r.category = :category and r.subCategory = :subcategory and r.state = :state")
    int countTicketsByCategorySubcategoryAndState(TicketsCategories category, TicketsSubcategories subcategory, TicketsStates state);

    @Query("SELECT COUNT(r) FROM Tickets r WHERE r.category = :category and r.subCategory = :subcategory")
    int countTicketsByCategorAndSubcategory(TicketsCategories category, TicketsSubcategories subcategory);

    @Query("SELECT COUNT(r) FROM Tickets r WHERE r.category = :category and month(r.openingDate) = :monthInit and year(r.openingDate) = :year")
    int countTicketsByCategorAndMonthsOfCurrentYear(TicketsCategories category, int monthInit, int year);

    @Query("SELECT COUNT(r) FROM Tickets r WHERE r.category = :category and r.subCategory != 2 and r.subCategory != 3 and r.state = :state")
    int countTicketsIT(TicketsCategories category, TicketsStates state);

    @Query("SELECT COUNT(r) FROM Tickets r WHERE r.observers like :observer and r.state = :state")
    int countTicketsObserverByState(User observer, TicketsStates state);

    @Query("SELECT COUNT(r) FROM Tickets r WHERE r.applicant like :applicant and r.state = :state")
    int countTicketsApplicantByState(User applicant, TicketsStates state);

    @Query("SELECT COUNT(r) FROM Tickets r WHERE r.category = :category and r.resolved = :technician")
    int countTicketsByCategoryAndTechnician(TicketsCategories category, User technician);

    @Query("SELECT COUNT(r) FROM Tickets r WHERE r.category = :category")
    int countTicketsByCategory(TicketsCategories category);

    @Query("SELECT COUNT(r) FROM Tickets r WHERE r.category = :category and r.state = 1")
    int countTicketsByCategoryNotManaged(TicketsCategories category);

    @Query("SELECT COUNT(r) FROM Tickets r WHERE r.category = :category and r.state != 1")
    int countTicketsByCategoryManageds(TicketsCategories category);

    @Query("SELECT COUNT(r) FROM Tickets r WHERE r.category = :category and r.state != 1 and r.resolved != :technician")
    int countTicketsByCategoryAndOthersTechnicians(TicketsCategories category, User technician);

    @Query("SELECT COUNT(r) FROM Tickets r WHERE r.category = :category and r.resolved = :technician and r.state = :state")
    int countTicketsByCategoryStateAndTechnician(TicketsCategories category, User technician, TicketsStates state);

    @Query("SELECT COUNT(r) FROM Tickets r where r.category = :category and month(r.openingDate) = :monthInit and year(r.openingDate) = :year")
    int countTicketsLastMonth(TicketsCategories category, int monthInit, int year);

    @Query("SELECT COUNT(r) FROM Tickets r where r.category = :category and month(r.openingDate) = :monthInit and year(r.openingDate) = :year and r.state = 1")
    int countTicketsLastMonthNotManageds(TicketsCategories category, int monthInit, int year);

    @Query("SELECT COUNT(r) FROM Tickets r where r.category = :category and month(r.openingDate) = :monthInit and year(r.openingDate) = :year and r.state != 1")
    int countTicketsLastMonthManageds(TicketsCategories category, int monthInit, int year);

    @Query("SELECT COUNT(r) FROM Tickets r where r.category = :category and day(r.openingDate) = :day and month(r.openingDate) = :monthInit and year(r.openingDate) = :year")
    int countTicketsCurrentDay(TicketsCategories category, int day, int monthInit, int year);

    @Query("SELECT COUNT(r) FROM Tickets r where r.category = :category and day(r.openingDate) = :day and month(r.openingDate) = :monthInit and year(r.openingDate) = :year and r.state = 1")
    int countTicketsCurrentDayNotManaged(TicketsCategories category, int day, int monthInit, int year);

    @Query("SELECT COUNT(r) FROM Tickets r where r.category = :category and day(r.editDate) = :day and month(r.editDate) = :monthInit and year(r.editDate) = :year and r.state != 1")
    int countTicketsCurrentDayManageds(TicketsCategories category, int day, int monthInit, int year);
}