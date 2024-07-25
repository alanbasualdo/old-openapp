package com.opencars.netgo.sales.viaticos.repository;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.locations.entity.Company;
import com.opencars.netgo.sales.proveedores.entity.SalesProviders;
import com.opencars.netgo.sales.viaticos.dto.ReportColaborators;
import com.opencars.netgo.sales.viaticos.entity.ConceptsCompras;
import com.opencars.netgo.sales.viaticos.entity.StatesCompras;
import com.opencars.netgo.sales.viaticos.entity.Viaticos;
import com.opencars.netgo.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ViaticosRepository extends JpaRepository<Viaticos, Long> {

    Page<Viaticos> findByColaborator(User colaborator, Pageable pageable);

    Page<Viaticos> findByAuthorizerAndState(User authorizer, StatesCompras state, Pageable pageable);

    Page<Viaticos> findByAnalystAndState(User analyst, StatesCompras state, Pageable pageable);

    @Query("SELECT r FROM Viaticos r where r.analyst = :analyst AND r.state.id < 3 AND r.branch = :branch")
    List<Viaticos> findListByAnalystAndStateNotPostedAndBranch(User analyst, Branch branch);

    @Query("SELECT r FROM Viaticos r where r.authorizer = :authorizer AND r.state.id = 1")
    List<Viaticos> findListByAuthorizerPendings(User authorizer);

    @Query("SELECT r FROM Viaticos r where r.state.id != 4 and r.state.id != 5")
    List<Viaticos> findAllPendingsForClose();

    @Query("SELECT r.analyst FROM Viaticos r group by r.analyst.id")
    List<User> findAnalystsActivatedAndDeactivated();

    @Query("SELECT r.treasurer FROM Viaticos r group by r.treasurer.id")
    List<User> findTreasurersActivatedAndDeactivated();

    @Query("SELECT r FROM Viaticos r where r.treasurer = :treasurer AND r.state.id < 4 AND r.branch = :branch")
    List<Viaticos> findListByTreasurerAndStateNotPaidAndBranch(User treasurer, Branch branch);

    @Query("SELECT r FROM Viaticos r where :analyst = r.analyst AND r.state.id = 5 AND r.rejectedBy = :analyst")
    Page<Viaticos> findByAnalystRejecteds(User analyst, Pageable pageable);

    @Query("SELECT r FROM Viaticos r where r.state.id = 2")
    Page<Viaticos> findByAnalystsPendings(Pageable pageable);

    @Query("SELECT r FROM Viaticos r where r.state.id = 3")
    Page<Viaticos> findByTreasurersPendings(Pageable pageable);

    @Query("SELECT r FROM Viaticos r where :analyst = r.analyst AND (r.state.id = 3 OR r.state.id = 4 OR (r.state.id = 5 and r.rejectedBy = :analyst))")
    List<Viaticos> findResolvedsByAnalyst(User analyst);

    @Query("SELECT r FROM Viaticos r where :treasurer = r.treasurer AND r.state.id = 4")
    List<Viaticos> findResolvedsByTreasurer(User treasurer);

    @Query("SELECT r FROM Viaticos r where :authorizer = r.authorizer AND r.state.id != 1")
    List<Viaticos> findResolvedsByAuthorizer(User authorizer);

    Page<Viaticos> findByTreasurerAndState(User treasurer, StatesCompras state, Pageable pageable);

    @Query("SELECT r FROM Viaticos r where :authorizer = r.authorizer AND r.state.id != 1 AND r.state.id != 5")
    Page<Viaticos> findAllNotRejectedAndAuthorizeds(User authorizer, Pageable pageable);

    @Query("SELECT r FROM Viaticos r where :analyst = r.analyst AND r.state.id != 1 AND r.state.id != 2 AND r.state.id != 5")
    Page<Viaticos> findAllNotRejectedAndLoaded(User analyst, Pageable pageable);

    Page<Viaticos> findByBranchAndState(Branch branch, StatesCompras state, Pageable pageable);

    @Query("SELECT COUNT(r) FROM Viaticos r WHERE r.analyst = :analyst and r.state.id = 2")
    int countViaticosByAnalystNotManageds(User analyst);

    @Query("SELECT COUNT(r) FROM Viaticos r WHERE r.authorizer = :authorizer and r.state.id = 1")
    int countViaticosByAuthorizerNotAuthorizeds(User authorizer);

    @Query("SELECT COUNT(r) FROM Viaticos r WHERE r.treasurer = :treasurer and r.state.id = 3")
    int countViaticosByTreasurerNotPaids(User treasurer);

    @Query("SELECT COUNT(r) FROM Viaticos r WHERE r.treasurer = :treasurer and r.state.id = 4")
    int countViaticosByTreasurerPaids(User treasurer);

    @Query("SELECT COUNT(r) FROM Viaticos r WHERE r.analyst = :analyst and r.dateLoaded != null and (r.state.id = 3 or r.state.id = 4)")
    int countViaticosByAnalystLoades(User analyst);

    @Query("SELECT COUNT(r) FROM Viaticos r WHERE r.authorizer = :authorizer and r.dateAuthorized != null")
    int countViaticosByAuthorizedsAuthorizeds(User authorizer);

    @Query("SELECT COUNT(r) FROM Viaticos r WHERE r.analyst = :analyst and r.rejectedBy = :analyst")
    int countViaticosByAnalystRejecteds(User analyst);

    @Query("SELECT COUNT(r) FROM Viaticos r WHERE r.authorizer = :authorizer and r.rejectedBy = :authorizer")
    int countViaticosByAuthorizerRejecteds(User authorizer);

    @Query("SELECT COUNT(r) FROM Viaticos r WHERE r.analyst = :analyst and (r.state.id = 2 or r.state.id = 3 or r.state.id = 4 or (r.state.id = 5 and r.rejectedBy = :analyst))")
    int countAllViaticosByAnalyst(User analyst);

    @Query("SELECT COUNT(r) FROM Viaticos r WHERE r.authorizer = :authorizer")
    int countAllViaticosByAuthorizer(User authorizer);

    @Query("SELECT COUNT(r) FROM Viaticos r WHERE r.treasurer = :treasurer and (r.state.id = 4 or r.state.id = 3)")
    int countAllViaticosByTreasurer(User treasurer);

    @Query("SELECT r FROM Viaticos r where r.colaborator.name like %:name% and r.authorizer = :authorizer order by dateInit DESC")
    List<Viaticos> findByNameColaboratorAndAuthorizer(String name, User authorizer);

    @Query("SELECT r FROM Viaticos r where r.colaborator.name like %:name% and r.analyst = :analyst order by dateInit DESC")
    List<Viaticos> findByNameColaboratorAndAnalyst(String name, User analyst);

    @Query("SELECT r FROM Viaticos r where r.colaborator.name like %:name% and r.treasurer = :treasurer order by dateInit DESC")
    List<Viaticos> findByNameColaboratorAndTreasurer(String name, User treasurer);

    @Query("SELECT r FROM Viaticos r where r.ref like %:referencia%")
    List<Viaticos> findByRef(String referencia);

    @Query("SELECT r FROM Viaticos r where r.branch = :branch")
    List<Viaticos> findByBranch(Branch branch);

    @Query("SELECT COUNT(r) FROM Viaticos r WHERE r.state.id = 1")
    int countAllPendingsForAuthorize();

    @Query("SELECT COUNT(r) FROM Viaticos r WHERE r.state.id = 2")
    int countAllPendingsForCharge();

    @Query("SELECT COUNT(r) FROM Viaticos r WHERE r.state.id = 3")
    int countAllPendingsForPaid();

    @Query("SELECT COUNT(r) FROM Viaticos r WHERE r.state.id = 1 and r.branch = :branch")
    int countAllPendingsForAuthorizeForBranch(Branch branch);

    @Query("SELECT COUNT(r) FROM Viaticos r WHERE r.state.id = 2 and r.branch = :branch")
    int countAllPendingsForChargeForBranch(Branch branch);

    @Query("SELECT COUNT(r) FROM Viaticos r WHERE r.state.id = 3 and r.branch = :branch")
    int countAllPendingsForPaidForBranch(Branch branch);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.treasurer = :treasurer and r.state.id = 3")
    Long sumNotPaidsForTreasurer(User treasurer);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.treasurer = :treasurer and r.state.id = 4")
    Long sumPaidsForTreasurer(User treasurer);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.treasurer = :treasurer and (r.state.id = 3 or r.state.id = 4)")
    Long sumaAmountForTreasurer(User treasurer);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.branch = :branch and r.state.id = 4")
    Long sumaAmountForBranchPaid(Branch branch);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.branch = :branch and r.state.id = 3")
    Long sumaAmountForBranchNotPaid(Branch branch);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.branch = :branch and (r.state.id = 3 or r.state.id = 4)")
    Long sumaAmountTotalForBranch(Branch branch);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.concept = :concept and (r.state.id = 3 or r.state.id = 4)")
    Long sumaAmountForConcept(ConceptsCompras concept);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.concept = :concept and r.branch = :branch and month(r.dateLoaded) = :month and year(r.dateLoaded) = :year")
    Long sumaAmountConceptForBranchAndTime(ConceptsCompras concept, Branch branch, int month, int year);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.concept = :concept and r.branch = :branch and year(r.dateLoaded) = :year")
    Long sumaAmountConceptForBranchYear(ConceptsCompras concept, Branch branch, int year);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.concept = :concept and r.branch = :branch and (r.state.id = 3 or r.state.id = 4)")
    Long sumAllAmountConceptForBranch(ConceptsCompras concept, Branch branch);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.concept = :concept and r.branch.brandsCompany.company = :company and month(r.dateLoaded) = :month and year(r.dateLoaded) = :year")
    Long sumaAmountConceptForCompanyAndTime(ConceptsCompras concept, Company company, int month, int year);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.concept = :concept and r.branch.brandsCompany.company = :company and year(r.dateLoaded) = :year")
    Long sumaAmountConceptForCompanyYear(ConceptsCompras concept, Company company, int year);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.concept = :concept and r.branch.brandsCompany.company = :company and (r.state.id = 3 or r.state.id = 4)")
    Long sumAllAmountConceptForCompany(ConceptsCompras concept, Company company);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.concept = :concept and r.colaborator = :colaborator and month(r.dateLoaded) = :month and year(r.dateLoaded) = :year")
    Long sumaAmountConceptForColaboratorAndTime(ConceptsCompras concept, User colaborator, int month, int year);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.concept = :concept and r.colaborator = :colaborator and year(r.dateLoaded) = :year")
    Long sumaAmountConceptForColaboratorYear(ConceptsCompras concept, User colaborator, int year);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.concept = :concept and r.colaborator = :colaborator and (r.state.id = 3 or r.state.id = 4)")
    Long sumaAllAmountConceptForColaborator(ConceptsCompras concept, User colaborator);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.branch = :branch and r.state.id = 4 and month(r.dateLoaded) = :month and year(r.dateLoaded) = :year")
    Long sumPaidBranch(Branch branch, int month, int year);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.branch = :branch and r.state.id = 4 and year(r.dateLoaded) = :year")
    Long sumPaidBranchYear(Branch branch, int year);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.branch = :branch and r.state.id = 4")
    Long sumAllPaidBranch(Branch branch);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.branch.brandsCompany.company = :company and r.state.id = 4 and month(r.dateLoaded) = :month and year(r.dateLoaded) = :year")
    Long sumPaidCompany(Company company, int month, int year);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.branch.brandsCompany.company = :company and r.state.id = 4 and year(r.dateLoaded) = :year")
    Long sumPaidCompanyYear(Company company, int year);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.branch.brandsCompany.company = :company and r.state.id = 4")
    Long sumAllPaidCompany(Company company);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.colaborator = :colaborator and r.state.id = 4 and month(r.dateLoaded) = :month and year(r.dateLoaded) = :year")
    Long sumPaidColaborator(User colaborator, int month, int year);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.colaborator = :colaborator and r.state.id = 4 and year(r.dateLoaded) = :year")
    Long sumPaidColaboratorYear(User colaborator, int year);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.colaborator = :colaborator and r.state.id = 4")
    Long sumAllPaidColaborator(User colaborator);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.branch.brandsCompany.company = :company and r.state.id = 3 and month(r.dateLoaded) = :month and year(r.dateLoaded) = :year")
    Long sumNotPaidCompany(Company company, int month, int year);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.branch.brandsCompany.company = :company and r.state.id = 3 and year(r.dateLoaded) = :year")
    Long sumNotPaidCompanyYear(Company company, int year);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.branch.brandsCompany.company = :company and r.state.id = 3")
    Long sumAllNotPaidCompany(Company company);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.colaborator = :colaborator and r.state.id = 3 and month(r.dateLoaded) = :month and year(r.dateLoaded) = :year")
    Long sumNotPaidColaborator(User colaborator, int month, int year);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.colaborator = :colaborator and r.state.id = 3 and year(r.dateLoaded) = :year")
    Long sumNotPaidColaboratorYear(User colaborator, int year);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.colaborator = :colaborator and r.state.id = 3")
    Long sumAllNotPaidColaborator(User colaborator);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.colaborator = :colaborator and r.state >= 2 and month(r.dateInit) = :month and year(r.dateInit) = :year")
    Long sumAmountMonthlyForColaborator(User colaborator, int month, int year);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.branch = :branch and r.state.id = 3 and month(r.dateLoaded) = :month and year(r.dateLoaded) = :year")
    Long sumNotPaidBranch(Branch branch, int month, int year);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.branch = :branch and r.state.id = 3 and year(r.dateLoaded) = :year")
    Long sumNotPaidBranchYear(Branch branch, int year);

    @Query("SELECT SUM(r.amount) FROM Viaticos r WHERE r.branch = :branch and r.state.id = 3")
    Long sumAllNotPaidBranch(Branch branch);

    @Query("SELECT DISTINCT new com.opencars.netgo.sales.viaticos.dto.ReportColaborators(v.colaborator, SUM(v.amount)) FROM Viaticos v INNER JOIN User u ON v.colaborator.id = u.id WHERE v.state.id = 3 OR v.state.id = 4 GROUP BY v.colaborator.id ORDER BY SUM(v.amount) DESC")
    List<ReportColaborators> findColaboratorsReport();

    @Query("SELECT DISTINCT new com.opencars.netgo.sales.viaticos.dto.ReportColaborators(v.colaborator, SUM(CASE WHEN (TIMESTAMPDIFF(DAY, v.dateLoaded, :dateCurrent) BETWEEN :initDays AND :endDays) THEN v.amount ELSE 0 END)) FROM Viaticos v" +
            " INNER JOIN User u ON v.colaborator.id = u.id" +
            " WHERE v.state.id = 3 OR v.state.id = 4" +
            " GROUP BY v.colaborator.id")
    List<ReportColaborators> findColaboratorsAmountsByRangeDays(LocalDate dateCurrent, int initDays, int endDays);

    @Query("SELECT DISTINCT new com.opencars.netgo.sales.viaticos.dto.ReportColaborators(v.colaborator, SUM(CASE WHEN (TIMESTAMPDIFF(DAY, v.dateLoaded, :dateCurrent) > 90) THEN v.amount ELSE 0 END)) FROM Viaticos v" +
            " INNER JOIN User u ON v.colaborator.id = u.id" +
            " WHERE v.state.id = 3 OR v.state.id = 4" +
            " GROUP BY v.colaborator.id")
    List<ReportColaborators> findColaboratorsMin90Days(LocalDate dateCurrent);

}
