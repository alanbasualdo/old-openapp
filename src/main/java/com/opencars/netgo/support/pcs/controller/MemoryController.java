package com.opencars.netgo.support.pcs.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.pcs.entity.Disk;
import com.opencars.netgo.support.pcs.entity.Memory;
import com.opencars.netgo.support.pcs.service.MemoryService;
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
@Api(tags = "Controlador de Memoria de Pcs")
public class MemoryController {

    @Autowired
    MemoryService memoryService;

    @ApiOperation(value = "Creación de una Memoria de Pc"
            ,notes = "Se envía objeto de tipo memory a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/memory")
    public ResponseEntity<?> create(@Valid @RequestBody Memory memory, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        Memory newMemory =
                new Memory(

                        memory.getValue()
                );
        memoryService.save(newMemory);

        return new ResponseEntity(new Msg("Memoria Guardada"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Lista de Memorias de Pc"
            ,notes = "Se obtiene una lista de memorias de pcs sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/memory")
    public List<Memory> getMemory(){

        List <Memory> list = memoryService.getAll();

        return list;
    }

    @ApiOperation(value = "Actualización de una memoria de Pc"
            ,notes = "Se actualiza la información de una memoria de pc")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/memory/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Memory memory){
        Memory memoryUpdated = memoryService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Memory not found for this id :: " + id));;
        memoryUpdated.setValue(memory.getValue());
        memoryService.save(memoryUpdated);

        return ResponseEntity.ok(memoryUpdated);
    }

    @ApiOperation(value = "Memoria de Pc por ID"
            ,notes = "Se obtiene una memoria de pc a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/memory/{id}")
    public ResponseEntity<Disk> getById(@PathVariable("id") int id){
        if(!memoryService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        Memory memory = memoryService.getOne(id).get();
        return new ResponseEntity(memory, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Memorias de Pc por Nombre"
            ,notes = "Se obtiene una lista de memorias de pc por coincidencia en el nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/memory/name/{name}")
    public List<Memory> getMemoryByName(@PathVariable("name") String name){

        List<Memory> list = memoryService.getByName(name);

        return list;
    }
}
