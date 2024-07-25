package com.opencars.netgo.cv.controller;

import com.opencars.netgo.cv.dto.CVsDTO;
import com.opencars.netgo.cv.dto.CountCVs;
import com.opencars.netgo.cv.dto.CvResponse;
import com.opencars.netgo.cv.entity.*;
import com.opencars.netgo.cv.service.CvService;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.msgs.MsgInt;
import com.opencars.netgo.users.entity.User;
import com.opencars.netgo.users.service.UserService;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de CV")
public class CvController {

    @Autowired
    CvService cvService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "Creación de un cv"
            ,notes = "Se envía objeto de tipo cv a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/cv")
    public ResponseEntity<?> create(@Valid @RequestBody Cv cv, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        String message = "";

        User colaborator = userService.getOne(cv.getColaborator().getId()).get();

        Cv newCv = new Cv();

        newCv.setColaborator(colaborator);
        Set<Education> education = new HashSet<>(cv.getEducation());
        newCv.setEducation(education);
        Set<Certifications> certifications = new HashSet<>(cv.getCertifications());
        newCv.setCertifications(certifications);
        Set<Experience> experience = new HashSet<>(cv.getExperience());
        newCv.setExperience(experience);
        Set<Hobbies> hobbies = new HashSet<>(cv.getHobbies());
        newCv.setHobbies(hobbies);

        try{
            cvService.save(newCv);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgInt("CV Guardado", HttpStatus.CREATED.value(), newCv.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de un CV"
            ,notes = "Se actualiza la información de un CV")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/cv/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Cv cv){
        String message = "";
        Cv cvUpdated = cvService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("CV not found for this id :: " + id));;
        cvUpdated.setColaborator(cv.getColaborator());

        Set<Education> education = new HashSet<>(cv.getEducation());
        cvUpdated.setEducation(education);
        Set<Certifications> certifications = new HashSet<>(cv.getCertifications());
        cvUpdated.setCertifications(certifications);
        Set<Experience> experiences = new HashSet<>(cv.getExperience());
        cvUpdated.setExperience(experiences);
        Set<Hobbies> hobbies = new HashSet<>(cv.getHobbies());
        cvUpdated.setHobbies(hobbies);

        try{
            cvService.save(cvUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("CV Actualizado", HttpStatus.OK.value()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "CV por colaborador"
            ,notes = "Se obtiene el CV de un colaborador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cv/colaborator/{colaborator}")
    public ResponseEntity<?> getByColaborator(@PathVariable("colaborator") int colaborator){
        String message = "";
        try{
            Cv cv = cvService.getByColaborator(colaborator).get();
            return ResponseEntity.status(HttpStatus.OK).body(new CvResponse(cv, HttpStatus.OK.value()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Msg(message, HttpStatus.NOT_FOUND.value()));
        }

    }

    @ApiOperation(value = "CV por coincidencia en título, cursos y experiencia"
            ,notes = "Se obtiene una lista de cvs por coincidencia en título, cursos y experiencia")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCCHH')")
    @GetMapping("/cv/coincidence/{coincidence}")
    public List<CVsDTO> getByCoincidence(@PathVariable("coincidence") String coincidence){
        List<CVsDTO> list = cvService.getByCoincidence(coincidence);
        return list;
    }

    @ApiOperation(value = "Estado de Curriculums por cantidad"
            ,notes = "Se obtiene la cantidad de curriculums creados y pendientes de creación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCCHH')")
    @GetMapping("/cv/counts")
    public CountCVs getCount(){

        CountCVs countCVs = cvService.countCVs();

        return countCVs;

    }
}
