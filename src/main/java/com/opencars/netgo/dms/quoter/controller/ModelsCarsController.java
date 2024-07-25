package com.opencars.netgo.dms.quoter.controller;

import com.opencars.netgo.dms.quoter.entity.ModelsCars;
import com.opencars.netgo.dms.quoter.service.ModelsCarsService;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.msgs.MsgInt;
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
@CrossOrigin()
@Api(tags = "Controlador de Tabla de Porcentajes por modelo de vegículo para cotizador")
public class ModelsCarsController {

    @Autowired
    ModelsCarsService modelsCarsService;

    @ApiOperation(value = "Lista de registros de porcentajes por modelo de vehículo"
            ,notes = "Se obtiene una lista de porcentajes de descuento por modelo de vehículo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cars/models/list")
    public List<ModelsCars> getAll(){
        List<ModelsCars> list = modelsCarsService.list();
        return list;
    }

    @ApiOperation(value = "Registro de porcentaje de descuento por modelo de vehículo, por ID"
            ,notes = "Se obtiene un registro de descuento por modelo del vehículo a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cars/models/{id}")
    public ResponseEntity<ModelsCars> getById(@PathVariable("id") int id){
        ModelsCars modelsCars = modelsCarsService.getOne(id).get();
        return new ResponseEntity(modelsCars, HttpStatus.OK);
    }

    @ApiOperation(value = "Creación de un registro de descuento por modelo del vehículo"
            ,notes = "Se envía objeto de tipo ModelsCars a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/cars/models/create")
    public ResponseEntity<?> create(@Valid @RequestBody ModelsCars modelsCars, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MsgInt("Alguno de los datos no coinciden con el formato esperado", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        String message = "";

        try{

            ModelsCars newModel = new ModelsCars(
                    modelsCars.getCodia(),
                    modelsCars.getDescription(),
                    modelsCars.getPercent()
            );

            modelsCarsService.save(newModel);

            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgInt("Registro Guardado", HttpStatus.CREATED.value(), newModel.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de un registro de descuento por modelo del vehículo"
            ,notes = "Se actualiza un registro de descuento por modelo del vehículo a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/cars/models/{id}")
    public ResponseEntity<MsgInt>  update(@PathVariable(value = "id") int id, @Valid @RequestBody ModelsCars modelsCars){
        ModelsCars modelUpdated = modelsCarsService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("ModelsCars not found for this id :: " + id));

        String message = "";

        modelUpdated.setPercent(modelsCars.getPercent());

        try{
            modelsCarsService.save(modelUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(new MsgInt("El registro se ha actualizado con éxito.", HttpStatus.OK.value(), modelUpdated.getId()));

        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Porcentaje según el modelo del vehículo"
            ,notes = "Se obtiene un porcentaje según el modelo del vehículo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cars/models/description/{description}")
    public ResponseEntity<ModelsCars> getPercentForModel(@PathVariable("description") String description){
        ModelsCars modelsCars = modelsCarsService.getPercentForModel(description).get();
        return new ResponseEntity(modelsCars, HttpStatus.OK);
    }

    @ApiOperation(value = "Eliminación de un registro de porcentaje por modelo del vehículo, por ID"
            ,notes = "Se elimina un registro de porcentaje por modelo del vehículo, a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('ADMINCOTIZADORVO')")
    @DeleteMapping("/cars/models/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            modelsCarsService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
