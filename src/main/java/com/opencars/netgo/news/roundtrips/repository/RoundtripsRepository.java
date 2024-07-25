package com.opencars.netgo.news.roundtrips.repository;

import com.opencars.netgo.locations.entity.Company;
import com.opencars.netgo.locations.entity.Sector;
import com.opencars.netgo.locations.entity.SubSector;
import com.opencars.netgo.news.roundtrips.dto.RoundtripsList;
import com.opencars.netgo.news.roundtrips.dto.RoundtripsSummary;
import com.opencars.netgo.news.roundtrips.entity.Roundtrips;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoundtripsRepository extends JpaRepository<Roundtrips, Integer> {

    Optional<Roundtrips> findByTitle(String title);

    boolean existsByTitle(String title);

    List<Roundtrips> findById(int id);
    @Query("SELECT r FROM Roundtrips r where r.id = :id")
    Optional<Roundtrips> getById(int id);

    @Query("SELECT new com.opencars.netgo.news.roundtrips.dto.RoundtripsSummary(r.id, r.title, r.date, r.audience, r.company, r.type, r.subSector) FROM Roundtrips r where r.date BETWEEN :init AND :end and r.published = 1 ORDER BY r.date DESC")
    List<RoundtripsSummary> findListBetweenDates(LocalDateTime init, LocalDateTime end);

    @Query("SELECT new com.opencars.netgo.news.roundtrips.dto.RoundtripsList (r.id, r.title, r.date, r.audience) FROM Roundtrips r where r.type = :type and r.published = :published ORDER BY r.date DESC")
    List<RoundtripsList> findListByTypeAndPublishedState(Sector type, int published);

    @Query("SELECT new com.opencars.netgo.news.roundtrips.dto.RoundtripsList (r.id, r.title, r.date, r.audience) FROM Roundtrips r where r.subSector = :subSector and r.company = :company and r.published = :published ORDER BY r.date DESC")
    List<RoundtripsList> findListBySubsectorAndCompanyAndPublishedState(SubSector subSector, Company company, int published);

    @Query("SELECT new com.opencars.netgo.news.roundtrips.dto.RoundtripsList (r.id, r.title, r.date, r.audience) FROM Roundtrips r where r.published = 0 ORDER BY r.date DESC")
    List<RoundtripsList> findAllPendings();

    @Query("SELECT new com.opencars.netgo.news.roundtrips.dto.RoundtripsList (r.id, r.title, r.date, r.audience) FROM Roundtrips r where (r.createdBy IS NOT NULL and r.createdBy.id = :creator) ORDER BY r.date DESC")
    List<RoundtripsList> findByCreator(int creator);

    @Query("SELECT new com.opencars.netgo.news.roundtrips.dto.RoundtripsList (r.id, r.title, r.date, r.audience) FROM Roundtrips r where r.title like %:title%")
    List<RoundtripsList> findRoundtripByCoincidence(String title);

    @Query("SELECT new com.opencars.netgo.news.roundtrips.dto.RoundtripsList (r.id, r.title, r.date, r.audience) FROM Roundtrips r where month(r.shortDate) = :month AND year(r.shortDate) = :year AND r.published = 1 ORDER BY r.date DESC")
    List<RoundtripsList> findAllByMonthAndYear(int month, int year);

    @Query("SELECT COUNT(r) FROM Roundtrips r WHERE r.type = :area and r.published = 0")
    int countRoundtripsNotPublishedByArea(Sector area);

    @Query("SELECT COUNT(r) FROM Roundtrips r WHERE r.published = 0")
    int countAllRoundtripsNotPublished();

    @Query("SELECT COUNT(r) FROM Roundtrips r where r.shortDate = :date and r.published = 1")
    int countRoundtripsByDate(LocalDate date);

    @Query("SELECT COUNT(r) FROM Roundtrips r where r.shortDate = :date and r.type = :sector and r.published = 1")
    int countRoundtripsByDateAndSector(LocalDate date, Sector sector);

    @Query("SELECT COUNT(r) FROM Roundtrips r where r.shortDate = :date and r.subSector = :subSector and r.published = 1")
    int countRoundtripsByDateAndSubSector(LocalDate date, SubSector subSector);

    @Query("SELECT COUNT(r) FROM Roundtrips r where r.shortDate = :date and r.subSector = :subSector and r.company = :company and r.published = 1")
    int countRoundtripsByDateAndSubSectorAndCompany(LocalDate date, SubSector subSector, Company company);



}
