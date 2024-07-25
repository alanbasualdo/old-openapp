package com.opencars.netgo.news.welcomes.repository;

import com.opencars.netgo.news.welcomes.dto.ListNames;
import com.opencars.netgo.news.welcomes.dto.WelcomesSummary;
import com.opencars.netgo.news.welcomes.entity.Welcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WelcomeRepository extends JpaRepository<Welcome, Integer> {

   @Query("SELECT r FROM Welcome r where r.shortDate = :shortDate and r.published = :published ORDER BY r.entryDate DESC")
   List<Welcome> findByShortDateAndPublished(LocalDate shortDate, int published);

   List<Welcome> findById(int id);

   @Query("SELECT new com.opencars.netgo.news.welcomes.dto.ListNames(r.id, r.name, r.entryDate) FROM Welcome r where r.published = 1 ORDER BY r.entryDate DESC")
   List<ListNames> findAllPublished();

   @Query("SELECT new com.opencars.netgo.news.welcomes.dto.ListNames(r.id, r.name, r.entryDate) FROM Welcome r where r.published = 0 ORDER BY r.entryDate DESC")
   List<ListNames> findAllNotPublished();

   @Query("SELECT r FROM Welcome r where r.id = :id")
   Optional<Welcome> findWelcomeById(int id);

   @Query("SELECT r FROM Welcome r where r.entryDate BETWEEN :init AND :end and r.published = 1 ORDER BY r.entryDate DESC")
   List<Welcome> findListBetweenDates(LocalDateTime init, LocalDateTime end);

   @Query("SELECT COUNT(r) FROM Welcome r WHERE r.published = 0")
   int countWelcomesNotPublished();

   @Query("SELECT COUNT(r) FROM Welcome r WHERE r.published = 1 and r.shortDate = :date")
   int countWelcomesPublished(LocalDate date);
}
