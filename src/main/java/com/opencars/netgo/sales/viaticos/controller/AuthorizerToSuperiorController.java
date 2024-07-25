package com.opencars.netgo.sales.viaticos.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.msgs.MsgInt;
import com.opencars.netgo.sales.viaticos.entity.AuthorizerToSuperior;
import com.opencars.netgo.sales.viaticos.service.AuthorizerToSuperiorService;
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
@Api(tags = "Controlador de Autorizante del Autorizante Superior")
public class AuthorizerToSuperiorController {

    @Autowired
    AuthorizerToSuperiorService authorizerToSuperiorService;

    @ApiOperation(value = "Creación de un autorizante de superior"
            ,notes = "Se envía objeto de tipo AuthorizerToSuperior a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/authorizers/to/sup/create")
    public ResponseEntity<?> create(@Valid @RequestBody AuthorizerToSuperior authorizerToSuperior, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MsgInt("Alguno de los datos no coinciden con el formato esperado", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        String message = "";

        try{

            AuthorizerToSuperior newAuthorizer = new AuthorizerToSuperior(
                    authorizerToSuperior.getColaborator()
            );

            authorizerToSuperiorService.save(newAuthorizer);

            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgInt("Registro Guardado", HttpStatus.CREATED.value(), newAuthorizer.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Lista de autorizante de autorizante superior"
            ,notes = "Se obtiene una lista de autorizante de autorizante superior")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/authorizers/to/sup/list")
    public List<AuthorizerToSuperior> getAll(){
        List<AuthorizerToSuperior> list = authorizerToSuperiorService.list();
        return list;
    }

    @ApiOperation(value = "Registro de un autorizante de autorizante superior por ID"
            ,notes = "Se obtiene un registro de un autorizante de autorizante superior a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/authorizers/to/sup/{id}")
    public ResponseEntity<AuthorizerToSuperior> getById(@PathVariable("id") int id){
        AuthorizerToSuperior authorizerToSuperior = authorizerToSuperiorService.getOne(id).get();
        return new ResponseEntity(authorizerToSuperior, HttpStatus.OK);
    }

    @ApiOperation(value = "Actualización de un autorizante de autorizante superior"
            ,notes = "Se actualiza un autorizante de autorizante superior través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/authorizers/to/sup/{id}")
    public ResponseEntity<MsgInt>  update(@PathVariable(value = "id") int id, @Valid @RequestBody AuthorizerToSuperior authorizerToSuperior){
        AuthorizerToSuperior authorizerUpdated = authorizerToSuperiorService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Authorizer not found for this id :: " + id));

        String message = "";

        authorizerUpdated.setColaborator(authorizerToSuperior.getColaborator());

        try{
            authorizerToSuperiorService.save(authorizerUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(new MsgInt("El registro se ha actualizado con éxito.", HttpStatus.OK.value(), authorizerUpdated.getId()));

        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Eliminación de Autorizante de Superior"
            ,notes = "Se elimina un autorizante de superior a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/authorizers/to/sup/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            authorizerToSuperiorService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
