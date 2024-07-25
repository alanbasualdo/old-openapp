package com.opencars.netgo.support.tickets.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.tickets.entity.TicketsStates;
import com.opencars.netgo.support.tickets.service.TicketsStatesService;
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
@Api(tags = "Controlador de Estados de Tickets")
public class TicketsStatesController {

    @Autowired
    TicketsStatesService ticketsStatesService;

    @ApiOperation(value = "Creación de un Estado de Ticket"
            ,notes = "Se envía objeto de tipo TicketsState a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/tickets/states")
    public ResponseEntity<?> create(@Valid @RequestBody TicketsStates ticketsStates, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        TicketsStates newTState =
                new TicketsStates(

                        ticketsStates.getState()
                );
        ticketsStatesService.save(newTState);

        return new ResponseEntity(new Msg("Categoría Guardada"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Lista de Estados de Ticket"
            ,notes = "Se obtiene una lista con los distintos estados posibles para un ticket")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tickets/states")
    public List<TicketsStates> getTicketsStates(){

        List<TicketsStates> list = ticketsStatesService.getAll();

        return list;
    }

    @ApiOperation(value = "Actualización de un Estado de Ticket"
            ,notes = "Se actualiza la información de un estado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/tickets/states/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody TicketsStates ticketsStates){
        TicketsStates tstateUpdated = ticketsStatesService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + id));;
        tstateUpdated.setState(ticketsStates.getState());

        ticketsStatesService.save(tstateUpdated);

        return ResponseEntity.ok(tstateUpdated);
    }

    @ApiOperation(value = "Estado de Ticket por ID"
            ,notes = "Se obtiene un estado de ticket a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tickets/states/{id}")
    public ResponseEntity<TicketsStates> getById(@PathVariable("id") int id){
        if(!ticketsStatesService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        TicketsStates ticketsStates = ticketsStatesService.getOne(id).get();
        return new ResponseEntity(ticketsStates, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Estados de Tickets por Nombre de Estado"
            ,notes = "Se obtiene una lista de estados por coincidencia en el nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/tickets/states/name/{name}")
    public List<TicketsStates> getStateByName(@PathVariable("name") String name){

        List<TicketsStates> list = ticketsStatesService.getByName(name);

        return list;
    }
}
