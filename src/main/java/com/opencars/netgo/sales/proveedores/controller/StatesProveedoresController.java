package com.opencars.netgo.sales.proveedores.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.msgs.MsgInt;
import com.opencars.netgo.sales.proveedores.entity.StatesProveedores;
import com.opencars.netgo.sales.proveedores.service.StatesProveedoresService;
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
@Api(tags = "Controlador de Estados de Módulo Compras-Proveedores")
public class StatesProveedoresController {

    @Autowired
    StatesProveedoresService statesProveedoresService;

    @ApiOperation(value = "Lista de estados de compras-proveedores"
            ,notes = "Se obtiene una lista de conceptos de compras-proveedores")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/proveedores/states/list")
    public List<StatesProveedores> getAll(){
        List<StatesProveedores> list = statesProveedoresService.list();
        return list;
    }

    @ApiOperation(value = "Estado de compras-proveedores por ID"
            ,notes = "Se obtiene un estado de compras-proveedores a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/proveedores/states/{id}")
    public ResponseEntity<StatesProveedores> getById(@PathVariable("id") int id){
        StatesProveedores statesProveedores = statesProveedoresService.getOne(id).get();
        return new ResponseEntity(statesProveedores, HttpStatus.OK);
    }

    @ApiOperation(value = "Creación de un estado de módulo compras-proveedores"
            ,notes = "Se envía objeto de tipo StatesProveedores a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/proveedores/states/create")
    public ResponseEntity<?> create(@Valid @RequestBody StatesProveedores statesProveedores, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MsgInt("Alguno de los datos no coinciden con el formato esperado", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        String message = "";

        try{

            StatesProveedores newState = new StatesProveedores(
                    statesProveedores.getId(),
                    statesProveedores.getState()
            );

            statesProveedoresService.save(newState);

            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgInt("Registro Guardado", HttpStatus.CREATED.value(), newState.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de un una estado de módulo compras-proveedores"
            ,notes = "Se actualiza un estado de compras-proveedores a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/proveedores/states/{id}")
    public ResponseEntity<MsgInt>  update(@PathVariable(value = "id") int id, @Valid @RequestBody StatesProveedores statesProveedores){
        StatesProveedores stateUpdated = statesProveedoresService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("State not found for this id :: " + id));

        String message = "";

        stateUpdated.setState(statesProveedores.getState());

        try{
            statesProveedoresService.save(stateUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(new MsgInt("El registro se ha actualizado con éxito.", HttpStatus.OK.value(), stateUpdated.getId()));

        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Eliminación de un estado de compras-proveedores por ID"
            ,notes = "Se elimina un registro a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/proveedores/states/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            statesProveedoresService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
