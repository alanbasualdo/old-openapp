package com.opencars.netgo.sales.proveedores.repository;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.sales.proveedores.dto.ReportProviders;
import com.opencars.netgo.sales.proveedores.dto.SalesFilter;
import com.opencars.netgo.sales.proveedores.dto.SalesToExport;
import com.opencars.netgo.sales.proveedores.entity.SalesProviders;
import com.opencars.netgo.sales.proveedores.entity.StatesProveedores;
import com.opencars.netgo.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalesProvidersRepository extends JpaRepository<SalesProviders, Long> {

    Page<SalesProviders> findByColaborator(User colaborator, Pageable pageable);

    @Query("SELECT r FROM SalesProviders r where r.provider.id = :provider AND r.colaborator.id = :colaborator")
    Page<SalesProviders> findByProviderAndColaborator(long provider, int colaborator, Pageable pageable);

    @Query("SELECT r FROM SalesProviders r where r.branch.id = :branch AND r.colaborator.id = :colaborator")
    Page<SalesProviders> findByBranchAndColaborator(int branch, int colaborator, Pageable pageable);

    @Query("SELECT r FROM SalesProviders r where r.provider.id = :provider")
    Page<SalesProviders> findByProvider(long provider, Pageable pageable);

    @Query("SELECT r FROM SalesProviders r where r.branch.id = :branch")
    Page<SalesProviders> findByBranch(int branch, Pageable pageable);

    @Query("SELECT r FROM SalesProviders r where r.provider.id = :provider AND (r.authorizerSector.id = :authorizer OR r.authorizerAccount.id = :authorizer OR r.authorizerMaxAmount.id = :authorizer)")
    Page<SalesProviders> findByProviderAndAuthorizer(long provider, int authorizer, Pageable pageable);

    @Query("SELECT r FROM SalesProviders r where r.branch.id = :branch AND (r.authorizerSector.id = :authorizer OR r.authorizerAccount.id = :authorizer OR r.authorizerMaxAmount.id = :authorizer)")
    Page<SalesProviders> findByBranchAndAuthorizer(int branch, int authorizer, Pageable pageable);

    @Query("SELECT r FROM SalesProviders r where r.provider.id = :provider AND r.analyst.id = :analyst")
    Page<SalesProviders> findByProviderAndAnalyst(long provider, int analyst, Pageable pageable);

    @Query("SELECT r FROM SalesProviders r where r.branch.id = :branch AND r.analyst.id = :analyst")
    Page<SalesProviders> findByBranchAndAnalyst(int branch, int analyst, Pageable pageable);

    @Query("SELECT r FROM SalesProviders r where r.provider.id = :provider AND r.treasurer.id = :treasurer")
    Page<SalesProviders> findByProviderAndTreasurer(long provider, int treasurer, Pageable pageable);

    @Query("SELECT r FROM SalesProviders r where r.branch.id = :branch AND r.treasurer.id = :treasurer")
    Page<SalesProviders> findByBranchAndTreasurer(int branch, int treasurer, Pageable pageable);

    Page<SalesProviders> findAll(Specification<SalesProviders> spec, Pageable pageable);

    @Query("SELECT r FROM SalesProviders r where r.treasurer = :treasurer AND r.branch = :branch AND r.state.id < 6")
    List<SalesProviders> findPendingsForTreasurers(User treasurer, Branch branch);

    @Query("SELECT r FROM SalesProviders r where r.analyst = :analyst AND r.state.id < 4 AND r.branch = :branch")
    List<SalesProviders> findPendingsForAnalyst(User analyst, Branch branch);

    @Query("SELECT r.authorizerSector FROM SalesProviders r group by r.authorizerSector.id")
    List<User> findAuthorizersSector();

    @Query("SELECT r.authorizerAccount FROM SalesProviders r group by r.authorizerAccount.id")
    List<User> findAuthorizersAccount();

    @Query("SELECT r.authorizerMaxAmount FROM SalesProviders r group by r.authorizerMaxAmount.id")
    List<User> findAuthorizersMaxAmount();

    @Query("SELECT COUNT(r) FROM SalesProviders r WHERE r.state.id > 1 AND (r.authorizerSector = :authorizer) or (r.authorizerAccount = :authorizer) or (r.authorizerMaxAmount = :authorizer)")
    int countAllSalesByAuthorizer(User authorizer);

    @Query("SELECT COUNT(r) FROM SalesProviders r WHERE r.state.id > 1 AND (r.authorizerSector = :authorizer and r.dateAuthorizedSector = null) or (r.authorizerAccount = :authorizer and r.dateAuthorizedAccount = null) or (r.authorizerMaxAmount = :authorizer and r.dateAuthorizedMaxAmount = null)")
    int countSalesByAuthorizerNotAuthorizeds(User authorizer);

    @Query("SELECT COUNT(r) FROM SalesProviders r WHERE r.state.id > 1 AND (r.authorizerSector = :authorizer and r.dateAuthorizedSector != null) or (r.authorizerAccount = :authorizer and r.dateAuthorizedAccount != null) or (r.authorizerMaxAmount = :authorizer and r.dateAuthorizedMaxAmount != null)")
    int countSalesByAuthorizedsAuthorizeds(User authorizer);

    @Query("SELECT COUNT(r) FROM SalesProviders r WHERE r.rejectedBy = :authorizer")
    int countSalesByAuthorizerRejecteds(User authorizer);

    @Query("SELECT r FROM SalesProviders r where ((r.authorizerSector = :authorizer and r.dateAuthorizedSector != null) or (r.authorizerAccount = :authorizer and r.dateAuthorizedAccount != null) or (r.authorizerMaxAmount = :authorizer and r.dateAuthorizedMaxAmount != null)) AND r.state.id > 1")
    List<SalesProviders> findResolvedsByAuthorizer(User authorizer);

    @Query("SELECT r FROM SalesProviders r where ((r.authorizerSector = :colaborator AND r.dateAuthorizedSector = null AND r.state.id = 1) OR (r.authorizerAccount = :colaborator AND r.dateAuthorizedAccount = null AND r.state.id = 1) OR (r.authorizerMaxAmount = :colaborator AND r.dateAuthorizedMaxAmount = null AND r.state.id = 2)) AND r.state.id != 7")
    Page<SalesProviders> findByAuthorizerPendings(User colaborator, Pageable pageable);

    @Query("SELECT r FROM SalesProviders r where ((r.authorizerSector = :colaborator AND r.dateAuthorizedSector = null AND r.state.id = 1) OR (r.authorizerAccount = :colaborator AND r.dateAuthorizedAccount = null AND r.state.id = 1) OR (r.authorizerMaxAmount = :colaborator AND r.dateAuthorizedMaxAmount = null AND r.state.id = 2)) AND r.state.id != 7")
    List<SalesProviders> findListByAuthorizerPendings(User colaborator);

    @Query("SELECT r FROM SalesProviders r where r.state.id = 7 AND r.rejectedBy = :colaborator")
    Page<SalesProviders> findByRejectedBy(User colaborator, Pageable pageable);

    @Query("SELECT r FROM SalesProviders r where :analyst = r.analyst AND (r.state.id = 4 or r.state.id = 5 or r.state.id = 6)")
    Page<SalesProviders> findAllNotRejectedAndLoaded(User analyst, Pageable pageable);

    @Query("SELECT r FROM SalesProviders r where :analyst = r.analyst AND ((r.state.id = 2 and r.authorizerMaxAmount = null) or r.state.id = 3)")
    Page<SalesProviders> findUnloadsByAnalyst(User analyst, Pageable pageable);

    @Query("SELECT r FROM SalesProviders r where r.state.id = 4 AND r.paidType = 'Pactado'")
    Page<SalesProviders> findAllDataPaidUnauthorizeds(Pageable pageable);

    @Query("SELECT r FROM SalesProviders r where r.state.id >=5 AND r.paidType = 'Pactado' AND r.state.id != 7")
    Page<SalesProviders> findAllDataPaidAuthorizeds(Pageable pageable);

    @Query("SELECT r FROM SalesProviders r where r.rejectionPaidMethod = 1")
    Page<SalesProviders> findAllDataPaidReprogramed(Pageable pageable);

    @Query("SELECT r.analyst FROM SalesProviders r group by r.analyst.id")
    List<User> findAnalystsActivatedAndDeactivated();

    @Query("SELECT r.treasurer FROM SalesProviders r group by r.treasurer.id")
    List<User> findTreasurersActivatedAndDeactivated();

    @Query("SELECT r FROM SalesProviders r where :treasurer = r.treasurer AND r.state.id = 6")
    List<SalesProviders> findResolvedsByTreasurer(User treasurer);

    @Query("SELECT new com.opencars.netgo.sales.proveedores.dto.SalesToExport(r.id, r.amount, s.state, c.name, r.dateInit, t.name, r.dateEmision, r.dateAgreed, p.nombre) FROM SalesProviders r INNER JOIN User c ON r.colaborator.id = c.id INNER JOIN StatesProveedores s ON r.state.id = s.id INNER JOIN User t ON r.treasurer.id = t.id INNER JOIN ProvidersSales p ON r.provider.id = p.id where r.state.id != 6 and r.state.id != 7")
    List<SalesToExport> findAllPendingsForClose();

    @Query("SELECT new com.opencars.netgo.sales.proveedores.dto.SalesToExport(r.id, r.amount, s.state, c.name, r.dateInit, t.name, r.dateEmision, r.dateAgreed, p.nombre) FROM SalesProviders r INNER JOIN User c ON r.colaborator.id = c.id INNER JOIN StatesProveedores s ON r.state.id = s.id INNER JOIN User t ON r.treasurer.id = t.id INNER JOIN ProvidersSales p ON r.provider.id = p.id where r.state.id = 6")
    List<SalesToExport> findAllClosedSales();

    @Query("SELECT COUNT(r) FROM SalesProviders r WHERE r.treasurer = :treasurer and (r.state.id = 6 or r.state.id = 5)")
    int countAllSalesByTreasurer(User treasurer);

    @Query("SELECT SUM(r.amount) FROM SalesProviders r WHERE r.treasurer = :treasurer and (r.state.id = 5 or r.state.id = 6)")
    Long sumaAmountForTreasurer(User treasurer);

    @Query("SELECT SUM(r.amount) FROM SalesProviders r WHERE r.treasurer = :treasurer and r.state.id = 6")
    Long sumPaidsForTreasurer(User treasurer);

    @Query("SELECT SUM(r.amount) FROM SalesProviders r WHERE r.treasurer = :treasurer and r.state.id = 5 and r.dateAgreed > :date")
    Long sumNotPaidsNotDefeatedForTreasurer(User treasurer, LocalDate date);

    @Query("SELECT SUM(r.amount) FROM SalesProviders r WHERE r.treasurer = :treasurer and r.state.id = 5 and r.dateAgreed <= :date")
    Long sumNotPaidsDefeatedForTreasurer(User treasurer, LocalDate date);

    @Query("SELECT COUNT(r) FROM SalesProviders r WHERE r.treasurer = :treasurer and r.state.id = 6")
    int countSalesByTreasurerPaids(User treasurer);

    @Query("SELECT COUNT(r) FROM SalesProviders r WHERE r.treasurer = :treasurer and r.state.id = 5 and r.dateAgreed <= :date")
    int countSalesByTreasurerNotPaidsDefeated(User treasurer, LocalDate date);

    @Query("SELECT COUNT(r) FROM SalesProviders r WHERE r.treasurer = :treasurer and r.state.id = 5 and r.dateAgreed > :date")
    int countSalesByTreasurerNotPaidsNotDefeated(User treasurer, LocalDate date);

    @Query("SELECT r FROM SalesProviders r where r.state.id = 5")
    Page<SalesProviders> findByTreasurersPendings(Pageable pageable);

    @Query("SELECT r FROM SalesProviders r where :analyst = r.analyst AND (r.state.id = 6 OR (r.state.id = 7 and r.rejectedBy = :analyst))")
    List<SalesProviders> findResolvedsByAnalyst(User analyst);

    @Query("SELECT COUNT(r) FROM SalesProviders r WHERE r.analyst = :analyst and ((r.state.id = 2 and r.authorizerMaxAmount = null) or (r.state.id = 3 and r.dateAuthorizedMaxAmount != null) or (r.state.id = 7 and r.rejectedBy = :analyst))")
    int countAllSalesByAnalyst(User analyst);

    @Query("SELECT COUNT(r) FROM SalesProviders r WHERE r.analyst = :analyst and ((r.state.id = 2 and r.authorizerMaxAmount = null) or (r.state.id = 3 and r.dateAuthorizedMaxAmount != null))")
    int countSalesByAnalystNotManageds(User analyst);

    @Query("SELECT COUNT(r) FROM SalesProviders r WHERE r.analyst = :analyst and r.dateLoaded != null and (r.state.id = 4 or r.state.id = 5 or r.state.id = 6)")
    int countSalesByAnalystLoades(User analyst);

    @Query("SELECT COUNT(r) FROM SalesProviders r WHERE r.analyst = :analyst and r.rejectedBy = :analyst")
    int countSalesByAnalystRejecteds(User analyst);

    Page<SalesProviders> findByTreasurerAndState(User treasurer, StatesProveedores state, Pageable pageable);

    @Query("SELECT new com.opencars.netgo.sales.proveedores.dto.ReportProviders(u.name, u.id, c.subConcept, c.id, COUNT(t.id), SUM(t.amount), SUM(t.rejection), (COUNT(t.id) - SUM(t.rejection))) FROM SalesProviders t INNER JOIN User u ON t.colaborator.id = u.id INNER JOIN SubcateorizedConcepts c ON t.concept.id = c.id GROUP BY u.id, u.name, c.subConcept ORDER BY u.id, c.subConcept")
    List<ReportProviders> findProvidersReport();

    @Query("SELECT new com.opencars.netgo.sales.proveedores.dto.ReportProviders(u.name, u.id, c.subConcept, c.id, COUNT(t.id), SUM(t.amount), SUM(t.rejection), (COUNT(t.id) - SUM(t.rejection))) FROM SalesProviders t INNER JOIN User u ON t.colaborator.id = u.id INNER JOIN SubcateorizedConcepts c ON t.concept.id = c.id INNER JOIN ProvidersSales p ON t.provider.id = p.id WHERE t.provider.id = :provider GROUP BY u.id, u.name, c.subConcept ORDER BY u.id, c.subConcept")
    List<ReportProviders> findProvidersReportByProvider(long provider);

    @Query("SELECT new com.opencars.netgo.sales.proveedores.dto.ReportProviders(u.name, u.id, c.subConcept, c.id, COUNT(t.id), SUM(t.amount), SUM(t.rejection), (COUNT(t.id) - SUM(t.rejection))) FROM SalesProviders t INNER JOIN User u ON t.colaborator.id = u.id INNER JOIN SubcateorizedConcepts c ON t.concept.id = c.id WHERE t.dateInit BETWEEN :dateInit AND :dateEnd GROUP BY u.id, u.name, c.subConcept ORDER BY u.id, c.subConcept")
    List<ReportProviders> findProvidersReportByDateRange(LocalDateTime dateInit, LocalDateTime dateEnd);

    @Query("SELECT new com.opencars.netgo.sales.proveedores.dto.ReportProviders(u.name, u.id, c.subConcept, c.id, COUNT(t.id), SUM(t.amount), SUM(t.rejection), (COUNT(t.id) - SUM(t.rejection))) FROM SalesProviders t INNER JOIN User u ON t.colaborator.id = u.id INNER JOIN SubcateorizedConcepts c ON t.concept.id = c.id WHERE t.dateInit BETWEEN :dateInit AND :dateEnd AND t.provider.id = :provider GROUP BY u.id, u.name, c.subConcept ORDER BY u.id, c.subConcept")
    List<ReportProviders> findProvidersReportByDateRangeAndProvider(LocalDateTime dateInit, LocalDateTime dateEnd, long provider);

    @Query("SELECT new com.opencars.netgo.sales.proveedores.dto.ReportProviders(u.name, u.id, c.subConcept, c.id, COUNT(t.id), SUM(t.amount), SUM(t.rejection), (COUNT(t.id) - SUM(t.rejection))) FROM SalesProviders t INNER JOIN User u ON t.colaborator.id = u.id INNER JOIN SubcateorizedConcepts c ON t.concept.id = c.id WHERE t.amount = :amount GROUP BY u.id, u.name, c.subConcept ORDER BY u.id, c.subConcept")
    List<ReportProviders> findByCoincidenceInAmount(double amount);

    @Query("SELECT new com.opencars.netgo.sales.proveedores.dto.ReportProviders(u.name, u.id, c.subConcept, c.id, COUNT(t.id), SUM(t.amount), SUM(t.rejection), (COUNT(t.id) - SUM(t.rejection))) FROM SalesProviders t INNER JOIN User u ON t.colaborator.id = u.id INNER JOIN SubcateorizedConcepts c ON t.concept.id = c.id WHERE t.amount = :amount and t.provider.id = :provider GROUP BY u.id, u.name, c.subConcept ORDER BY u.id, c.subConcept")
    List<ReportProviders> findByCoincidenceInAmountAndProvider(double amount, long provider);

    @Query("SELECT new com.opencars.netgo.sales.proveedores.dto.SalesFilter(r.id, r.dateEmision, r.dateInit, r.dateAgreed, r.provider.nombre, r.amount, r.state) FROM SalesProviders r INNER JOIN SubcateorizedConcepts c on r.concept.id = c.id WHERE r.colaborator.id = :colaborator and c.id = :concept order by r.dateInit DESC")
    List<SalesFilter> findByColaboratorAndConcept(int colaborator, int concept);

    @Query("SELECT new com.opencars.netgo.sales.proveedores.dto.SalesFilter(r.id, r.dateEmision, r.dateInit, r.dateAgreed, r.provider.nombre, r.amount, r.state) FROM SalesProviders r INNER JOIN SubcateorizedConcepts c on r.concept.id = c.id WHERE r.colaborator.id = :colaborator and c.id = :concept and r.provider.id = :provider order by r.dateInit DESC")
    List<SalesFilter> findByColaboratorAndConceptAndProvider(int colaborator, int concept, long provider);

    @Query("SELECT new com.opencars.netgo.sales.proveedores.dto.SalesFilter(r.id, r.dateEmision, r.dateInit, r.dateAgreed, r.provider.nombre, r.amount, r.state) FROM SalesProviders r INNER JOIN SubcateorizedConcepts c on r.concept.id = c.id WHERE r.colaborator.id = :colaborator and c.id = :concept and r.dateInit BETWEEN :dateInit AND :dateEnd order by r.dateInit DESC")
    List<SalesFilter> findByColaboratorAndConceptByDateRange(int colaborator, int concept, LocalDateTime dateInit, LocalDateTime dateEnd);

    @Query("SELECT new com.opencars.netgo.sales.proveedores.dto.SalesFilter(r.id, r.dateEmision, r.dateInit, r.dateAgreed, r.provider.nombre, r.amount, r.state) FROM SalesProviders r INNER JOIN SubcateorizedConcepts c on r.concept.id = c.id WHERE r.colaborator.id = :colaborator and c.id = :concept and r.provider.id = :provider and r.dateInit BETWEEN :dateInit AND :dateEnd order by r.dateInit DESC")
    List<SalesFilter> findByColaboratorAndConceptAndProviderByDateRange(int colaborator, int concept, LocalDateTime dateInit, LocalDateTime dateEnd, long provider);

    @Query("SELECT new com.opencars.netgo.sales.proveedores.dto.SalesFilter(r.id, r.dateEmision, r.dateInit, r.dateAgreed, r.provider.nombre, r.amount, r.state) FROM SalesProviders r INNER JOIN SubcateorizedConcepts c on r.concept.id = c.id WHERE r.colaborator.id = :colaborator and c.id = :concept and r.amount = :amount order by r.dateInit DESC")
    List<SalesFilter> findByColaboratorAndConceptAndAmount(int colaborator, int concept, double amount);

    @Query("SELECT new com.opencars.netgo.sales.proveedores.dto.SalesFilter(r.id, r.dateEmision, r.dateInit, r.dateAgreed, r.provider.nombre, r.amount, r.state) FROM SalesProviders r INNER JOIN SubcateorizedConcepts c on r.concept.id = c.id WHERE r.colaborator.id = :colaborator and c.id = :concept and r.amount = :amount and r.provider.id = :provider order by r.dateInit DESC")
    List<SalesFilter> findByColaboratorAndConceptAndAmountAndProvider(int colaborator, int concept, double amount, long provider);

}
