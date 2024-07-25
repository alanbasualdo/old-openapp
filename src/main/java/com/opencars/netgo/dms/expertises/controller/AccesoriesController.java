package com.opencars.netgo.dms.expertises.controller;

import com.opencars.netgo.dms.expertises.entity.Accesories;
import com.opencars.netgo.dms.expertises.entity.Missing;
import com.opencars.netgo.dms.expertises.service.AccesoriesService;
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
@RequestMapping("/api/accesories")
@CrossOrigin()
@Api(tags = "Controlador de accesorios de vehículos")
public class AccesoriesController {

    @Autowired
    AccesoriesService accesoriesService;

    @ApiOperation(value = "Creación de Accesorio"
            ,notes = "Se envía un accesorio a través de un payload y devuelve como resultado un objeto de tipo accesories")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('SUPERVISORPERITAJES')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Accesories accesorie, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        Accesories newAccesorie =
                new Accesories(
                        accesorie.getAccesorie()
                );
        accesoriesService.save(newAccesorie);

        return new ResponseEntity(new Msg("Faltante Guardado"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Actualización de un Accesorio"
            ,notes = "Se actualiza la información de un accesorio")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('SUPERVISORPERITAJES')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Accesories accesorie){
        Accesories accesorieUpdated = accesoriesService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Accesorie not found for this id :: " + id));;
        accesorieUpdated.setAccesorie(accesorie.getAccesorie());
        accesoriesService.save(accesorieUpdated);

        return ResponseEntity.ok(accesorieUpdated);
    }

    @ApiOperation(value = "Lista de accesorios de autos"
            ,notes = "Se obtiene una lista de accesorios de autos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list")
    public List<Accesories> getAll(){
        List<Accesories> list = accesoriesService.findAll();
        return list;
    }

    @ApiOperation(value = "Accesorio por ID"
            ,notes = "Se obtiene un accesorio específico a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/id/{id}")
    public ResponseEntity<Missing> getById(@PathVariable("id") int id){
        if(!accesoriesService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        Accesories accesorie = accesoriesService.getOne(id).get();
        return new ResponseEntity(accesorie, HttpStatus.OK);
    }

    @ApiOperation(value = "Eliminación de un accesorio, por ID"
            ,notes = "Se elimina un accesorio, a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('SUPERVISORPERITAJES')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            accesoriesService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
