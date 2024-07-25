package com.opencars.netgo.support.tickets.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.tickets.entity.TicketsSubSubCategories;
import com.opencars.netgo.support.tickets.entity.TicketsSubcategories;
import com.opencars.netgo.support.tickets.service.TicketsSubSubCategoriesService;
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
@Api(tags = "Controlador de SubSubcategoría de Tickets")
public class TicketsSubSubCategoriesController {

    @Autowired
    TicketsSubSubCategoriesService ticketsSubSubCategoriesService;

    @ApiOperation(value = "Creación de una SubSubcategoría de Ticket"
            ,notes = "Se envía objeto de tipo TicketsSubSubCategories a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/tickets/subsubcategories")
    public ResponseEntity<?> create(@Valid @RequestBody TicketsSubSubCategories ticketsSubcategories, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        TicketsSubSubCategories newTSubCategory =
                new TicketsSubSubCategories(

                        ticketsSubcategories.getSubSubCategory(),
                        ticketsSubcategories.getSubCategory()
                );
        ticketsSubSubCategoriesService.save(newTSubCategory);

        return new ResponseEntity(new Msg("Subcategoría Guardada"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Lista de SubSubCategorías de Tickets"
            ,notes = "Se obtiene una lista de subsubcategorías de tickets")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tickets/subsubcategories")
    public List<TicketsSubSubCategories> getTicketsSubCategories(){

        List<TicketsSubSubCategories> list = ticketsSubSubCategoriesService.getAll();

        return list;
    }
    @ApiOperation(value = "Actualización de una SubSubcategoría de Ticket"
            ,notes = "Se actualiza la información de una subsubcategoría")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/tickets/subsubcategories/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody TicketsSubSubCategories ticketsSubcategories){
        TicketsSubSubCategories tsubcategoryUpdated = ticketsSubSubCategoriesService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + id));;
        tsubcategoryUpdated.setSubSubCategory(ticketsSubcategories.getSubSubCategory());
        tsubcategoryUpdated.setSubCategory(ticketsSubcategories.getSubCategory());

        ticketsSubSubCategoriesService.save(tsubcategoryUpdated);

        return ResponseEntity.ok(tsubcategoryUpdated);
    }

    @ApiOperation(value = "SubSubcategoría de Ticket por ID"
            ,notes = "Se obtiene una subsubcategoría de ticket a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tickets/subsubcategories/{id}")
    public ResponseEntity<TicketsSubSubCategories> getById(@PathVariable("id") int id){
        if(!ticketsSubSubCategoriesService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        TicketsSubSubCategories ticketsSubcategories = ticketsSubSubCategoriesService.getOne(id).get();
        return new ResponseEntity(ticketsSubcategories, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de SubSubCategorías de Tickets por Nombre de SubSubcategoría"
            ,notes = "Se obtiene una lista de subsubcategorías por coincidencia en el nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/tickets/subsubcategories/name/{name}")
    public List<TicketsSubSubCategories> getSubsubcategoryByName(@PathVariable("name") String name){

        List<TicketsSubSubCategories> list = ticketsSubSubCategoriesService.getByName(name);

        return list;
    }

    @ApiOperation(value = "Lista de SubSubCategorías de Tickets para una Subcategoría"
            ,notes = "Se obtiene una lista de sub-subcategorías de tickets, disponible para una subcategoría")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tickets/subcategory/{subcategory}/subsubcategories")
    public List<TicketsSubSubCategories> getSubsubcategoriesForSubcategory(@PathVariable("subcategory") TicketsSubcategories subcategory){

        List<TicketsSubSubCategories> list = ticketsSubSubCategoriesService.getBySubcategory(subcategory);

        return list;
    }
}
