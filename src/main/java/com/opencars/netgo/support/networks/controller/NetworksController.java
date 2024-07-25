package com.opencars.netgo.support.networks.controller;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.networks.entity.Networks;
import com.opencars.netgo.support.networks.service.NetworksService;
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
@RequestMapping("/api/networks")
@CrossOrigin
@Api(tags = "Controlador de Redes")
public class NetworksController {

    @Autowired
    NetworksService networksService;

    @ApiOperation(value = "Creación de una Red"
            ,notes = "Se envía objeto de tipo Networks a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/network")
    public ResponseEntity<?> create(@Valid @RequestBody Networks network, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        Networks newNetwork =
                new Networks(
                        network.getBranch(),
                        network.getNetwork(),
                        network.getPassword(),
                        network.getDevice()
                );
        networksService.save(newNetwork);

        return new ResponseEntity(new Msg("Red Guardada"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Lista de Redes"
            ,notes = "Se obtiene una lista de redes sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list")
    public List<Networks> getNetworks(){

        List<Networks> list = networksService.getAll();

        return list;
    }

    @ApiOperation(value = "Actualización de una Red"
            ,notes = "Se actualiza la información de una red")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/network/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Networks network){
        Networks networkUpdated = networksService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Network not found for this id :: " + id));;

        networkUpdated.setBranch(network.getBranch());
        networkUpdated.setNetwork(network.getNetwork());
        networkUpdated.setPassword(network.getPassword());
        networkUpdated.setDevice(network.getDevice());
        networksService.save(networkUpdated);

        return ResponseEntity.ok(networkUpdated);
    }

    @ApiOperation(value = "Red por ID"
            ,notes = "Se obtiene una red a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/network/{id}")
    public ResponseEntity<Networks> getById(@PathVariable("id") int id){
        if(!networksService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        Networks network = networksService.getOne(id).get();
        return new ResponseEntity(network, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Redes por Coincidencia"
            ,notes = "Se obtiene una lista de redes por coincidencia")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/network/coincidence/{network}")
    public List<Networks> getNetworkTypeByCoincidence(@PathVariable("network") String network){

        List<Networks> list = networksService.getByCoincidence(network);

        return list;
    }

    @ApiOperation(value = "Lista de Redes por Sucursal"
            ,notes = "Se obtiene una lista de redes por sucursal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/network/branch/{branch}")
    public List<Networks> getNetworkByBranch(@PathVariable("branch") Branch branch){

        List<Networks> list = networksService.getByBranch(branch.getId());

        return list;
    }
}
