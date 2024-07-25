package com.opencars.netgo.dms.calidad.repository;

import com.opencars.netgo.dms.calidad.entity.ReportRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportCalidadRepository extends JpaRepository<ReportRegister, Integer> {

    List<ReportRegister> findById(int id);

    @Query("SELECT r FROM ReportRegister r where r.id = :id")
    Optional<ReportRegister> findReportById(int id);

    @Query("SELECT r FROM ReportRegister r where r.id = :id")
    Optional<ReportRegister> findByIdOPtional(int id);
}
