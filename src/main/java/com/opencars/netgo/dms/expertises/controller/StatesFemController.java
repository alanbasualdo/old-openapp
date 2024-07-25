package com.opencars.netgo.dms.expertises.controller;

import com.opencars.netgo.dms.expertises.entity.StatesFem;
import com.opencars.netgo.dms.expertises.service.StatesFemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statesfem")
@CrossOrigin()
@Api(tags = "Controlador de Estados en femenino para usar en Peritajes y otros")
public class StatesFemController {

    @Autowired
    StatesFemService statesFemService;

    @ApiOperation(value = "Lista de Estados en femenino"
            ,notes = "Se obtiene una lista de estados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list")
    public List<StatesFem> getAll(){
        List<StatesFem> list = statesFemService.findAll();
        return list;
    }
}