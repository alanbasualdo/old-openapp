package com.opencars.netgo.support.mobiles.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.mobiles.entity.BrandsMobiles;
import com.opencars.netgo.support.mobiles.service.BrandsMobilesService;
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
@RequestMapping("/api/brandsmobiles")
@CrossOrigin
@Api(tags = "Controlador de Marcas de Celulares")
public class BrandsMobilesController {

    @Autowired
    BrandsMobilesService brandsMobilesService;

    @ApiOperation(value = "Creación de una Marca de Celular"
            ,notes = "Se envía objeto de tipo BrandsMobiles a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/brands")
    public ResponseEntity<?> create(@Valid @RequestBody BrandsMobiles brands, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        BrandsMobiles newBrand =
                new BrandsMobiles(
                        brands.getName()
                );
        brandsMobilesService.save(newBrand);

        return new ResponseEntity(new Msg("Marca Guardada"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Lista de Marcas de Celulares"
            ,notes = "Se obtiene una lista de marcas de celulares sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/brands")
    public List<BrandsMobiles> getBrands(){

        List<BrandsMobiles> list = brandsMobilesService.getAll();

        return list;
    }

    @ApiOperation(value = "Actualización de una Marca de Celulares"
            ,notes = "Se actualiza la información de una marca de celulares")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/brands/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody BrandsMobiles brands){
        BrandsMobiles brandUpdated = brandsMobilesService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found for this id :: " + id));;
        brandUpdated.setName(brands.getName());
        brandsMobilesService.save(brandUpdated);

        return ResponseEntity.ok(brandUpdated);
    }

    @ApiOperation(value = "Marca de celular por ID"
            ,notes = "Se obtiene una marca de un celular a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/brands/{id}")
    public ResponseEntity<BrandsMobiles> getById(@PathVariable("id") int id){
        if(!brandsMobilesService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        BrandsMobiles brands = brandsMobilesService.getOne(id).get();
        return new ResponseEntity(brands, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Marcas de Celulares por Nombre"
            ,notes = "Se obtiene una lista de marcas de celulares por coincidencia en el nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/brands/name/{name}")
    public List<BrandsMobiles> getBrandsByName(@PathVariable("name") String name){

        List<BrandsMobiles> list = brandsMobilesService.getByName(name);

        return list;
    }
}
