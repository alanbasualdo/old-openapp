package com.opencars.netgo.locations.controller;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.locations.entity.Sector;
import com.opencars.netgo.locations.entity.SubSector;
import com.opencars.netgo.locations.service.SubSectorService;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.tickets.entity.TicketsStates;
import com.opencars.netgo.users.entity.User;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Subsector")
public class SubSectorController {

    @Autowired
    SubSectorService subSectorService;

    @ApiOperation(value = "Creación de un subsector"
            ,notes = "Se envía objeto de tipo subsector a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/subsector")
    public ResponseEntity<?> create(@Valid @RequestBody SubSector subSector, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        SubSector newSubsector =
                new SubSector(
                        subSector.getName(),
                        subSector.getSector()
                );

        subSectorService.save(newSubsector);

        return new ResponseEntity(new Msg("Subárea Guardada"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Actualización de una subárea"
            ,notes = "Se actualiza la información de una subárea")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/subsector/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody SubSector subSector){
        SubSector subSectorUpdated = subSectorService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subsector not found for this id :: " + id));;
        subSectorUpdated.setName(subSector.getName());
        subSectorUpdated.setSector(subSector.getSector());

        subSectorService.save(subSectorUpdated);

        return ResponseEntity.ok(subSectorUpdated);
    }

    @ApiOperation(value = "Lista de Subáreas"
            ,notes = "Se obtiene una lista de subáreas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/subsector")
    public List<SubSector> getSubsectors(){

        List<SubSector> list = subSectorService.getAll();

        return list;
    }

    @ApiOperation(value = "Subárea por ID"
            ,notes = "Se obtiene una subárea a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/subsector/{id}")
    public ResponseEntity<Branch> getById(@PathVariable("id") int id){
        if(!subSectorService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        SubSector subSector = subSectorService.getOne(id).get();
        return new ResponseEntity(subSector, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Subáreas por Sector"
            ,notes = "Se obtiene una lista de subáreas correspondientes a un sector")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/subsector/sector/{sector}")
    public List<SubSector> getSubsectorsBySector(@PathVariable("sector") Sector sector){

        List<SubSector> list = subSectorService.getBySector(sector);

        return list;
    }

    @ApiOperation(value = "Verifica si existe un área tiene subáreas"
            ,notes = "Verifica si existe un área tiene subáreas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/subsector/verification/sector/{sector}")
    public ResponseEntity<?> verificationSubsector(@PathVariable("sector") Sector sector){
        int count = subSectorService.countSubsector(sector);
        if (count == 0){
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("No hay subáreas para el área consultado.", HttpStatus.NOT_FOUND.value()));

        }else{
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Se han encontrado subáreas", HttpStatus.OK.value()));

        }
    }

}
