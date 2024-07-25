package com.opencars.netgo.dms.expertises.controller;

import com.opencars.netgo.dms.expertises.entity.StatesNoYes;
import com.opencars.netgo.dms.expertises.service.StatesNoYesService;
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
@RequestMapping("/api/statesnoyes")
@CrossOrigin()
@Api(tags = "Controlador de Estados No-Si para usar en Peritajes y otros")
public class StatesNoYesController {

    @Autowired
    StatesNoYesService statesNoYesService;

    @ApiOperation(value = "Lista de Estados"
            ,notes = "Se obtiene una lista de estados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list")
    public List<StatesNoYes> getAll(){
        List<StatesNoYes> list = statesNoYesService.findAll();
        return list;
    }
}
