package com.opencars.netgo.dms.expertises.controller;

import com.opencars.netgo.dms.expertises.entity.Missing;
import com.opencars.netgo.dms.expertises.service.MissingService;
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
@Api(tags = "Controlador de Faltantes de Peritajes")
public class MissingController {

    @Autowired
    MissingService missingService;

    @ApiOperation(value = "Creación de Faltante"
            ,notes = "Se envía faltante a través de un payload y devuelve como resultado un objeto de tipo missing")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('SUPERVISORPERITAJES')")
    @PostMapping("/missing")
    public ResponseEntity<?> create(@Valid @RequestBody Missing missing, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        Missing newMissing =
                new Missing(
                        missing.getMissing()
                );
        missingService.save(newMissing);

        return new ResponseEntity(new Msg("Faltante Guardado"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Actualización de un Faltante"
            ,notes = "Se actualiza la información de un faltante")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('SUPERVISORPERITAJES')")
    @PutMapping("/missing/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Missing missing){
        Missing missingUpdated = missingService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Missing not found for this id :: " + id));;
        missingUpdated.setMissing(missing.getMissing());
        missingService.save(missingUpdated);

        return ResponseEntity.ok(missingUpdated);
    }

    @ApiOperation(value = "Lista de Faltantes"
            ,notes = "Se obtiene una lista de faltantes sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/missing")
    public List<Missing> getMissings(){

        List<Missing> list = missingService.getAll();

        return list;
    }

    @ApiOperation(value = "Faltante por ID"
            ,notes = "Se obtiene un faltante específico a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/missing/{id}")
    public ResponseEntity<Missing> getById(@PathVariable("id") int id){
        if(!missingService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        Missing missing = missingService.getOne(id).get();
        return new ResponseEntity(missing, HttpStatus.OK);
    }

    @ApiOperation(value = "Eliminación de un faltante, por ID"
            ,notes = "Se elimina un faltante, a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('SUPERVISORPERITAJES')")
    @DeleteMapping("/missing/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            missingService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
