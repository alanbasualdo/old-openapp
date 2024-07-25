package com.opencars.netgo.news.compliance.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.news.compliance.entity.Titles;
import com.opencars.netgo.news.compliance.service.TitlesComplianceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Títulos de Compliance")
public class TitlesComplianceController {

    @Autowired
    TitlesComplianceService titlesComplianceService;

    @ApiOperation(value = "Creación de un Título de manual de Compliance"
            ,notes = "Se envía un objeto de tipo title a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCCHH')")
    @PostMapping("/compliance/title")
    public ResponseEntity<?> create(@Valid @RequestBody Titles titles){
        Titles newTitle =
                new Titles(

                        titles.getTitle(),
                        titles.getNumber()

                );
        titlesComplianceService.save(newTitle);

        return new ResponseEntity(new Msg("Título Guardado"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Actualización de una Título del Manual de Compliance"
            ,notes = "Se actualiza un título a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCCHH')")
    @PutMapping("/compliance/title/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Titles titles){
        Titles titleUpdated = titlesComplianceService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Title not found for this id :: " + id));;
        titleUpdated.setTitle(titles.getTitle());
        titleUpdated.setNumber(titles.getNumber());

        titlesComplianceService.save(titleUpdated);

        return ResponseEntity.ok(titleUpdated);
    }

    @ApiOperation(value = "Lista de Títulos de Manual de Compliance"
            ,notes = "Se obtiene una lista de los títulos del manual de compliance")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCCHH')")
    @GetMapping("/compliance/titles")
    public List<Titles> getTitlesCompliance(){

        List<Titles> list = titlesComplianceService.getAll();

        return list;
    }
}
