package com.opencars.netgo.support.mobiles.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.mobiles.entity.LinesMobiles;
import com.opencars.netgo.support.mobiles.service.LinesMobilesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/linesmobiles")
@CrossOrigin
@Api(tags = "Controlador de Líneas Móviles")
public class LinesMobilesController {

    @Autowired
    LinesMobilesService linesMobilesService;

    @ApiOperation(value = "Creación de una Línea de Celular"
            ,notes = "Se envía objeto de tipo LinesMobiles a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody LinesMobiles line, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        LinesMobiles newLine =
                new LinesMobiles(
                        line.getLine(),
                        line.getPlan(),
                        line.getProvider(),
                        line.getCompany()
                );
        linesMobilesService.save(newLine);

        return new ResponseEntity(new Msg("Línea Guardada"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Lista de Líneas Móviles"
            ,notes = "Se obtiene una lista de líneas móviles paginada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT') or hasRole('VIEWPERMISSIONS') or hasRole('VIEWDLS')")
    @GetMapping("/lines")
    public Page<LinesMobiles> getLines(@PageableDefault(size = 10, page = 0) Pageable pageable){

        Page<LinesMobiles> list = linesMobilesService.getAll(pageable);

        return list;
    }

    @ApiOperation(value = "Actualización de una Línea de Celular"
            ,notes = "Se actualiza la información de una línea de celular")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/lines/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody LinesMobiles line){
        LinesMobiles lineUpdated = linesMobilesService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Line not found for this id :: " + id));;
        lineUpdated.setLine(line.getLine());
        lineUpdated.setPlan(line.getPlan());
        lineUpdated.setProvider(line.getProvider());
        lineUpdated.setCompany(line.getCompany());
        linesMobilesService.save(lineUpdated);

        return ResponseEntity.ok(lineUpdated);
    }

    @ApiOperation(value = "Línea de celular por ID"
            ,notes = "Se obtiene una línea de celular a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/lines/{id}")
    public ResponseEntity<LinesMobiles> getById(@PathVariable("id") int id){
        if(!linesMobilesService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        LinesMobiles line = linesMobilesService.getOne(id).get();
        return new ResponseEntity(line, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Líneas de Celulares por coincidencia en el número"
            ,notes = "Se obtiene una lista de líneas de celulares por coincidencia en el número")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/lines/number/{number}")
    public List<LinesMobiles> getBrandsByNumber(@PathVariable("number") String number){

        List<LinesMobiles> list = linesMobilesService.getByLine(number);

        return list;
    }
}
