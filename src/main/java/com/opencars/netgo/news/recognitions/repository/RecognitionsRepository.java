package com.opencars.netgo.news.recognitions.repository;

import com.opencars.netgo.news.recognitions.dto.RecognitionsList;
import com.opencars.netgo.news.recognitions.entity.Recognition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecognitionsRepository extends JpaRepository<Recognition, Integer> {

    List<Recognition> findByTitle(String name);
    boolean existsByTitle(String name);

    @Query("SELECT r FROM Recognition r WHERE r.shortDate = :shortDate and r.published = 1 ORDER BY r.createdAt DESC")
    List<Recognition> findByShortDate(LocalDate shortDate);

    @Query("SELECT new com.opencars.netgo.news.recognitions.dto.RecognitionsList(r.id, r.title, r.createdAt) FROM Recognition r where r.published = :published ORDER BY r.createdAt DESC")
    List<RecognitionsList> findAllByPublishedState(int published);

    @Query("SELECT new com.opencars.netgo.news.recognitions.dto.RecognitionsList(r.id, r.title) FROM Recognition r where r.createdAt BETWEEN :init AND :end and r.published = 1 ORDER BY r.createdAt DESC")
    List<RecognitionsList> findListBetweenDates(LocalDateTime init, LocalDateTime end);

    @Query("SELECT COUNT(r) FROM Recognition r where r.shortDate = :shortDate and r.published = 1")
    int countRecognitionsByDate(LocalDate shortDate);

    @Query("SELECT COUNT(r) FROM Recognition r WHERE r.published = 0")
    int countRecognitionsNotPublisheds();
}
