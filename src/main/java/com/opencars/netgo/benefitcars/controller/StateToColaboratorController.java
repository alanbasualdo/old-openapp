package com.opencars.netgo.benefitcars.controller;

import com.opencars.netgo.benefitcars.entity.StateToColaborator;
import com.opencars.netgo.benefitcars.service.StateToColaboratorService;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.msgs.MsgInt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Estado de Solicitudes de Beneficio en Compra de Vehículos para Colaboradores")
public class StateToColaboratorController {

    @Autowired
    StateToColaboratorService stateToColaboratorService;

    @ApiOperation(value = "Creación de Estado de Solicitudes de Beneficio en Compra de Vehículos para Colaboradores"
            ,notes = "Se envía objeto de tipo StateToColaborator a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/statetocolaborator")
    public ResponseEntity<?> create(@Valid @RequestBody StateToColaborator stateToColaborator, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        String message = "";

        StateToColaborator newState = new StateToColaborator(
                stateToColaborator.getId(),
                stateToColaborator.getState()
        );

        try{
            stateToColaboratorService.save(newState);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgInt("Estado Guardado", HttpStatus.CREATED.value(), newState.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Lista de Estado de Solicitudes de Beneficio en Compra de Vehículos para Colaboradores"
            ,notes = "Se obtiene una lista de Estado de Solicitudes de Beneficio en Compra de Vehículos para Colaboradores")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/statetocolaborator/list")
    public List<StateToColaborator> getAll(){

        List<StateToColaborator> list = stateToColaboratorService.getAll();

        return list;
    }
}
