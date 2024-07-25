package com.opencars.netgo.dms.expertises.repository;

import com.opencars.netgo.dms.expertises.dto.DataToExport;
import com.opencars.netgo.dms.expertises.dto.DataToExportOld;
import com.opencars.netgo.dms.expertises.entity.Expertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpertiseRepository extends JpaRepository<Expertise, Long> {

    @Query("SELECT r FROM Expertise r where r.name_client like %:nameClient%")
    List<Expertise> findByNameClient (String nameClient);
    @Query("SELECT r FROM Expertise r where r.model like %:model%")
    List<Expertise> findByUnit(String model);
    @Query("SELECT r FROM Expertise r where r.domain like %:domain%")
    List<Expertise> findByPatent(String domain);
    @Query("SELECT new com.opencars.netgo.dms.expertises.dto.DataToExportOld(p.id, p.date, p.model, p.domain, p.name_client, p.costrep) FROM Expertise p WHERE (p.date BETWEEN :initDate AND :endDate)")
    List<DataToExportOld> findDataToExport(LocalDate initDate, LocalDate endDate);
}
