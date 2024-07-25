package com.opencars.netgo.sales.viaticos.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.msgs.MsgInt;
import com.opencars.netgo.sales.viaticos.entity.AuthorizerToSuperior2;
import com.opencars.netgo.sales.viaticos.service.AuthorizerToSuperior2Service;
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
@Api(tags = "Controlador de autorizante superior 2")
public class AuthorizerToSuperior2Controller {

    @Autowired
    AuthorizerToSuperior2Service authorizerToSuperior2Service;

    @ApiOperation(value = "Creación de un autorizante de superior 2"
            ,notes = "Se envía objeto de tipo AuthorizerToSuperior2 a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/authorizers/to/sup2/create")
    public ResponseEntity<?> create(@Valid @RequestBody AuthorizerToSuperior2 authorizerToSuperior, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MsgInt("Alguno de los datos no coinciden con el formato esperado", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        String message = "";

        try{

            AuthorizerToSuperior2 newAuthorizer = new AuthorizerToSuperior2(
                    authorizerToSuperior.getColaborator()
            );

            authorizerToSuperior2Service.save(newAuthorizer);

            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgInt("Registro Guardado", HttpStatus.CREATED.value(), newAuthorizer.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Lista de autorizante de autorizante superior 2"
            ,notes = "Se obtiene una lista de autorizante de autorizante superior 2")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/authorizers/to/sup2/list")
    public List<AuthorizerToSuperior2> getAll(){
        List<AuthorizerToSuperior2> list = authorizerToSuperior2Service.list();
        return list;
    }

    @ApiOperation(value = "Registro de un autorizante de autorizante superior 2 por ID"
            ,notes = "Se obtiene un registro de un autorizante de autorizante superior 2 a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/authorizers/to/sup2/{id}")
    public ResponseEntity<AuthorizerToSuperior2> getById(@PathVariable("id") int id){
        AuthorizerToSuperior2 authorizerToSuperior = authorizerToSuperior2Service.getOne(id).get();
        return new ResponseEntity(authorizerToSuperior, HttpStatus.OK);
    }

    @ApiOperation(value = "Actualización de un autorizante de autorizante superior 2"
            ,notes = "Se actualiza un autorizante de autorizante superior 2 través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/authorizers/to/sup2/{id}")
    public ResponseEntity<MsgInt>  update(@PathVariable(value = "id") int id, @Valid @RequestBody AuthorizerToSuperior2 authorizerToSuperior){
        AuthorizerToSuperior2 authorizerUpdated = authorizerToSuperior2Service.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Authorizer not found for this id :: " + id));

        String message = "";

        authorizerUpdated.setColaborator(authorizerToSuperior.getColaborator());

        try{
            authorizerToSuperior2Service.save(authorizerUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(new MsgInt("El registro se ha actualizado con éxito.", HttpStatus.OK.value(), authorizerUpdated.getId()));

        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Eliminación de Autorizante de Superior 2"
            ,notes = "Se elimina un autorizante de superior 2 a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/authorizers/to/sup2/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            authorizerToSuperior2Service.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
