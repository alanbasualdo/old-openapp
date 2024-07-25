package com.opencars.netgo.benefitcars.repository;

import com.opencars.netgo.benefitcars.entity.BenefitCars;
import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.locations.entity.Sector;
import com.opencars.netgo.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BenefitCarsRepository extends JpaRepository<BenefitCars, Long> {
    List<BenefitCars> findByColaboratorOrderByIdDesc(User colaborator);

    @Query("SELECT r FROM BenefitCars r WHERE r.colaborator.branch = :branch ORDER BY r.id DESC ")
    List<BenefitCars> findByBranchManager(Branch branch);

    @Query("SELECT r FROM BenefitCars r INNER JOIN User u ON r.colaborator.id = u.id INNER JOIN Position p ON p.position.id = p.id INNER JOIN SubSector s ON p.subSector.id = s.id INNER JOIN Sector a ON s.sector.id = a.id WHERE a = :sector AND r.stateInternal.id >= 2 ORDER BY r.id DESC ")
    List<BenefitCars> findBySectorManager(Sector sector);

    @Query("SELECT r FROM BenefitCars r WHERE r.stateInternal.id >= 3 ORDER BY r.id DESC ")
    List<BenefitCars> findAllForVOManager();

    @Query("SELECT r FROM BenefitCars r WHERE r.stateInternal.id >= 4 ORDER BY r.id DESC ")
    List<BenefitCars> findAllForGeneralManager();

    @Query("SELECT r FROM BenefitCars r WHERE r.stateInternal.id >= 5 ORDER BY r.id DESC ")
    List<BenefitCars> findAllForCCHHManager();

    @Query("SELECT r FROM BenefitCars r WHERE r.stateInternal.id >= 6 ORDER BY r.id DESC ")
    List<BenefitCars> findAllForPresident();

    @Query("SELECT r FROM BenefitCars r WHERE r.stateInternal.id >= 7 ORDER BY r.id DESC ")
    List<BenefitCars> findAllForRRLL();

    @Query("SELECT r from BenefitCars r WHERE r.colaborator = :colaborator AND DATEDIFF(:date, r.dateGranting) < 365")
    Optional<BenefitCars> findBenefitCardGrantedForColaboratorOnLastYear(User colaborator, LocalDate date);

    @Query("SELECT r from BenefitCars r WHERE r.colaborator = :colaborator AND (DATEDIFF(:date, r.dateCreated) < 365) AND r.dateGranting = null")
    Optional<BenefitCars> findBenefitCardRequestForColaboratorOnLastYear(User colaborator, LocalDate date);
}
