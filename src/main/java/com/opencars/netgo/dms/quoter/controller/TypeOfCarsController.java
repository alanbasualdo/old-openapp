package com.opencars.netgo.dms.quoter.controller;

import com.opencars.netgo.dms.quoter.entity.TypeOfCars;
import com.opencars.netgo.dms.quoter.service.TypeOfCarsService;
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
@Api(tags = "Controlador de Tabla de Tipo de Vehículos")
public class TypeOfCarsController {

    @Autowired
    TypeOfCarsService typeOfCarsService;

    @ApiOperation(value = "Lista de registros de tabla por tipo de vehículos"
            ,notes = "Se obtiene una lista de los registros de tipos de vehículos para cotizador de VO")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cars/types/list")
    public List<TypeOfCars> getAll(){
        List<TypeOfCars> list = typeOfCarsService.list();
        return list;
    }

    @ApiOperation(value = "Registro de tabla por tipo de vehículo, por ID"
            ,notes = "Se obtiene un registro de la tabla de tipo de vehículos a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cars/types/{id}")
    public ResponseEntity<TypeOfCars> getById(@PathVariable("id") int id){
        TypeOfCars typeOfCars = typeOfCarsService.getOne(id).get();
        return new ResponseEntity(typeOfCars, HttpStatus.OK);
    }

    @ApiOperation(value = "Creación de un registro la tabla de tipo de vehículos"
            ,notes = "Se envía objeto de tipo TypeOfCars a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/cars/types/create")
    public ResponseEntity<?> create(@Valid @RequestBody TypeOfCars typeOfCars, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MsgInt("Alguno de los datos no coinciden con el formato esperado", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        String message = "";

        try{

            TypeOfCars newTypeOfCars = new TypeOfCars(
                    typeOfCars.getType(),
                    typeOfCars.getPercent()
            );

            typeOfCarsService.save(newTypeOfCars);

            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgInt("Registro Guardado", HttpStatus.CREATED.value(), newTypeOfCars.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de un registro de tipo de vehículo"
            ,notes = "Se actualiza un registro de tipo de vehículo, a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/cars/types/{id}")
    public ResponseEntity<MsgInt>  update(@PathVariable(value = "id") int id, @Valid @RequestBody TypeOfCars typeOfCars){
        TypeOfCars typeOfCarsUpdated = typeOfCarsService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("TypeOfCars not found for this id :: " + id));

        String message = "";

        typeOfCarsUpdated.setType(typeOfCars.getType());
        typeOfCarsUpdated.setPercent(typeOfCars.getPercent());

        try{
            typeOfCarsService.save(typeOfCarsUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(new MsgInt("El registro se ha actualizado con éxito.", HttpStatus.OK.value(), typeOfCarsUpdated.getId()));

        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Eliminación de un registro de tipo de vehículo, por ID"
            ,notes = "Se elimina un registro de tipo de vehículo, a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('ADMINCOTIZADORVO')")
    @DeleteMapping("/cars/types/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            typeOfCarsService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
