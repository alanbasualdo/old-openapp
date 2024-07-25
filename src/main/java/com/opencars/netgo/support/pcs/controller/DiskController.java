package com.opencars.netgo.support.pcs.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.pcs.entity.Disk;
import com.opencars.netgo.support.pcs.service.DiskService;
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
@Api(tags = "Controlador de Discos de Pcs")
public class DiskController {

    @Autowired
    DiskService diskService;

    @ApiOperation(value = "Creación de un Disco de Pc"
            ,notes = "Se envía objeto de tipo Disk a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/disk")
    public ResponseEntity<?> create(@Valid @RequestBody Disk disk, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        Disk newDisk =
                new Disk(

                        disk.getValue()
                );
        diskService.save(newDisk);

        return new ResponseEntity(new Msg("Disco Guardado"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Lista de Discos"
            ,notes = "Se obtiene una lista de discos de pc sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/disk")
    public List<Disk> getDisks(){

        List <Disk> list = diskService.getAll();

        return list;
    }

    @ApiOperation(value = "Actualización de un Disco de Pc"
            ,notes = "Se actualiza la información de un disco de pc")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/disk/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Disk disk){
        Disk diskUpdated = diskService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + id));;
        diskUpdated.setValue(disk.getValue());
        diskService.save(diskUpdated);

        return ResponseEntity.ok(diskUpdated);
    }

    @ApiOperation(value = "Disco de Pc por ID"
            ,notes = "Se obtiene un disco de pc a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/disk/{id}")
    public ResponseEntity<Disk> getById(@PathVariable("id") int id){
        if(!diskService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        Disk disk = diskService.getOne(id).get();
        return new ResponseEntity(disk, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Discos de Pc por Nombre"
            ,notes = "Se obtiene una lista de discos de pc por coincidencia en el nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/disk/name/{name}")
    public List<Disk> getDiskByName(@PathVariable("name") String name){

        List<Disk> list = diskService.getByName(name);

        return list;
    }
}
