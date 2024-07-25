package com.opencars.netgo.dms.expertises.repository;

import com.opencars.netgo.dms.expertises.dto.DataToExport;
import com.opencars.netgo.dms.expertises.entity.ExpertiseV2;
import com.opencars.netgo.dms.quoter.entity.Cotization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpertiseV2Repository extends JpaRepository<ExpertiseV2, Long> {

    @Query("SELECT e FROM ExpertiseV2 e INNER JOIN Cotization co ON e.cotization.id = co.id INNER JOIN Clients c ON co.client.id = c.id WHERE c.nombre LIKE %:nameClient%")
    List<ExpertiseV2> findByNameClient (String nameClient);
    @Query("SELECT e FROM ExpertiseV2 e INNER JOIN Cotization c ON e.cotization.id = c.id WHERE c.model LIKE %:model%")
    List<ExpertiseV2> findByUnit(String model);
    @Query("SELECT r FROM ExpertiseV2 r where r.domain like %:domain%")
    List<ExpertiseV2> findByPatent(String domain);
    boolean existsByCotization(Cotization cotization);
    @Query("SELECT new com.opencars.netgo.dms.expertises.dto.DataToExport(p.id, p.date, c.model, c.patent, cli.nombre, p.costrep) FROM ExpertiseV2 p INNER JOIN p.cotization c ON p.cotization = c.id INNER JOIN c.client cli ON c.client = cli.id WHERE (p.date BETWEEN :initDate AND :endDate)")
    List<DataToExport> findDataToExport(LocalDate initDate, LocalDate endDate);
}
