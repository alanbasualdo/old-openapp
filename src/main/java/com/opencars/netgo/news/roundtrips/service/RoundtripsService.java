package com.opencars.netgo.news.roundtrips.service;

import com.opencars.netgo.locations.entity.Company;
import com.opencars.netgo.locations.entity.Sector;
import com.opencars.netgo.locations.entity.SubSector;
import com.opencars.netgo.locations.repository.CompanyRepository;
import com.opencars.netgo.locations.repository.SectorRepository;
import com.opencars.netgo.locations.repository.SubSectorRepository;
import com.opencars.netgo.news.roundtrips.dto.RoundtripsList;
import com.opencars.netgo.news.roundtrips.dto.RoundtripsSummary;
import com.opencars.netgo.news.roundtrips.entity.Roundtrips;
import com.opencars.netgo.news.roundtrips.repository.RoundtripsRepository;
import com.opencars.netgo.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class RoundtripsService {

    @Autowired
    RoundtripsRepository roundtripsRepository;

    @Autowired
    SectorRepository sectorRepository;

    @Autowired
    SubSectorRepository subSectorRepository;

    @Autowired
    CompanyRepository companyRepository;

    public Optional<Roundtrips> getOne(int id){
        return roundtripsRepository.getById(id);
    }

    public boolean existsBytitle(String title){

        return roundtripsRepository.existsByTitle(title);
    }

    public void deleteById(int id){
        roundtripsRepository.deleteById(id);
    }

    public Optional<Roundtrips> getByTitle(String title){
        return roundtripsRepository.findByTitle(title);
    }

    public List<RoundtripsList> getByTypeAndPublishedState(int type, int published){

        Sector sector = sectorRepository.getById(type);

        List<RoundtripsList> list = roundtripsRepository.findListByTypeAndPublishedState(sector, published);

        return list;

    }

    public List<RoundtripsList> getBySubsectorAndCompanyAndPublishedState(int subsector, int companyId, int published){

        SubSector subSector = subSectorRepository.getById(subsector);

        Company company = companyRepository.getById(companyId);

        List<RoundtripsList> list = roundtripsRepository.findListBySubsectorAndCompanyAndPublishedState(subSector, company, published);

        return list;

    }

    public List<RoundtripsList> getAllPendings(){

        List<RoundtripsList> list = roundtripsRepository.findAllPendings();

        return list;

    }

    public List<RoundtripsList> getByCreator(int creator){

        List<RoundtripsList> list = roundtripsRepository.findByCreator(creator);

        return list;

    }

    public List<RoundtripsList> getAllByMonthAndYear(int month, int year){

        List<RoundtripsList> list = roundtripsRepository.findAllByMonthAndYear(month, year);

        return list;

    }

    public void save(Roundtrips roundtrips){
        roundtripsRepository.save(roundtrips);
    }

    public boolean existsById(int id){
        return roundtripsRepository.existsById(id);
    }

    public List<Roundtrips> getById(int id){
        return roundtripsRepository.findById(id);
    }

    public List<RoundtripsSummary> getRoundtripsSummary(){

        LocalDateTime currentDate = LocalDateTime.now();

        List<RoundtripsSummary> list = roundtripsRepository.findListBetweenDates(currentDate.minusDays(7), currentDate);

        return list;

    }

    public int countRoundtripsNotPublishedByArea(Sector area){

        int count = roundtripsRepository.countRoundtripsNotPublishedByArea(area);

        return count;

    }

    public int countAllRoundtripsNotPublished(){

        int count = roundtripsRepository.countAllRoundtripsNotPublished();

        return count;

    }

    public int countRoundtripsByDate(){

        LocalDate currentDate = LocalDate.now();

        int count = roundtripsRepository.countRoundtripsByDate(currentDate);

        return count;

    }

    public int countRoundtripsByDateAndSector(Sector sector){

        LocalDate currentDate = LocalDate.now();

        int count = roundtripsRepository.countRoundtripsByDateAndSector(currentDate, sector);

        return count;

    }

    public int countRoundtripsByDateAndSubSector(SubSector subSector){

        LocalDate currentDate = LocalDate.now();

        int count = roundtripsRepository.countRoundtripsByDateAndSubSector(currentDate, subSector);

        return count;

    }

    public int countRoundtripsByDateAndSubSectorAndCompany(SubSector subSector, Company company){

        LocalDate currentDate = LocalDate.now();

        int count = roundtripsRepository.countRoundtripsByDateAndSubSectorAndCompany(currentDate, subSector, company);

        return count;

    }

    public List<RoundtripsList> getByCoincidenceInTitle(String title){
        return roundtripsRepository.findRoundtripByCoincidence(title);
    }

}
