package com.opencars.netgo.sales.proveedores.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.msgs.MsgInt;
import com.opencars.netgo.sales.proveedores.entity.AuthorizersAmounts;
import com.opencars.netgo.sales.proveedores.entity.ConceptsProviders;
import com.opencars.netgo.sales.proveedores.entity.SubcateorizedConcepts;
import com.opencars.netgo.sales.proveedores.service.AuthorizersAmountsService;
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
@Api(tags = "Controlador de Autorizantes designados por monto, de cuentas contables")
public class AuthorizersAmountsController {

    @Autowired
    AuthorizersAmountsService authorizersAmountsService;

    @ApiOperation(value = "Lista de autorizantes designados"
            ,notes = "Se obtiene una lista de autorizantes designados según monto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/authorizers/providers/amounts/list")
    public List<AuthorizersAmounts> getAll(){
        List<AuthorizersAmounts> list = authorizersAmountsService.list();
        return list;
    }

    @ApiOperation(value = "Registro de autorizante por ID"
            ,notes = "Se obtiene un registro de autorizante designado por monto a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/authorizers/providers/amounts/{id}")
    public ResponseEntity<AuthorizersAmounts> getById(@PathVariable("id") int id){
        AuthorizersAmounts authorizersAmounts = authorizersAmountsService.getOne(id).get();
        return new ResponseEntity(authorizersAmounts, HttpStatus.OK);
    }

    @ApiOperation(value = "Creación de un registro de autorizante designado por monto"
            ,notes = "Se envía objeto de tipo AuthorizersAmounts a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/authorizers/providers/amounts/create")
    public ResponseEntity<?> create(@Valid @RequestBody AuthorizersAmounts authorizersAmounts, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MsgInt("Alguno de los datos no coinciden con el formato esperado", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        String message = "";

        try{

            AuthorizersAmounts newAuthorizer = new AuthorizersAmounts(
                    authorizersAmounts.getInitAmount(),
                    authorizersAmounts.getEndAmount(),
                    authorizersAmounts.getAuthorizer(),
                    authorizersAmounts.getConcept()
            );

            authorizersAmountsService.save(newAuthorizer);

            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgInt("Registro Guardado", HttpStatus.CREATED.value(), newAuthorizer.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de un autorizante designado por monto"
            ,notes = "Se actualiza un autorizante designado a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/authorizers/providers/amounts/{id}")
    public ResponseEntity<MsgInt>  update(@PathVariable(value = "id") int id, @Valid @RequestBody AuthorizersAmounts authorizersAmounts){
        AuthorizersAmounts authorizerUpdated = authorizersAmountsService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Authorizer not found for this id :: " + id));

        String message = "";

        authorizerUpdated.setInitAmount(authorizersAmounts.getInitAmount());
        authorizerUpdated.setEndAmount(authorizersAmounts.getEndAmount());
        authorizerUpdated.setAuthorizer(authorizersAmounts.getAuthorizer());
        authorizerUpdated.setConcept(authorizersAmounts.getConcept());

        try{
            authorizersAmountsService.save(authorizerUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(new MsgInt("El registro se ha actualizado con éxito.", HttpStatus.OK.value(), authorizerUpdated.getId()));

        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    public ResponseEntity<AuthorizersAmounts> getAuthorizerForConceptAndAmount(long amount, SubcateorizedConcepts concept){
        AuthorizersAmounts authorizersAmounts = authorizersAmountsService.getAuthorizerForSubConceptAndAmount(amount, concept).get();
        return new ResponseEntity(authorizersAmounts, HttpStatus.OK);
    }

    @ApiOperation(value = "Eliminación de un registro de autorizante designado por ID"
            ,notes = "Se elimina un registro a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/authorizers/providers/amounts/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            authorizersAmountsService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
