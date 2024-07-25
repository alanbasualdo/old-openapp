package com.opencars.netgo.news.goodbyes.repository;

import com.opencars.netgo.news.goodbyes.entity.Goodbyes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GoodbyesRepository extends JpaRepository<Goodbyes, Integer> {

    boolean existsByDate(LocalDate date);
    List<Goodbyes> findByDate(LocalDate date);
    List<Goodbyes> findByName(String name);
}
