package com.opencars.netgo.support.mobiles.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.mobiles.entity.ModelsMobiles;
import com.opencars.netgo.support.mobiles.service.ModelsMobilesService;
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
@RequestMapping("/api/modelsmobiles")
@CrossOrigin
@Api(tags = "Controlador de Modelos de Celulares")
public class ModelsMobilesController {

    @Autowired
    ModelsMobilesService modelsMobilesService;

    @ApiOperation(value = "Creación de un Modelo de Celular"
            ,notes = "Se envía objeto de tipo ModelsMobiles a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/model")
    public ResponseEntity<?> create(@Valid @RequestBody ModelsMobiles model, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        ModelsMobiles newModel =
                new ModelsMobiles(
                        model.getModel(),
                        model.getBrand()
                );
        modelsMobilesService.save(newModel);

        return new ResponseEntity(new Msg("Modelo Guardado"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Lista de Modelos de Celulares"
            ,notes = "Se obtiene una lista de modelos de celulares sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/model")
    public List<ModelsMobiles> getModel(){

        List<ModelsMobiles> list = modelsMobilesService.getAll();

        return list;
    }

    @ApiOperation(value = "Actualización de un modelo de celular"
            ,notes = "Se actualiza la información de un modelo de celular")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/model/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody ModelsMobiles model){
        ModelsMobiles modelUpdated = modelsMobilesService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Model not found for this id :: " + id));;
        modelUpdated.setModel(model.getModel());
        modelUpdated.setBrand(model.getBrand());
        modelsMobilesService.save(modelUpdated);

        return ResponseEntity.ok(modelUpdated);
    }

    @ApiOperation(value = "Modelo de Celular por ID"
            ,notes = "Se obtiene un modelo de celular a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/model/{id}")
    public ResponseEntity<ModelsMobiles> getById(@PathVariable("id") int id){
        if(!modelsMobilesService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        ModelsMobiles model = modelsMobilesService.getOne(id).get();
        return new ResponseEntity(model, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Modelos de celulares por Nombre"
            ,notes = "Se obtiene una lista de modelos de celulares por coincidencia en el nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/model/name/{name}")
    public List<ModelsMobiles> getModelByName(@PathVariable("name") String name){

        List<ModelsMobiles> list = modelsMobilesService.getByName(name);

        return list;
    }
}
