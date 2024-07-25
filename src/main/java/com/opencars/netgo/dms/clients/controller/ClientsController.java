package com.opencars.netgo.dms.clients.controller;

import ch.qos.logback.core.net.server.Client;
import com.opencars.netgo.dms.clients.entity.Clients;
import com.opencars.netgo.dms.clients.service.ClientsService;
import com.opencars.netgo.msgs.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin()
@Api(tags = "Controlador de Clientes")
public class ClientsController {

    @Autowired
    ClientsService clientsService;

    @ApiOperation(value = "Creación de un cliente"
            ,notes = "Se envía un objeto de tipo cliente a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Clients client, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        if (clientsService.existsByTelefono(client.getTelefono()))
            return new ResponseEntity(new Msg("Ya existe un cliente con ese teléfono"), HttpStatus.BAD_REQUEST);
        if (clientsService.existsByCorreo(client.getCorreo()))
            return new ResponseEntity(new Msg("Ya existe un cliente con ese correo"), HttpStatus.BAD_REQUEST);
        if (clientsService.existsByDocumento(client.getDocumento()))
            return new ResponseEntity(new Msg("Ya existe un cliente con ese documento"), HttpStatus.BAD_REQUEST);

        String message = "";
        Clients newClient =
                new Clients(
                        client.getNombre(),
                        client.getTelefono(),
                        client.getCorreo(),
                        client.getDocumento(),
                        client.getCreatedBy()
                );

        try{
            clientsService.save(newClient);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Msg("Cliente Guardado", HttpStatus.CREATED.value()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de un cliente"
            ,notes = "Se actualiza un cliente a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/client/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @Valid @RequestBody Clients client, BindingResult bindingResult){
        String message = "";
        try{
            Clients clientUpdated = clientsService.getOne(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + id));
            clientUpdated.setNombre(client.getNombre());
            clientUpdated.setTelefono(client.getTelefono());
            clientUpdated.setCorreo(client.getCorreo());
            clientUpdated.setDocumento(client.getDocumento());
            clientUpdated.setEditBy(client.getEditBy());

            clientsService.save(clientUpdated);

            return ResponseEntity.ok(clientUpdated);
        }catch (Exception e){
            if (bindingResult.hasErrors())
                return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
            if (clientsService.existsByTelefono(client.getTelefono()))
                return new ResponseEntity(new Msg("Ya existe un cliente con ese teléfono"), HttpStatus.BAD_REQUEST);
            if (clientsService.existsByCorreo(client.getCorreo()))
                return new ResponseEntity(new Msg("Ya existe un cliente con ese correo"), HttpStatus.BAD_REQUEST);
            if (clientsService.existsByDocumento(client.getDocumento()))
                return new ResponseEntity(new Msg("Ya existe un cliente con ese documento"), HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Lista de clientes"
            ,notes = "Se obtiene una lista de clientes paginada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list")
    public Page<Clients> getClients(@PageableDefault(size = 10, page = 0) Pageable pageable){

        Page<Clients> list = clientsService.getAllClients(pageable);

        return list;
    }

    @ApiOperation(value = "Cliente por ID"
            ,notes = "Se obtiene un cliente a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/client/{id}")
    public ResponseEntity<Client> getById(@PathVariable("id") Long id){
        if(!clientsService.existsById(id))
            return new ResponseEntity(new Msg("no existe"), HttpStatus.NOT_FOUND);
        Clients client = clientsService.getOne(id).get();
        return new ResponseEntity(client, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Clientes por coincidencia en Nombre"
            ,notes = "Se obtiene una lista de clientes por coincidencia en el nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/name/{name}")
    public List<Clients> getClientsByName(@PathVariable("name") String name){
        List<Clients> list = clientsService.getByName(name);
        return list;
    }
}
