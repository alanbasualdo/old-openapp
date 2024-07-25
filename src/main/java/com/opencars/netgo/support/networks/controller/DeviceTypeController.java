package com.opencars.netgo.support.networks.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.networks.entity.DeviceType;
import com.opencars.netgo.support.networks.service.DeviceTypeService;
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
@RequestMapping("/api/devicetype")
@CrossOrigin
@Api(tags = "Controlador de tipos de Dispositivos de Red")
public class DeviceTypeController {

    @Autowired
    DeviceTypeService deviceTypeService;

    @ApiOperation(value = "Creación de un tipo de Dispositivo de Red"
            ,notes = "Se envía objeto de tipo DeviceType a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/type")
    public ResponseEntity<?> create(@Valid @RequestBody DeviceType deviceType, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        DeviceType newDeviceType =
                new DeviceType(
                        deviceType.getType()
                );
        deviceTypeService.save(newDeviceType);

        return new ResponseEntity(new Msg("Tipo de dispositivo guardado"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Lista de Tipos de Dispositivos"
            ,notes = "Se obtiene una lista de tipos de dispositivos sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/types/list")
    public List<DeviceType> getTypes(){

        List<DeviceType> list = deviceTypeService.getAll();

        return list;
    }

    @ApiOperation(value = "Actualización de un tipo de Dispositivo"
            ,notes = "Se actualiza la información de un tipo de dispositivo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/types/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody DeviceType deviceType){
        DeviceType deviceUpdated = deviceTypeService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found for this id :: " + id));;
        deviceUpdated.setType(deviceType.getType());
        deviceTypeService.save(deviceUpdated);

        return ResponseEntity.ok(deviceUpdated);
    }

    @ApiOperation(value = "Tipo de Dispositivo por ID"
            ,notes = "Se obtiene un tipo de dispositivo a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/type/{id}")
    public ResponseEntity<DeviceType> getById(@PathVariable("id") int id){
        if(!deviceTypeService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        DeviceType deviceType = deviceTypeService.getOne(id).get();
        return new ResponseEntity(deviceType, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Tipos de Dispositivos por Coincidencia"
            ,notes = "Se obtiene una lista de tipos de dispositivos por coincidencia")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/type/coincidence/{type}")
    public List<DeviceType> getDeviceTypeByCoincidence(@PathVariable("type") String type){

        List<DeviceType> list = deviceTypeService.getByCoincidence(type);

        return list;
    }

}
