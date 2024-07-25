package com.opencars.netgo.cv.controller;

import com.opencars.netgo.cv.entity.Certifications;
import com.opencars.netgo.cv.service.CertificationsService;
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
@Api(tags = "Controlador de Cursos | Certificaciones")
public class CertificationsController {

    @Autowired
    CertificationsService certificationsService;

    @ApiOperation(value = "Creación de un Curso o Certificación"
            ,notes = "Se envía objeto de tipo certification a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/certification")
    public ResponseEntity<?> create(@Valid @RequestBody Certifications certification, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        String message = "";
        Certifications newCertification =
                new Certifications(

                        certification.getCourse(),
                        certification.getInstitution(),
                        certification.getYearEnd(),
                        certification.getCertificate()
                );

        try{
            certificationsService.save(newCertification);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgInt("Curso Guardado", HttpStatus.CREATED.value(), newCertification.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de un Curso"
            ,notes = "Se actualiza la información de un curso")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/certification/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Certifications certification){
        String message = "";
        Certifications certificationUpdated = certificationsService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found for this id :: " + id));

        certificationUpdated.setCourse(certification.getCourse());
        certificationUpdated.setInstitution(certification.getInstitution());
        certificationUpdated.setYearEnd(certification.getYearEnd());
        certificationUpdated.setCertificate(certification.getCertificate());

        try{
            certificationsService.save(certificationUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(new MsgInt("Curso Actualizado", HttpStatus.OK.value(), certificationUpdated.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Curso por ID"
            ,notes = "Se obtiene un curso específico a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/certification/id/{id}")
    public Certifications getById(@PathVariable("id") int id){

        Certifications certification = certificationsService.getOne(id).get();

        return certification;
    }

    @ApiOperation(value = "Eliminación de un Curso por ID"
            ,notes = "Se elimina un curso a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/certification/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            certificationsService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Curso Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
