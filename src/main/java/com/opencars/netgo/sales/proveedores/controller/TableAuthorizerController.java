package com.opencars.netgo.sales.proveedores.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.msgs.MsgInt;
import com.opencars.netgo.sales.proveedores.entity.TableAuthorizer;
import com.opencars.netgo.sales.proveedores.service.TableAuthorizerService;
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
@Api(tags = "Controlador de Tabla de Autorizantes de Viáticos")
public class TableAuthorizerController {

    @Autowired
    TableAuthorizerService tableAuthorizerService;

    @ApiOperation(value = "Lista de autorizantes"
            ,notes = "Se obtiene una lista de autorizantes según monto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/authorizers/list")
    public List<TableAuthorizer> getAll(){
        List<TableAuthorizer> list = tableAuthorizerService.list();
        return list;
    }

    @ApiOperation(value = "Registro de autorizante por ID"
            ,notes = "Se obtiene un registro de autorizante por monto a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/authorizers/{id}")
    public ResponseEntity<TableAuthorizer> getById(@PathVariable("id") int id){
        TableAuthorizer tableAuthorizer = tableAuthorizerService.getOne(id).get();
        return new ResponseEntity(tableAuthorizer, HttpStatus.OK);
    }

    @ApiOperation(value = "Creación de un registro de autorizante por monto"
            ,notes = "Se envía objeto de tipo TableAuthorizer a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/authorizers/create")
    public ResponseEntity<?> create(@Valid @RequestBody TableAuthorizer tableAuthorizer, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MsgInt("Alguno de los datos no coinciden con el formato esperado", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        String message = "";

        try{

            TableAuthorizer newAuthorizer = new TableAuthorizer(
                    tableAuthorizer.getInitAmount(),
                    tableAuthorizer.getEndAmount(),
                    tableAuthorizer.getAuthorizer()
            );

            tableAuthorizerService.save(newAuthorizer);

            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgInt("Registro Guardado", HttpStatus.CREATED.value(), newAuthorizer.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de un autorizante de viáticos por monto"
            ,notes = "Se actualiza un autorizante de viáticos a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/authorizers/{id}")
    public ResponseEntity<MsgInt>  update(@PathVariable(value = "id") int id, @Valid @RequestBody TableAuthorizer tableAuthorizer){
        TableAuthorizer authorizerUpdated = tableAuthorizerService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Authorizer not found for this id :: " + id));

        String message = "";

        authorizerUpdated.setInitAmount(tableAuthorizer.getInitAmount());
        authorizerUpdated.setEndAmount(tableAuthorizer.getEndAmount());
        authorizerUpdated.setAuthorizer(tableAuthorizer.getAuthorizer());

        try{
            tableAuthorizerService.save(authorizerUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(new MsgInt("El registro se ha actualizado con éxito.", HttpStatus.OK.value(), authorizerUpdated.getId()));

        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    public ResponseEntity<TableAuthorizer> getAuthorizer(@PathVariable("amount") long amount){
        TableAuthorizer tableAuthorizer = tableAuthorizerService.getAuthorizerForAmount(amount).get();
        return new ResponseEntity(tableAuthorizer, HttpStatus.OK);
    }

    @ApiOperation(value = "Eliminación de un registro de autorizante de viáticos por ID"
            ,notes = "Se elimina un registro a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/authorizers/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            tableAuthorizerService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
