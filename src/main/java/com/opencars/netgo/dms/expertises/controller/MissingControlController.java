package com.opencars.netgo.dms.expertises.controller;

import com.opencars.netgo.dms.expertises.entity.MissingControl;
import com.opencars.netgo.dms.expertises.service.MissingControlService;
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
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Precios de Faltantes de Peritajes")
public class MissingControlController {

    @Autowired
    MissingControlService missingControlService;

    @ApiOperation(value = "Creación de registro de control de precio de Faltante"
            ,notes = "Se envía registro a través de un payload y devuelve como resultado un objeto de tipo missingcontrol")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('SUPERVISORPERITAJES')")
    @PostMapping("/missingcontrol")
    public ResponseEntity<?> create(@Valid @RequestBody MissingControl missing, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        MissingControl newMissing =
                new MissingControl(
                        missing.getMissing(),
                        missing.getTypeOfCars(),
                        missing.getPrice()
                );
        missingControlService.save(newMissing);

        return new ResponseEntity(new Msg("Registro Guardado"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Actualización de un Registro de control de precio de faltante de peritaje"
            ,notes = "Se actualiza la información de un registro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('SUPERVISORPERITAJES')")
    @PutMapping("/missingcontrol/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody MissingControl missing){
        MissingControl missingUpdated = missingControlService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Missingcontrol not found for this id :: " + id));;
        missingUpdated.setMissing(missing.getMissing());
        missingUpdated.setTypeOfCars(missing.getTypeOfCars());
        missingUpdated.setPrice(missing.getPrice());
        missingControlService.save(missingUpdated);

        return ResponseEntity.ok(missingUpdated);
    }

    @ApiOperation(value = "Lista de registro de control de precios de faltantes de peritajes"
            ,notes = "Se obtiene una lista de registros sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/missingcontrol")
    public List<MissingControl> getMissingscontrol(){
        List<MissingControl> list = missingControlService.getAll();
        return list;
    }

    @ApiOperation(value = "Registro por ID"
            ,notes = "Se obtiene un registro específico a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/missingcontrol/{id}")
    public ResponseEntity<MissingControl> getById(@PathVariable("id") int id){
        if(!missingControlService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        MissingControl missing = missingControlService.getOne(id).get();
        return new ResponseEntity(missing, HttpStatus.OK);
    }

    @ApiOperation(value = "Eliminación de un registro de control de precios de faltantes, por ID"
            ,notes = "Se elimina un registro de control de precios de faltantes, a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('SUPERVISORPERITAJES')")
    @DeleteMapping("/missingcontrol/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            missingControlService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
