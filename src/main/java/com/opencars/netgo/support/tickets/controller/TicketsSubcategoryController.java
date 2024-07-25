package com.opencars.netgo.support.tickets.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.tickets.entity.TicketsCategories;
import com.opencars.netgo.support.tickets.entity.TicketsSubcategories;
import com.opencars.netgo.support.tickets.service.TicketsCategoriesService;
import com.opencars.netgo.support.tickets.service.TicketsSubCategoryService;
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
@Api(tags = "Controlador de Subcategoría de Tickets")
public class TicketsSubcategoryController {

    @Autowired
    TicketsSubCategoryService ticketsSubCategoryService;

    @Autowired
    TicketsCategoriesService ticketsCategoriesService;

    @ApiOperation(value = "Creación de una Subcategoría de Ticket"
            ,notes = "Se envía objeto de tipo TicketsSubCategories a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/tickets/subcategories")
    public ResponseEntity<?> create(@Valid @RequestBody TicketsSubcategories ticketsSubcategories, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        TicketsSubcategories newTSubCategory =
                new TicketsSubcategories(

                        ticketsSubcategories.getSubCategory(),
                        ticketsSubcategories.getCategory()
                );
        ticketsSubCategoryService.save(newTSubCategory);

        return new ResponseEntity(new Msg("Subcategoría Guardada"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Lista de SubCategorías de Tickets"
            ,notes = "Se obtiene una lista de subcategorías de tickets")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tickets/subcategories")
    public List<TicketsSubcategories> getTicketsSubCategories(){

        List<TicketsSubcategories> list = ticketsSubCategoryService.getAll();

        return list;
    }

    @ApiOperation(value = "Actualización de una Subcategoría de Ticket"
            ,notes = "Se actualiza la información de una subcategoría")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/tickets/subcategories/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody TicketsSubcategories ticketsSubcategories){
        TicketsSubcategories tsubcategoryUpdated = ticketsSubCategoryService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + id));;

            System.out.println(ticketsSubcategories.getSubCategory());
        System.out.println(ticketsSubcategories.getCategory());
        System.out.println(ticketsSubcategories.getId());
        tsubcategoryUpdated.setSubCategory(ticketsSubcategories.getSubCategory());
        tsubcategoryUpdated.setCategory(ticketsSubcategories.getCategory());

        ticketsSubCategoryService.save(tsubcategoryUpdated);

        return ResponseEntity.ok(tsubcategoryUpdated);
    }

    @ApiOperation(value = "Subcategoría de Ticket por ID"
            ,notes = "Se obtiene una subcategoría de ticket a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tickets/subcategories/{id}")
    public ResponseEntity<TicketsSubcategories> getById(@PathVariable("id") int id){
        if(!ticketsSubCategoryService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        TicketsSubcategories ticketsSubcategories = ticketsSubCategoryService.getOne(id).get();
        return new ResponseEntity(ticketsSubcategories, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de SubCategorías de Tickets por Nombre de Subcategoría"
            ,notes = "Se obtiene una lista de subcategorías por coincidencia en el nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/tickets/subcategories/name/{name}")
    public List<TicketsSubcategories> getSubcategoryByName(@PathVariable("name") String name){

        List<TicketsSubcategories> list = ticketsSubCategoryService.getByName(name);

        return list;
    }

    @ApiOperation(value = "Lista de SubCategorías de Tickets para una Categoría"
            ,notes = "Se obtiene una lista de subcategorías de tickets, disponible para una categoría")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tickets/category/{category}/subcategories")
    public List<TicketsSubcategories> getSubcategoriesForCategory(@PathVariable("category") TicketsCategories category){

        List<TicketsSubcategories> list = ticketsSubCategoryService.getByCategory(category);

        return list;
    }

}
