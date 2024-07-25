package com.opencars.netgo.news.general.controller;

import com.opencars.netgo.dms.calidad.service.ReportCalidadService;
import com.opencars.netgo.locations.entity.Company;
import com.opencars.netgo.locations.entity.Sector;
import com.opencars.netgo.locations.entity.SubSector;
import com.opencars.netgo.locations.service.CompanyService;
import com.opencars.netgo.locations.service.SectorService;
import com.opencars.netgo.locations.service.SubSectorService;
import com.opencars.netgo.news.general.dto.NewsCounts;
import com.opencars.netgo.news.general.dto.RoundtripsCount;
import com.opencars.netgo.news.general.dto.Summary;
import com.opencars.netgo.news.goodbyes.controller.GoodbyesController;
import com.opencars.netgo.news.letters.controller.LettersController;
import com.opencars.netgo.news.maximuns.controller.MaximunsController;
import com.opencars.netgo.news.recognitions.controller.RecognitionsController;
import com.opencars.netgo.news.roundtrips.controller.RoundtripsController;
import com.opencars.netgo.news.searchs.controller.SearchsController;
import com.opencars.netgo.news.welcomes.controller.WelcomeController;
import com.opencars.netgo.users.controller.UsersController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador General de Noticias")
public class GeneralController {

    @Autowired
    WelcomeController welcomeController;

    @Autowired
    RoundtripsController roundtripsController;

    @Autowired
    LettersController lettersController;

    @Autowired
    RecognitionsController recognitionsController;

    @Autowired
    UsersController usersController;

    @Autowired
    GoodbyesController goodbyesController;

    @Autowired
    MaximunsController maximunsController;

    @Autowired
    SectorService sectorService;

    @Autowired
    SubSectorService subSectorService;

    @Autowired
    CompanyService companyService;

    @Autowired
    ReportCalidadService reportCalidadService;

    @Autowired
    SearchsController searchsController;

    @ApiOperation(value = "Cantidad de Noticias por Tipo"
            ,notes = "Se obtiene la cantidad de noticias publicadas el día en curso, por tipo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/news/general/count")
    public ResponseEntity<NewsCounts> getCount(){
        NewsCounts newsCount = new NewsCounts(
                welcomeController.getCountWelcomes(),
                roundtripsController.getCountRoundtripsCurrents(),
                lettersController.getCountLetters(),
                recognitionsController.getCountRecognitions(),
                usersController.getCountBirthdays(),
                goodbyesController.getCountGoodbyes(),
                searchsController.countWelcomesActives()

        );
        return new ResponseEntity(newsCount, HttpStatus.OK);
    }

    @ApiOperation(value = "Resumen Semanal de Noticias"
            ,notes = "Se obtienen las listas de las diferentes noticias de la última semana")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/news/general/summary")
    public ResponseEntity<Summary> getSummary(){
        Summary newSummary = new Summary(
               goodbyesController.getWelcomesForSummary(),
               lettersController.getLetterListForSummary(),
               maximunsController.getOutstanding(),
               recognitionsController.getRecognitionsForSummary(),
               roundtripsController.getRoundtripsForSummary(),
               welcomeController.getWelcomesForSummary(),
               usersController.getBirthdaysForSummary(),
               reportCalidadService.getById(1),
               reportCalidadService.getById(2),
               searchsController.getActives()

        );
        return new ResponseEntity(newSummary, HttpStatus.OK);
    }

    @ApiOperation(value = "Cantidad de Circulares por Área"
            ,notes = "Se obtiene la cantidad de circulares publicadas el día en curso, por área")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/news/roundtrips/general/count")
    public List<RoundtripsCount> getRoundtripsCountByType(){

        List<Sector> listSectors = sectorService.getAll();
        List<RoundtripsCount> list = new ArrayList<>();
        for (Sector listSector : listSectors) {
            list.add(new RoundtripsCount(listSector.getId(), sectorService.getOne(listSector.getId()).get().getName(), roundtripsController.getCountRoundtripsCurrentsByArea(sectorService.getOne(listSector.getId()).get())));
        }
        return list;
    }

    @ApiOperation(value = "Cantidad de Circulares por Subárea"
            ,notes = "Se obtiene la cantidad de circulares publicadas el día en curso, por subárea")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/news/roundtrips/general/subarea/count")
    public List<RoundtripsCount> getRoundtripsCountBySubsector(){

        List<SubSector> listSubSectors = subSectorService.getAll();
        List<RoundtripsCount> list = new ArrayList<>();
        for (SubSector listSubSector : listSubSectors) {
            list.add(new RoundtripsCount(listSubSector.getId(), subSectorService.getOne(listSubSector.getId()).get().getName(), roundtripsController.getCountRoundtripsCurrentsBySubsector(subSectorService.getOne(listSubSector.getId()).get())));
        }
        return list;
    }

    @ApiOperation(value = "Cantidad de Circulares por Subárea y Empresa"
            ,notes = "Se obtiene la cantidad de circulares publicadas el día en curso, por subárea y Empresa")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/news/roundtrips/general/subarea/{subarea}/company/count")
    public List<RoundtripsCount> getRoundtripsCountBySubsectorAndCompany(@PathVariable("subarea") SubSector subarea){

        List<Company> listCompanies = companyService.getAll();
        List<RoundtripsCount> list = new ArrayList<>();
        for (Company company: listCompanies){

            RoundtripsCount newRoundtripsCount = new RoundtripsCount(
                    company.getId(),
                    companyService.getOne(company.getId()).get().getName(),
                    roundtripsController.getCountRoundtripsCurrentsBySubsectorAndCompany(subarea, companyService.getOne(company.getId()).get())
            );

            list.add(newRoundtripsCount);

        }

        return list;
    }

}
