package com.opencars.netgo.dms.quoter.controller;

import com.opencars.netgo.dms.quoter.entity.YearsPercents;
import com.opencars.netgo.dms.quoter.service.YearsPercentsService;
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
@Api(tags = "Controlador de Tabla de Porcentajes por año de vehículos para cotizador VO")
public class YearsPercentesController {

    @Autowired
    YearsPercentsService yearsPercentsService;

    @ApiOperation(value = "Lista de registros de porcentajes por año del vehículo"
            ,notes = "Se obtiene una lista de porcentajes a descontar según año del vehículo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cars/yearspercents/list")
    public List<YearsPercents> getAll(){
        List<YearsPercents> list = yearsPercentsService.list();
        return list;
    }

    @ApiOperation(value = "Registro de porcentaje de descuento por año de auto, por ID"
            ,notes = "Se obtiene un registro de porcentaje de descuento de un auto a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cars/yearspercents/{id}")
    public ResponseEntity<YearsPercents> getById(@PathVariable("id") int id){
        YearsPercents yearsPercents = yearsPercentsService.getOne(id).get();
        return new ResponseEntity(yearsPercents, HttpStatus.OK);
    }

    @ApiOperation(value = "Registro de porcentaje de descuento por año de auto, por año"
            ,notes = "Se obtiene un registro de porcentaje de descuento de un auto a través de su año")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cars/yearspercents/year/{year}")
    public ResponseEntity<YearsPercents> getByYear(@PathVariable("year") int year){
        YearsPercents yearsPercents = yearsPercentsService.getByYear(year).get();
        return new ResponseEntity(yearsPercents, HttpStatus.OK);
    }

    @ApiOperation(value = "Creación de un registro de porcentaje de descuento de un auto por año"
            ,notes = "Se envía objeto de tipo YearsPercents a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/cars/yearspercents/create")
    public ResponseEntity<?> create(@Valid @RequestBody YearsPercents yearsPercents, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MsgInt("Alguno de los datos no coinciden con el formato esperado", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        String message = "";

        try{

            YearsPercents newYearsPercents = new YearsPercents(
                    yearsPercents.getYear(),
                    yearsPercents.getPercent()
            );

            yearsPercentsService.save(newYearsPercents);

            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgInt("Registro Guardado", HttpStatus.CREATED.value(), newYearsPercents.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de un registro de porcentaje de descuento de un auto por año"
            ,notes = "Se actualiza un registro de porcentaje de descuento de un auto por año a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/cars/yearspercents/{id}")
    public ResponseEntity<MsgInt>  update(@PathVariable(value = "id") int id, @Valid @RequestBody YearsPercents yearsPercents){
        YearsPercents yearsPercentsUpdated = yearsPercentsService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("YearsPercents not found for this id :: " + id));

        String message = "";

        yearsPercentsUpdated.setYear(yearsPercents.getYear());
        yearsPercentsUpdated.setPercent(yearsPercents.getPercent());

        try{
            yearsPercentsService.save(yearsPercentsUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(new MsgInt("El registro se ha actualizado con éxito.", HttpStatus.OK.value(), yearsPercentsUpdated.getId()));

        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Eliminación de un registro de porcentaje por año, por ID"
            ,notes = "Se elimina un registro de porcentaje por año, a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('ADMINCOTIZADORVO')")
    @DeleteMapping("/cars/yearspercents/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            yearsPercentsService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
