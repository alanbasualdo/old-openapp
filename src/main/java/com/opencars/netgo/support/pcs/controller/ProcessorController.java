package com.opencars.netgo.support.pcs.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.pcs.entity.Processor;
import com.opencars.netgo.support.pcs.service.ProcessorService;
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
@Api(tags = "Controlador de Procesadores de Pcs")
public class ProcessorController {

    @Autowired
    ProcessorService processorService;

    @ApiOperation(value = "Creación de un Procesador de Pc"
            ,notes = "Se envía objeto de tipo processor a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/processor")
    public ResponseEntity<?> create(@Valid @RequestBody Processor processor, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        Processor newProcessor =
                new Processor(

                        processor.getModel()
                );
        processorService.save(newProcessor);

        return new ResponseEntity(new Msg("Procesador Guardado"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Lista de Procesadores"
            ,notes = "Se obtiene una lista de procesadores sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/processor")
    public List<Processor> getProcessors(){

        List <Processor> list = processorService.getAll();

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
    @PutMapping("/processor/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Processor processor){
        Processor processorUpdated = processorService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Memory not found for this id :: " + id));;
        processorUpdated.setModel(processor.getModel());
        processorService.save(processorUpdated);

        return ResponseEntity.ok(processorUpdated);
    }

    @ApiOperation(value = "Procesador de Pc por ID"
            ,notes = "Se obtiene un procesador de pc a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/processor/{id}")
    public ResponseEntity<Processor> getById(@PathVariable("id") int id){
        if(!processorService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        Processor processor = processorService.getOne(id).get();
        return new ResponseEntity(processor, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Procesadores de Pc por Nombre"
            ,notes = "Se obtiene una lista de procesadores de pc por coincidencia en el nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/processor/name/{name}")
    public List<Processor> getProcessorByName(@PathVariable("name") String name){

        List<Processor> list = processorService.getByName(name);

        return list;
    }
}
