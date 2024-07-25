package com.opencars.netgo.dms.expertises.controller;

import com.opencars.netgo.dms.expertises.entity.ItemsControl;
import com.opencars.netgo.dms.expertises.service.ItemsControlService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Precios de Items de Peritajes")
public class ItemsControlController {

    @Autowired
    ItemsControlService itemsControlService;

    @ApiOperation(value = "Actualización de un Registro de control de precio de item de peritaje"
            ,notes = "Se actualiza la información de un registro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('SUPERVISORPERITAJES')")
    @PutMapping("/itemcontrol/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody ItemsControl item){
        ItemsControl itemUpdated = itemsControlService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("ItemsControl not found for this id :: " + id));;
        itemUpdated.setPrice(item.getPrice());
        itemsControlService.save(itemUpdated);

        return ResponseEntity.ok(itemUpdated);
    }

    @ApiOperation(value = "Lista de registro de control de precios de items de peritajes"
            ,notes = "Se obtiene una lista de registros sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('SUPERVISORPERITAJES')")
    @GetMapping("/itemscontrol")
    public List<ItemsControl> getItemsControl(){
        List<ItemsControl> list = itemsControlService.getAll();
        return list;
    }

    @ApiOperation(value = "Registro por ID"
            ,notes = "Se obtiene un registro específico a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('SUPERVISORPERITAJES')")
    @GetMapping("/itemcontrol/{id}")
    public ResponseEntity<ItemsControl> getById(@PathVariable("id") int id){
        if(!itemsControlService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        ItemsControl itemsControl = itemsControlService.getOne(id).get();
        return new ResponseEntity(itemsControl, HttpStatus.OK);
    }
}
