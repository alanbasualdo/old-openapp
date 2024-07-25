package com.opencars.netgo.support.tickets.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.tickets.entity.TicketsCategories;
import com.opencars.netgo.support.tickets.service.TicketsCategoriesService;
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
@Api(tags = "Controlador de Categorías de Tickets")
public class TicketsCategoriesController {

    @Autowired
    TicketsCategoriesService ticketsCategoriesService;

    @ApiOperation(value = "Creación de una Categoría de Ticket"
            ,notes = "Se envía objeto de tipo TicketCategories a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/tickets/categories")
    public ResponseEntity<?> create(@Valid @RequestBody TicketsCategories ticketsCategories, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        TicketsCategories newTCategory =
                new TicketsCategories(

                        ticketsCategories.getCategory(),
                        ticketsCategories.getRol()
                );
        ticketsCategoriesService.save(newTCategory);

        return new ResponseEntity(new Msg("Categoría Guardada"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Lista de Categorías de Tickets"
            ,notes = "Se obtiene una lista de categorías de tickets")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tickets/categories")
    public List<TicketsCategories> getTicketsCategories(){

        List<TicketsCategories> list = ticketsCategoriesService.getAll();

        return list;
    }

    @ApiOperation(value = "Actualización de una Categoría de Ticket"
            ,notes = "Se actualiza la información de una categoría")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/tickets/categories/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody TicketsCategories ticketsCategories){
        TicketsCategories tcategoryUpdated = ticketsCategoriesService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + id));;
        tcategoryUpdated.setCategory(ticketsCategories.getCategory());
        tcategoryUpdated.setRol(ticketsCategories.getRol());

        ticketsCategoriesService.save(tcategoryUpdated);

        return ResponseEntity.ok(tcategoryUpdated);
    }

    @ApiOperation(value = "Categoría de Ticket por ID"
            ,notes = "Se obtiene una categoría de ticket a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tickets/categories/{id}")
    public ResponseEntity<TicketsCategories> getById(@PathVariable("id") int id){
        if(!ticketsCategoriesService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        TicketsCategories ticketsCategories = ticketsCategoriesService.getOne(id).get();
        return new ResponseEntity(ticketsCategories, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Categorías de Tickets por Nombre de Categoría"
            ,notes = "Se obtiene una lista de categorías por coincidencia en el nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tickets/categories/name/{name}")
    public List<TicketsCategories> getCategoryByName(@PathVariable("name") String name){

        List<TicketsCategories> list = ticketsCategoriesService.getByName(name);

        return list;
    }
}
