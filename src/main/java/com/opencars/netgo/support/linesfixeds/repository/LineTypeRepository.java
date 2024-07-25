package com.opencars.netgo.support.linesfixeds.repository;

import com.opencars.netgo.support.linesfixeds.entity.LineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineTypeRepository extends JpaRepository<LineType, Integer> {

    @Query("SELECT r FROM LineType r where r.type like %:type%")
    List<LineType> findByCoincidence (String type);
}
