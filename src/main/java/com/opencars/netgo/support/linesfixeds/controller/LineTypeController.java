package com.opencars.netgo.support.linesfixeds.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.linesfixeds.entity.LineType;
import com.opencars.netgo.support.linesfixeds.service.LineTypeService;
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
@RequestMapping("/api/linesfixedstypes")
@CrossOrigin
@Api(tags = "Controlador de Tipos de Líneas Fijas")
public class LineTypeController {

    @Autowired
    LineTypeService lineTypeService;

    @ApiOperation(value = "Creación de un tipo de Dispositivo de Línea Fija"
            ,notes = "Se envía objeto de tipo LineType a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/type")
    public ResponseEntity<?> create(@Valid @RequestBody LineType lineType, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        LineType newLineType =
                new LineType(
                        lineType.getType()
                );
        lineTypeService.save(newLineType);

        return new ResponseEntity(new Msg("Tipo de línea fija guardado"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Lista de Tipos de Líneas Fijas"
            ,notes = "Se obtiene una lista de tipos de líneas fijas sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/types/list")
    public List<LineType> getTypes(){

        List<LineType> list = lineTypeService.getAll();

        return list;
    }

    @ApiOperation(value = "Actualización de un tipo de Línea Fija"
            ,notes = "Se actualiza la información de un tipo de línea fija")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/types/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody LineType lineType){
        LineType typeUpdated = lineTypeService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("LineType not found for this id :: " + id));;
        typeUpdated.setType(lineType.getType());
        lineTypeService.save(typeUpdated);

        return ResponseEntity.ok(typeUpdated);
    }

    @ApiOperation(value = "Tipo de Línea Fija por ID"
            ,notes = "Se obtiene un tipo de línea fija a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/type/{id}")
    public ResponseEntity<LineType> getById(@PathVariable("id") int id){
        if(!lineTypeService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        LineType lineType = lineTypeService.getOne(id).get();
        return new ResponseEntity(lineType, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de tipos de Líneas Fijas por Coincidencia"
            ,notes = "Se obtiene una lista de tipos de líneas fijas por coincidencia")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/type/coincidence/{coincidence}")
    public List<LineType> getLineTypeByCoincidence(@PathVariable("coincidence") String coincidence){

        List<LineType> list = lineTypeService.getByCoincidence(coincidence);

        return list;
    }

}
