package com.opencars.netgo.cv.controller;

import com.opencars.netgo.cv.entity.*;
import com.opencars.netgo.cv.service.EducationService;
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
@Api(tags = "Controlador de Carrera de Educación")
public class EducationController {

    @Autowired
    EducationService educationService;

    @ApiOperation(value = "Creación de un Título | Formación Profesional"
            ,notes = "Se envía objeto de tipo education a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/education")
    public ResponseEntity<?> create(@Valid @RequestBody Education education, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        String message = "";
        Education newEducation =
                new Education(

                        education.getTitle(),
                        education.getInstitution(),
                        education.getYearInit(),
                        education.getYearEnd(),
                        education.getCertificate()
                );


        try{
            educationService.save(newEducation);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgInt("Título Guardado", HttpStatus.CREATED.value(), newEducation.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de un Título"
            ,notes = "Se actualiza la información de un título")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/education/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Education education){
        String message = "";
        Education educationUpdated = educationService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Title not found for this id :: " + id));

        educationUpdated.setTitle(education.getTitle());
        educationUpdated.setInstitution(education.getInstitution());
        educationUpdated.setYearInit(education.getYearInit());
        educationUpdated.setYearEnd(education.getYearEnd());
        educationUpdated.setCertificate(education.getCertificate());

        try{
            educationService.save(educationUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(new MsgInt("Título Actualizado", HttpStatus.OK.value(), educationUpdated.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Título por ID"
            ,notes = "Se obtiene un título específico a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/education/id/{id}")
    public Education getById(@PathVariable("id") int id){

        Education education = educationService.getOne(id).get();

        return education;
    }

    @ApiOperation(value = "Eliminación de Título por ID"
            ,notes = "Se elimina un título a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/education/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            educationService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Título Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }


}
