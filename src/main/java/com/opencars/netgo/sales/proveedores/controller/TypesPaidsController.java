package com.opencars.netgo.sales.proveedores.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.sales.proveedores.entity.TypesPaids;
import com.opencars.netgo.sales.proveedores.service.TypesPaidsService;
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
@RequestMapping("/api/typespaids")
@CrossOrigin
@Api(tags = "Controlador de Tipos de Pagos para el módulo Compras")
public class TypesPaidsController {

    @Autowired
    TypesPaidsService typesPaidsService;

    @ApiOperation(value = "Creación de un tipo de Pago"
            ,notes = "Se envía objeto de tipo TypesPaids a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/type")
    public ResponseEntity<?> create(@Valid @RequestBody TypesPaids typesPaids, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        TypesPaids newTypePaids =
                new TypesPaids(
                        typesPaids.getType()
                );
        typesPaidsService.save(newTypePaids);

        return new ResponseEntity(new Msg("Tipo de pago guardado"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Lista de Tipos de Pago"
            ,notes = "Se obtiene una lista de tipos de pago sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/list")
    public List<TypesPaids> getTypesPaids(){

        List<TypesPaids> list = typesPaidsService.getAll();

        return list;
    }

    @ApiOperation(value = "Actualización de un tipo de pago"
            ,notes = "Se actualiza la información de un tipo de pago")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody TypesPaids typesPaids){
        TypesPaids typeUpdated = typesPaidsService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("TypesPaids not found for this id :: " + id));;
        typeUpdated.setType(typesPaids.getType());
        typesPaidsService.save(typeUpdated);

        return ResponseEntity.ok(typeUpdated);
    }

    @ApiOperation(value = "Tipo de Pago por ID"
            ,notes = "Se obtiene un tipo de pago a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/type/{id}")
    public ResponseEntity<TypesPaids> getById(@PathVariable("id") int id){
        if(!typesPaidsService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        TypesPaids typesPaids = typesPaidsService.getOne(id).get();
        return new ResponseEntity(typesPaids, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de tipos de pagos por Coincidencia"
            ,notes = "Se obtiene una lista de tipos de pago por coincidencia")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/type/coincidence/{coincidence}")
    public List<TypesPaids> getTypePaidByCoincidence(@PathVariable("coincidence") String coincidence){

        List<TypesPaids> list = typesPaidsService.getByCoincidence(coincidence);

        return list;
    }
}
