package com.opencars.netgo.auth.controller;

import com.opencars.netgo.auth.entity.Rol;
import com.opencars.netgo.auth.service.RolService;
import com.opencars.netgo.msgs.Msg;
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
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Roles de Usuario")
public class RolController {

    @Autowired
    RolService rolService;

    @ApiOperation(value = "Creación de Rol"
            ,notes = "Se envía nombre del rol a través de un payload y devuelve como resultado un objeto de tipo role")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/rol")
    public ResponseEntity<?> create(@Valid @RequestBody Rol role, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        Rol newRole =
                new Rol(

                        role.getRolName(),
                        role.getDescription(),
                        role.getType()
                );
        rolService.save(newRole);

        return new ResponseEntity(new Msg("Rol Guardado"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Actualización de un Rol"
            ,notes = "Se actualiza la información de un rol")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/rol/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Rol rol){
        Rol roleUpdated = rolService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found for this id :: " + id));;
        roleUpdated.setRolName(rol.getRolName());
        roleUpdated.setDescription(rol.getDescription());
        roleUpdated.setType(rol.getType());

        rolService.save(roleUpdated);

        return ResponseEntity.ok(roleUpdated);
    }

    @ApiOperation(value = "Lista de Roles"
            ,notes = "Se obtiene una lista de roles sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/rol")
    public List<Rol> getRoles(){

        List<Rol> list = rolService.getAll();

        return list;
    }

    @ApiOperation(value = "Lista de Roles de Tipo"
            ,notes = "Se obtiene una lista de roles por tipo, sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/rol/type/{type}")
    public List<Rol> getRolesByType(@PathVariable(value = "type") String type){

        List<Rol> list = rolService.getAllByType(type);

        return list;
    }

    @ApiOperation(value = "Rol por ID"
            ,notes = "Se obtiene un rol específico a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/rol/{id}")
    public ResponseEntity<Rol> getById(@PathVariable("id") int id){
        if(!rolService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        Rol role = rolService.getOne(id).get();
        return new ResponseEntity(role, HttpStatus.OK);
    }

    @ApiOperation(value = "Rol por Nombre y Tipo"
            ,notes = "Se obtiene un rol específico a través de su nombre y tipo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/rol/name/{name}/type/{type}")
    public List<Rol> getRolesByNameAndType(@PathVariable("name") String name, @PathVariable("type") String type){

        List<Rol> list = rolService.getByNameAndType(name, type);

        return list;
    }

    @ApiOperation(value = "Rol por Nombre"
            ,notes = "Se obtiene un rol específico a través de su nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/rol/name/{name}")
    public List<Rol> getRolesByName(@PathVariable("name") String name){

        List<Rol> list = rolService.getByName(name);

        return list;
    }


}
