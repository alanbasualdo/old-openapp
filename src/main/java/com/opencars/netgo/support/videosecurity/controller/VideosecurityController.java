package com.opencars.netgo.support.videosecurity.controller;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.videosecurity.entity.Videosecurity;
import com.opencars.netgo.support.videosecurity.service.VideosecurityService;
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
@RequestMapping("/api/videosecurity")
@CrossOrigin
@Api(tags = "Controlador de Videovigilancia")
public class VideosecurityController {

    @Autowired
    VideosecurityService videosecurityService;

    @ApiOperation(value = "Creación de un registro de Monitoreo de Seguridad"
            ,notes = "Se envía un objeto de tipo Videosecurity a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Videosecurity videosecurity, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);

        Videosecurity newVideosecurity =
                new Videosecurity(
                        videosecurity.getBranch(),
                        videosecurity.getSn(),
                        videosecurity.getUser(),
                        videosecurity.getPassword(),
                        videosecurity.getIp(),
                        videosecurity.getPort(),
                        videosecurity.getNote()
                );

        videosecurityService.save(newVideosecurity);
        return new ResponseEntity(newVideosecurity, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Actualización de un registro de Monitoreo de Seguridad"
            ,notes = "Se actualiza la información de un registro de Monitoreo de Seguridad")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/access/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Videosecurity videosecurity){
        Videosecurity videosecurityUpdated = videosecurityService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Videosecurity not found for this id :: " + id));;
        videosecurityUpdated.setBranch(videosecurity.getBranch());
        videosecurityUpdated.setSn(videosecurity.getSn());
        videosecurityUpdated.setUser(videosecurity.getUser());
        videosecurityUpdated.setPassword(videosecurity.getPassword());
        videosecurityUpdated.setIp(videosecurity.getIp());
        videosecurityUpdated.setPort(videosecurity.getPort());
        videosecurityUpdated.setNote(videosecurity.getNote());

        videosecurityService.save(videosecurityUpdated);

        return ResponseEntity.ok(videosecurityUpdated);
    }

    @ApiOperation(value = "Lista de Registros de Monitoreo de Seguridad"
            ,notes = "Se obtiene una lista de registros de Monitoreo de Seguridad")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/list")
    public List<Videosecurity> getList(){

        List<Videosecurity> list = videosecurityService.getList();

        return list;
    }

    @ApiOperation(value = "Lista de Registros de Monitoreo de Seguridad por Sucursal"
            ,notes = "Se obtiene una lista de registros de Monitoreo de Seguridad por Sucursal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/branch/{branch}")
    public List<Videosecurity> getByBranch(@PathVariable("branch") Branch branch){

        List<Videosecurity> videosecurities = videosecurityService.getByBranch(branch.getId());
        return videosecurities;
    }

    @ApiOperation(value = "Eliminación de un registro de Monitoreo de Seguridad por ID"
            ,notes = "Se elimina un registro de Monitoreo de Seguridad a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('ADMINIT')")
    @DeleteMapping("/access/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            videosecurityService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Acceso Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
