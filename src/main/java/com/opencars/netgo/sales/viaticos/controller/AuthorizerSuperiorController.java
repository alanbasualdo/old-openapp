package com.opencars.netgo.sales.viaticos.controller;

import com.opencars.netgo.msgs.MsgInt;
import com.opencars.netgo.sales.viaticos.entity.AuthorizerSuperior;
import com.opencars.netgo.sales.viaticos.service.AuthorizerSuperiorService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Autorizante Superior")
public class AuthorizerSuperiorController {

    @Autowired
    AuthorizerSuperiorService authorizerSuperiorService;

    @ApiOperation(value = "Lista de autorizantes superiores"
            ,notes = "Se obtiene una lista de autorizantes superiores")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/authorizers/sup/list")
    public List<AuthorizerSuperior> getAll(){
        List<AuthorizerSuperior> list = authorizerSuperiorService.list();
        return list;
    }

    @ApiOperation(value = "Registro de un autorizante superior por ID"
            ,notes = "Se obtiene un registro de un autorizante superior a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/authorizers/sup/{id}")
    public ResponseEntity<AuthorizerSuperior> getById(@PathVariable("id") int id){
        AuthorizerSuperior authorizerSuperior = authorizerSuperiorService.getOne(id).get();
        return new ResponseEntity(authorizerSuperior, HttpStatus.OK);
    }

    @ApiOperation(value = "Actualización de un autorizante superior"
            ,notes = "Se actualiza un autorizante superior través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/authorizers/sup/{id}")
    public ResponseEntity<MsgInt>  update(@PathVariable(value = "id") int id, @Valid @RequestBody AuthorizerSuperior authorizerSuperior){
        AuthorizerSuperior authorizerUpdated = authorizerSuperiorService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Authorizer not found for this id :: " + id));

        String message = "";

        authorizerUpdated.setColaborator(authorizerSuperior.getColaborator());

        try{
            authorizerSuperiorService.save(authorizerUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(new MsgInt("El registro se ha actualizado con éxito.", HttpStatus.OK.value(), authorizerUpdated.getId()));

        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }
}
