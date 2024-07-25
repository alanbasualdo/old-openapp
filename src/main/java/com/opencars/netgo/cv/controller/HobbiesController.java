package com.opencars.netgo.cv.controller;

import com.opencars.netgo.cv.entity.Experience;
import com.opencars.netgo.cv.entity.Hobbies;
import com.opencars.netgo.cv.service.HobbiesService;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.msgs.MsgInt;
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

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Hobbies")
public class HobbiesController {

    @Autowired
    HobbiesService hobbiesService;

    @ApiOperation(value = "Creación de Hobbies"
            ,notes = "Se envía objeto de tipo hobbies a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/hobbies")
    public ResponseEntity<?> create(@Valid @RequestBody Hobbies hobbies, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        String message = "";
        Hobbies newHobbies =
                new Hobbies(

                        hobbies.getHobbie(),
                        hobbies.getDescription()
                );
        try{
            hobbiesService.save(newHobbies);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgInt("Curso Guardado", HttpStatus.CREATED.value(), newHobbies.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de un Hobbie"
            ,notes = "Se actualiza la información de un hobbie")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/hobbies/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Hobbies hobbies){
        String message = "";
        Hobbies hobbieUpdated = hobbiesService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hobbie not found for this id :: " + id));

        hobbieUpdated.setHobbie(hobbies.getHobbie());
        hobbieUpdated.setDescription(hobbies.getDescription());

        try{
            hobbiesService.save(hobbieUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Hobbie Actualizado", HttpStatus.OK.value()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Hobbie por ID"
            ,notes = "Se obtiene un hobbie específico a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/hobbie/id/{id}")
    public Hobbies getById(@PathVariable("id") int id){

        Hobbies hobbies = hobbiesService.getOne(id).get();
        return hobbies;

    }

    @ApiOperation(value = "Eliminación de un Hobbie por ID"
            ,notes = "Se elimina un Hobbie a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/hobbies/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            hobbiesService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Hobbie Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

}
