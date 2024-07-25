package com.opencars.netgo.support.pcs.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.pcs.entity.Model;
import com.opencars.netgo.support.pcs.service.ModelService;
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
@Api(tags = "Controlador de Modelos de Pcs")
public class ModelController {

    @Autowired
    ModelService modelService;

    @ApiOperation(value = "Creación de un Modelo de Pc"
            ,notes = "Se envía objeto de tipo model a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/model")
    public ResponseEntity<?> create(@Valid @RequestBody Model model, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        Model newModel =
                new Model(

                        model.getModel()
                );
        modelService.save(newModel);

        return new ResponseEntity(new Msg("Modelo Guardado"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Lista de Modelos de Pc"
            ,notes = "Se obtiene una lista de modelos de pc sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/model")
    public List<Model> getModel(){

        List <Model> list = modelService.getAll();

        return list;
    }

    @ApiOperation(value = "Actualización de un modelo de Pc"
            ,notes = "Se actualiza la información de un modelo de pc")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/model/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Model model){
        Model modelUpdated = modelService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Memory not found for this id :: " + id));;
        modelUpdated.setModel(model.getModel());
        modelService.save(modelUpdated);

        return ResponseEntity.ok(modelUpdated);
    }

    @ApiOperation(value = "Modelo de Pc por ID"
            ,notes = "Se obtiene un modelo de pc a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/model/{id}")
    public ResponseEntity<Model> getById(@PathVariable("id") int id){
        if(!modelService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        Model model = modelService.getOne(id).get();
        return new ResponseEntity(model, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Modelos de Pc por Nombre"
            ,notes = "Se obtiene una lista de modelos de pc por coincidencia en el nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/model/name/{name}")
    public List<Model> getModelByName(@PathVariable("name") String name){

        List<Model> list = modelService.getByName(name);

        return list;
    }
}
