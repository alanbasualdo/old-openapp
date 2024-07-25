package com.opencars.netgo.updates.repository;

import com.opencars.netgo.updates.entity.PhotoList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PhotoListRepository extends JpaRepository<PhotoList, Long> {

    Page<PhotoList> findAllByOrderByIdDesc(Pageable pageable);

    @Query("SELECT COUNT(r) FROM PhotoList r where r.date = :date")
    int countUpdatesByDate(LocalDate date);
}
