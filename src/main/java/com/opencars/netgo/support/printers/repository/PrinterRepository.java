package com.opencars.netgo.support.printers.repository;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.support.printers.entity.Printer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrinterRepository extends JpaRepository<Printer, Integer> {
    Page<Printer> findByState(String state, Pageable pageable);

    @Query("SELECT COUNT(r) FROM Printer r where r.typeDevice = 'printer'")
    int countTotalPrinters();

    @Query("SELECT COUNT(r) FROM Printer r where r.typeDevice = 'scanner'")
    int countTotalScanners();

    @Query("SELECT COUNT(r) FROM Printer r where r.branch.id = :branch and r.typeDevice = 'printer'")
    int countTotalPrintersByBranch(int branch);

    @Query("SELECT COUNT(r) FROM Printer r where r.branch.id = :branch and r.typeDevice = 'scanner'")
    int countTotalScannersByBranch(int branch);

    @Query("SELECT COUNT(r) FROM Printer r where r.state = 'Asignada' and r.typeDevice = 'printer'")
    int countAssignedsPrinters();

    @Query("SELECT COUNT(r) FROM Printer r where r.state = 'Asignada' and r.typeDevice = 'scanner'")
    int countAssignedsScanner();

    @Query("SELECT COUNT(r) FROM Printer r where r.branch.id = :branch and r.state = 'Asignada' and r.typeDevice = 'printer'")
    int countAssignedsPrintersByBranch(int branch);

    @Query("SELECT COUNT(r) FROM Printer r where r.branch.id = :branch and r.state = 'Asignada' and r.typeDevice = 'scanner'")
    int countAssignedsScannersByBranch(int branch);

    @Query("SELECT COUNT(r) FROM Printer r where r.state = 'Sin asignar' and r.typeDevice = 'printer'")
    int countStockPrinters();

    @Query("SELECT COUNT(r) FROM Printer r where r.state = 'Sin asignar' and r.typeDevice = 'scanner'")
    int countStockScanners();

    @Query("SELECT COUNT(r) FROM Printer r where r.branch.id = :branch and r.state = 'Sin asignar' and r.typeDevice = 'printer'")
    int countStockPrintersByBranch(int branch);

    @Query("SELECT COUNT(r) FROM Printer r where r.branch.id = :branch and r.state = 'Sin asignar' and r.typeDevice = 'scanner'")
    int countStockScannersByBranch(int branch);

    List<Printer> findByBranch(Branch branch);
}
