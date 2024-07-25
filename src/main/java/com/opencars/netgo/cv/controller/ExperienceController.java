package com.opencars.netgo.cv.controller;

import com.opencars.netgo.cv.entity.Experience;
import com.opencars.netgo.cv.service.ExperienceService;
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
@Api(tags = "Controlador de Experiencia Laboral")
public class ExperienceController {

    @Autowired
    ExperienceService experienceService;

    @ApiOperation(value = "Creación de un ítem de Experiencia Laboral"
            ,notes = "Se envía objeto de tipo experience a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/experience")
    public ResponseEntity<?> create(@Valid @RequestBody Experience experience, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        String message = "";
        Experience newExperience =
                new Experience(

                        experience.getPosition(),
                        experience.getEnterprise(),
                        experience.getYearInit(),
                        experience.getYearEnd(),
                        experience.getDetail()
                );

        try{
            experienceService.save(newExperience);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgInt("Experiencia Guardado", HttpStatus.CREATED.value(), newExperience.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de un ítem de Experiencia Laboral"
            ,notes = "Se actualiza la información de un ítem de experiencia laboral")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/experience/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Experience experience){
        String message = "";
        Experience experienceUpdated = experienceService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found for this id :: " + id));

        experienceUpdated.setPosition(experience.getPosition());
        experienceUpdated.setEnterprise(experience.getEnterprise());
        experienceUpdated.setYearInit(experience.getYearInit());
        experienceUpdated.setYearEnd(experience.getYearEnd());
        experienceUpdated.setDetail(experience.getDetail());

        try{
            experienceService.save(experienceUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Experiencia Actualizada", HttpStatus.OK.value()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Experiencia Laboral por ID"
            ,notes = "Se obtiene un ítem de experiencia laboral específico a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/experience/id/{id}")
    public Experience getById(@PathVariable("id") int id){

        Experience experience = experienceService.getOne(id).get();

        return experience;
    }

    @ApiOperation(value = "Eliminación de ítem de Experiencia Laboral por ID"
            ,notes = "Se elimina un título a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/experience/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            experienceService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Experiencia Eliminada", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

}
