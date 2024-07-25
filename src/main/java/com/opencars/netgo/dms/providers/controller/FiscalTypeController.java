package com.opencars.netgo.dms.providers.controller;

import ch.qos.logback.core.net.server.Client;
import com.opencars.netgo.dms.providers.entity.FiscalType;
import com.opencars.netgo.dms.providers.service.FiscalTypeService;
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

import java.util.List;

@RestController
@RequestMapping("/api/fiscaltype")
@CrossOrigin()
@Api(tags = "Controlador de Condiciones fiscales")
public class FiscalTypeController {

    @Autowired
    FiscalTypeService fiscalTypeService;

    @ApiOperation(value = "Creación de un condición fiscal"
            ,notes = "Se envía un objeto de tipo fiscalType a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody FiscalType fiscalType, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        if (fiscalTypeService.existsByNombre(fiscalType.getNombre()))
            return new ResponseEntity(new Msg("Ya existe una condición fiscal con ese nombre"), HttpStatus.BAD_REQUEST);


        String message = "";
        FiscalType newCondition =
                new FiscalType(
                        fiscalType.getNombre()
                );

        try{
            fiscalTypeService.save(newCondition);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Msg("Condición Guardada", HttpStatus.CREATED.value()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de una condición fiscal"
            ,notes = "Se actualiza una condición fiscal a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody FiscalType fiscalType, BindingResult bindingResult){
        String message = "";
        try{
            FiscalType conditionUpdated = fiscalTypeService.getOne(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Condition not found for this id :: " + id));
            conditionUpdated.setNombre(fiscalType.getNombre());

            fiscalTypeService.save(conditionUpdated);

            return ResponseEntity.ok(conditionUpdated);
        }catch (Exception e){
            if (bindingResult.hasErrors())
                return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
            if (fiscalTypeService.existsByNombre(fiscalType.getNombre()))
                return new ResponseEntity(new Msg("Ya existe una condición con ese nombre"), HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Lista de condiciones fiscales"
            ,notes = "Se obtiene una lista de condiciones fiscales")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list")
    public List<FiscalType> getConditions(){

        List<FiscalType> list = fiscalTypeService.list();

        return list;
    }

    @ApiOperation(value = "Condición fiscal por ID"
            ,notes = "Se obtiene una condición fiscal a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/condition/{id}")
    public ResponseEntity<Client> getById(@PathVariable("id") int id){
        if(!fiscalTypeService.existsById(id))
            return new ResponseEntity(new Msg("no existe"), HttpStatus.NOT_FOUND);
        FiscalType fiscalType = fiscalTypeService.getOne(id).get();
        return new ResponseEntity(fiscalType, HttpStatus.OK);
    }
}
