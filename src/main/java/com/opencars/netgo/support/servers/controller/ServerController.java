package com.opencars.netgo.support.servers.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.servers.entity.Server;
import com.opencars.netgo.support.servers.service.ServerService;
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
@RequestMapping("/api/servers")
@CrossOrigin
@Api(tags = "Controlador de Servidores")
public class ServerController {

    @Autowired
    ServerService serverService;

    @ApiOperation(value = "Creación de un Servidor"
            ,notes = "Se envía un objeto de tipo Server a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Server server, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);

        Server newServer =
                new Server(
                        server.getName(),
                        server.getIp(),
                        server.getUser(),
                        server.getPassword(),
                        server.getOperativeSystem(),
                        server.getAccessType(),
                        server.getUbication(),
                        server.getNote()
                );

        serverService.save(newServer);
        return new ResponseEntity(newServer, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Actualización de un Servidor"
            ,notes = "Se actualiza la información de un servidor")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/server/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Server server){
        Server serverUpdated = serverService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Server not found for this id :: " + id));;
        serverUpdated.setName(server.getName());
        serverUpdated.setIp(server.getIp());
        serverUpdated.setUser(server.getUser());
        serverUpdated.setPassword(server.getPassword());
        serverUpdated.setOperativeSystem(server.getOperativeSystem());
        serverUpdated.setAccessType(server.getAccessType());
        serverUpdated.setUbication(server.getUbication());
        serverUpdated.setNote(server.getNote());

        serverService.save(serverUpdated);

        return ResponseEntity.ok(serverUpdated);
    }

    @ApiOperation(value = "Lista de Servidores"
            ,notes = "Se obtiene una lista de servidores")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/list")
    public List<Server> getList(){

        List<Server> list = serverService.getList();

        return list;
    }

    @ApiOperation(value = "Lista de Servidores por coincidencia en nombre"
            ,notes = "Se obtiene una lista de servidores por coincidencia en el nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/name/{name}")
    public List<Server> getByCoincidence(@PathVariable("name") String name){

        List<Server> servers = serverService.getByCoincidence(name);
        return servers;
    }

    @ApiOperation(value = "Eliminación de un servidor por ID"
            ,notes = "Se elimina un servidor a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('ADMINIT')")
    @DeleteMapping("/server/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            serverService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Servidor Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

}
