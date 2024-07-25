package com.opencars.netgo.support.linesfixeds.repository;

import com.opencars.netgo.support.linesfixeds.entity.LinesFixeds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinesFixedsRepository extends JpaRepository<LinesFixeds, Integer> {

    @Query("SELECT r FROM LinesFixeds r where r.line like %:line%")
    List<LinesFixeds> findByCoincidence (String line);

    @Query("SELECT r FROM LinesFixeds r where r.useLine like %:use%")
    List<LinesFixeds> findByCoincidenceInUse (String use);

    @Query("SELECT r FROM LinesFixeds r where r.useLine like %:use% and r.type.type = :type")
    List<LinesFixeds> findByCoincidenceInUseAndType(String use, String type);

    @Query("SELECT r FROM LinesFixeds r where r.type.type = :type")
    List<LinesFixeds> findByType (String type);

    @Query("SELECT r FROM LinesFixeds r where r.branch.id = :id")
    List<LinesFixeds> findByBranch(int id);

    @Query("SELECT r FROM LinesFixeds r where r.branch.id = :id and r.type.type = :type")
    List<LinesFixeds> findByBranchAndType(int id, String type);
}
