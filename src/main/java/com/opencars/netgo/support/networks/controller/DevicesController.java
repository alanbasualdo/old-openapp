package com.opencars.netgo.support.networks.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.networks.entity.Devices;
import com.opencars.netgo.support.networks.service.DevicesService;
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
@RequestMapping("/api/devices")
@CrossOrigin
@Api(tags = "Controlador de Dispositivos de Red")
public class DevicesController {

    @Autowired
    DevicesService devicesService;

    @ApiOperation(value = "Creación de un Dispositivo de Red"
            ,notes = "Se envía objeto de tipo Device a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/device")
    public ResponseEntity<?> create(@Valid @RequestBody Devices device, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        Devices newDevice =
                new Devices(
                        device.getName(),
                        device.getType()
                );
        devicesService.save(newDevice);

        return new ResponseEntity(new Msg("Dispositivo Guardado"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Lista de Dispositivos de Red"
            ,notes = "Se obtiene una lista de dispositivos de sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list")
    public List<Devices> getDevices(){

        List<Devices> list = devicesService.getAll();

        return list;
    }

    @ApiOperation(value = "Actualización de un Dispositivo de Red"
            ,notes = "Se actualiza la información de un dispositivo de red")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/device/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Devices device){
        Devices deviceUpdated = devicesService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found for this id :: " + id));;

        deviceUpdated.setName(device.getName());
        deviceUpdated.setType(device.getType());
        devicesService.save(deviceUpdated);

        return ResponseEntity.ok(deviceUpdated);
    }

    @ApiOperation(value = "Dispositivo por ID"
            ,notes = "Se obtiene un dispositivo a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/device/{id}")
    public ResponseEntity<Devices> getById(@PathVariable("id") int id){
        if(!devicesService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        Devices device = devicesService.getOne(id).get();
        return new ResponseEntity(device, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Dispositivos por Coincidencia"
            ,notes = "Se obtiene una lista de dispositivos por coincidencia")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/device/coincidence/{device}")
    public List<Devices> getDeviceTypeByCoincidence(@PathVariable("device") String device){

        List<Devices> list = devicesService.getByCoincidence(device);

        return list;
    }
}
