package com.opencars.netgo.support.pcs.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.pcs.entity.PcTypes;
import com.opencars.netgo.support.pcs.entity.Processor;
import com.opencars.netgo.support.pcs.service.TypesService;
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
@RequestMapping("/api/pcs")
@CrossOrigin
@Api(tags = "Controlador de Tipos de Pcs")
public class TypesController {

    @Autowired
    TypesService typesService;

    @ApiOperation(value = "Creación de un Tipo de Pc"
            ,notes = "Se envía objeto Tipo de Pc a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/types")
    public ResponseEntity<?> create(@Valid @RequestBody PcTypes pcTypes, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        PcTypes newPcTypes =
                new PcTypes(

                        pcTypes.getType()
                );
        typesService.save(newPcTypes);

        return new ResponseEntity(new Msg("Tipo de Pc Guardado"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Lista de Tipos de Pc"
            ,notes = "Se obtiene una lista con los distintos tipos de pc sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/types")
    public List<PcTypes> getTypes(){

        List <PcTypes> list = typesService.getAll();

        return list;
    }

    @ApiOperation(value = "Actualización de un Tipo de Pc"
            ,notes = "Se actualiza la información de un tipo de pc")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/types/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody PcTypes pcTypes){
        PcTypes pcTypesUpdated = typesService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Memory not found for this id :: " + id));;
        pcTypesUpdated.setType(pcTypes.getType());
        typesService.save(pcTypesUpdated);

        return ResponseEntity.ok(pcTypesUpdated);
    }

    @ApiOperation(value = "Tipo de Pc por ID"
            ,notes = "Se obtiene un tipo de pc a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/types/{id}")
    public ResponseEntity<PcTypes> getById(@PathVariable("id") int id){
        if(!typesService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        PcTypes pcTypes = typesService.getOne(id).get();
        return new ResponseEntity(pcTypes, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Tipos de Pc por Nombre"
            ,notes = "Se obtiene una lista de tipos de pc por coincidencia en el nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/types/name/{name}")
    public List<PcTypes> getPcTypesByName(@PathVariable("name") String name){

        List<PcTypes> list = typesService.getByName(name);

        return list;
    }
}
