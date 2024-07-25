package com.opencars.netgo.support.pcs.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.pcs.entity.Brands;
import com.opencars.netgo.support.pcs.service.BrandsService;
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
@RequestMapping("/api/pcs")
@CrossOrigin
@Api(tags = "Controlador de Marcas de Pcs")
public class BrandsController {

    @Autowired
    BrandsService brandsService;

    @ApiOperation(value = "Creación de una Marca de Pc"
            ,notes = "Se envía objeto de tipo Brands a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/brands")
    public ResponseEntity<?> create(@Valid @RequestBody Brands brands, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        Brands newBrand =
                new Brands(

                        brands.getName()
                );
        brandsService.save(newBrand);

        return new ResponseEntity(new Msg("Marca Guardada"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Lista de Marcas de Pc"
            ,notes = "Se obtiene una lista de marcas de pc sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/brands")
    public List<Brands> getBrands(){

        List <Brands> list = brandsService.getAll();

        return list;
    }

    @ApiOperation(value = "Actualización de una Marca de Pc"
            ,notes = "Se actualiza la información de una marca de pc")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/brands/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Brands brands){
        Brands brandUpdated = brandsService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + id));;
        brandUpdated.setName(brands.getName());
        brandsService.save(brandUpdated);

        return ResponseEntity.ok(brandUpdated);
    }

    @ApiOperation(value = "Marca de Pc por ID"
            ,notes = "Se obtiene una marca de pc a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/brands/{id}")
    public ResponseEntity<Brands> getById(@PathVariable("id") int id){
        if(!brandsService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        Brands brands = brandsService.getOne(id).get();
        return new ResponseEntity(brands, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Marcas de Pc por Nombre"
            ,notes = "Se obtiene una lista de marcas de pc por coincidencia en el nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/brands/name/{name}")
    public List<Brands> getBrandsByName(@PathVariable("name") String name){

        List<Brands> list = brandsService.getByName(name);

        return list;
    }
}
