package com.opencars.netgo.locations.controller;

import com.opencars.netgo.locations.entity.Sector;
import com.opencars.netgo.locations.service.SectorService;
import com.opencars.netgo.msgs.Msg;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Sectores")
public class SectorController {

    @Autowired
    SectorService sectorService;

    @ApiOperation(value = "Creación de un Área"
            ,notes = "Se envía objeto de tipo sector a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/sector")
    public ResponseEntity<?> create(@Valid @RequestBody Sector sector, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        Sector newSector =
                new Sector(

                        sector.getName()
                );
        sectorService.save(newSector);

        return new ResponseEntity(new Msg("Sector Guardado"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Actualización de un Área"
            ,notes = "Se actualiza la información de un área")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/sector/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Sector sector){
        Sector sectorUpdated = sectorService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sector not found for this id :: " + id));;
        sectorUpdated.setName(sector.getName());

        sectorService.save(sectorUpdated);

        return ResponseEntity.ok(sectorUpdated);
    }

    @ApiOperation(value = "Lista de Áreas"
            ,notes = "Se obtiene una lista de áreas sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/sector")
    public List<Sector> getSectors(){

        List<Sector> list = sectorService.getAll();

        return list;
    }

    @ApiOperation(value = "Área por ID"
            ,notes = "Se obtiene un área a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/sector/{id}")
    public List<Sector> getById(@PathVariable("id") int id){
        List<Sector> sector = new ArrayList<>();
        sector.add(sectorService.getOne(id).get());
        return sector;
    }

    @ApiOperation(value = "Lista de Áreas por Nombre"
            ,notes = "Se obtiene una lista de áreas por coincidencia en el nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/sector/name/{name}")
    public List<Sector> getSectorsByName(@PathVariable("name") String name){

        List<Sector> list = sectorService.getByName(name);

        return list;
    }
}
