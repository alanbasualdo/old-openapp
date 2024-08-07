package com.opencars.netgo.sales.proveedores.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.msgs.MsgInt;
import com.opencars.netgo.sales.proveedores.entity.GarajeChiefs;
import com.opencars.netgo.sales.proveedores.service.GarajeChiefsService;
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
@Api(tags = "Controlador de Tabla de Jefes de Taller")
public class GarajeChiefsController {

    @Autowired
    GarajeChiefsService garajeChiefsService;

    @ApiOperation(value = "Lista de Jefes de Taller por Taller"
            ,notes = "Se obtiene una lista de jefes de taller por taller")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/garajechiefs/list")
    public List<GarajeChiefs> getAll(){
        List<GarajeChiefs> list = garajeChiefsService.list();
        return list;
    }

    @ApiOperation(value = "Jefe de Taller por Taller por ID"
            ,notes = "Se obtiene un registro de jefe de taller por taller a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/garajechiefs/{id}")
    public ResponseEntity<GarajeChiefs> getById(@PathVariable("id") int id){
        GarajeChiefs garajeChiefs = garajeChiefsService.getOne(id).get();
        return new ResponseEntity(garajeChiefs, HttpStatus.OK);
    }

    @ApiOperation(value = "Creación de un registro de jefe de taller por taller"
            ,notes = "Se envía objeto de tipo GarajeChiefs a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/garajechiefs/create")
    public ResponseEntity<?> create(@Valid @RequestBody GarajeChiefs garajeChiefs, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MsgInt("Alguno de los datos no coinciden con el formato esperado", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        String message = "";

        try{

            GarajeChiefs newGarajeChiefs = new GarajeChiefs(
                    garajeChiefs.getBranch(),
                    garajeChiefs.getChief()
            );

            garajeChiefsService.save(newGarajeChiefs);

            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgInt("Registro Guardado", HttpStatus.CREATED.value(), newGarajeChiefs.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de un jefe de taller por taller"
            ,notes = "Se actualiza un jefe de taller por taller a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/garajechiefs/{id}")
    public ResponseEntity<MsgInt>  update(@PathVariable(value = "id") int id, @Valid @RequestBody GarajeChiefs garajeChiefs){
        GarajeChiefs garajeChiefsUpdated = garajeChiefsService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("GarajeChiefs not found for this id :: " + id));

        String message = "";

        garajeChiefsUpdated.setBranch(garajeChiefs.getBranch());
        garajeChiefsUpdated.setChief(garajeChiefs.getChief());

        try{
            garajeChiefsService.save(garajeChiefsUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(new MsgInt("El registro se ha actualizado con éxito.", HttpStatus.OK.value(), garajeChiefsUpdated.getId()));

        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Eliminación de un registro de jefe de taller por taller, por ID"
            ,notes = "Se elimina un registro a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/garajechiefs/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            garajeChiefsService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

}
