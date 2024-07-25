package com.opencars.netgo.news.letters.repository;

import com.opencars.netgo.news.letters.dto.LetterList;
import com.opencars.netgo.news.letters.dto.LetterListNotPublished;
import com.opencars.netgo.news.letters.dto.LettersSummary;
import com.opencars.netgo.news.letters.entity.Letter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LettersRepository extends JpaRepository<Letter, Integer> {

    @Query("SELECT r FROM Letter r where r.shortDate = :shortDate ORDER BY r.date DESC")
    List<Letter> getByShortDate(LocalDate shortDate);

    @Query("SELECT new com.opencars.netgo.news.letters.dto.LetterList(r.id, r.shortDate) FROM Letter r where r.published = 1 ORDER BY r.date DESC")
    List<LetterList> findAllPublished();

    @Query("SELECT new com.opencars.netgo.news.letters.dto.LetterListNotPublished(r.id, r.title, r.date) FROM Letter r where r.published = 0 ORDER BY r.date DESC")
    List<LetterListNotPublished> findAllNotPublished();

    @Query("SELECT r FROM Letter r where r.shortDate = :shortDate and r.published = :published ORDER BY r.date DESC")
    List<Letter> findByShortDateAndPublished(LocalDate shortDate, int published);

    @Query("SELECT new com.opencars.netgo.news.letters.dto.LettersSummary(r.id, r.title) FROM Letter r where r.date BETWEEN :init AND :end and r.published = 1 ORDER BY r.date DESC")
    List<LettersSummary> findListBetweenDates(LocalDateTime init, LocalDateTime end);

    @Query("SELECT COUNT(r) FROM Letter r where r.shortDate = :shortDate and r.published = 1")
    int countLettersByDate(LocalDate shortDate);

    @Query("SELECT COUNT(r) FROM Letter r WHERE r.published = 0")
    int countLettersNotPublished();
}
