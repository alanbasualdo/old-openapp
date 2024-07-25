package com.opencars.netgo.sales.proveedores.controller;

import com.opencars.netgo.msgs.MsgInt;
import com.opencars.netgo.sales.proveedores.entity.ExceptedsConceptsTreasurers;
import com.opencars.netgo.sales.proveedores.service.ExceptedsConceptsTreasurersService;
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
@RequestMapping("/api/treasurers")
@CrossOrigin
@Api(tags = "Controlador de Conceptos Exceptuados para un Tesorero")
public class ExceptedsConceptsTreasurersController {

    @Autowired
    ExceptedsConceptsTreasurersService exceptedsConceptsTreasurersService;

    @ApiOperation(value = "Lista de conceptos exceptuados para un Tesorero"
            ,notes = "Se obtiene una lista de conceptos exceptuados para un tesorero")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/excepted/concepts/list")
    public List<ExceptedsConceptsTreasurers> getAll(){
        List<ExceptedsConceptsTreasurers> list = exceptedsConceptsTreasurersService.list();
        return list;
    }

    @ApiOperation(value = "Tesorero de concepto por ID"
            ,notes = "Se obtiene un tesorero de concepto a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/excepted/concepts/{id}")
    public ResponseEntity<ExceptedsConceptsTreasurers> getById(@PathVariable("id") int id){
        ExceptedsConceptsTreasurers exceptedsConcepts = exceptedsConceptsTreasurersService.getOne(id).get();
        return new ResponseEntity(exceptedsConcepts, HttpStatus.OK);
    }

    @ApiOperation(value = "Creación de un concepto exceptuado por Tesorero"
            ,notes = "Se envía objeto de tipo ExceptedsConceptsTreasurers a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/excepted/concepts/create")
    public ResponseEntity<?> create(@Valid @RequestBody ExceptedsConceptsTreasurers exceptedsConcepts, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MsgInt("Alguno de los datos no coinciden con el formato esperado", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        String message = "";

        try{

            ExceptedsConceptsTreasurers newConcept = new ExceptedsConceptsTreasurers(
                    exceptedsConcepts.getConcept(),
                    exceptedsConcepts.getTreasurer()
            );

            exceptedsConceptsTreasurersService.save(newConcept);

            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgInt("Registro Guardado", HttpStatus.CREATED.value(), newConcept.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de un tesorero de concepto"
            ,notes = "Se actualiza un tesorero de concepto a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/excepted/concepts/{id}")
    public ResponseEntity<MsgInt>  update(@PathVariable(value = "id") int id, @Valid @RequestBody ExceptedsConceptsTreasurers exceptedsConcepts){
        ExceptedsConceptsTreasurers conceptUpdated = exceptedsConceptsTreasurersService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Concept not found for this id :: " + id));

        String message = "";

        conceptUpdated.setConcept(exceptedsConcepts.getConcept());
        conceptUpdated.setTreasurer(exceptedsConcepts.getTreasurer());

        try{
            exceptedsConceptsTreasurersService.save(conceptUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(new MsgInt("El registro se ha actualizado con éxito.", HttpStatus.OK.value(), conceptUpdated.getId()));

        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Eliminación de un tesorero de concepto exceptuado por ID"
            ,notes = "Se elimina un registro a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/excepted/concepts/delete/{id}")
    public ResponseEntity<MsgInt> deleteById(@PathVariable int id) {
        try{
            exceptedsConceptsTreasurersService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new MsgInt("Registro Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MsgInt("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
