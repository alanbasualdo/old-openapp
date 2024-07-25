package com.opencars.netgo.dms.quoter.controller;

import com.opencars.netgo.dms.quoter.entity.MileageTable;
import com.opencars.netgo.dms.quoter.service.MileageService;
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
@Api(tags = "Controlador de Tabla de Kilometrajes para cotizador")
public class MileageController {

    @Autowired
    MileageService mileageService;

    @ApiOperation(value = "Lista de registros de kilometraje"
            ,notes = "Se obtiene una lista de rangos de kilometrajes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mileage/list")
    public List<MileageTable> getAll(){
        List<MileageTable> list = mileageService.list();
        return list;
    }

    @ApiOperation(value = "Registro de kilometraje por ID"
            ,notes = "Se obtiene un registro de rangos de kilometrajes a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mileage/{id}")
    public ResponseEntity<MileageTable> getById(@PathVariable("id") int id){
        MileageTable mileageTable = mileageService.getOne(id).get();
        return new ResponseEntity(mileageTable, HttpStatus.OK);
    }

    @ApiOperation(value = "Creación de un registro de kilometraje"
            ,notes = "Se envía objeto de tipo MileageTable a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/mileage/create")
    public ResponseEntity<?> create(@Valid @RequestBody MileageTable mileageTable, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MsgInt("Alguno de los datos no coinciden con el formato esperado", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        String message = "";

        try{

            MileageTable newMileage = new MileageTable(
                    mileageTable.getInitKm(),
                    mileageTable.getEndKm(),
                    mileageTable.getPercent()
            );

            mileageService.save(newMileage);

            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgInt("Registro Guardado", HttpStatus.CREATED.value(), newMileage.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de un registro de kilometraje"
            ,notes = "Se actualiza un registro de kilometraje a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/mileage/{id}")
    public ResponseEntity<MsgInt>  update(@PathVariable(value = "id") int id, @Valid @RequestBody MileageTable mileageTable){
        MileageTable mileageUpdated = mileageService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mileage not found for this id :: " + id));

        String message = "";

        mileageUpdated.setInitKm(mileageTable.getInitKm());
        mileageUpdated.setEndKm(mileageTable.getEndKm());
        mileageUpdated.setPercent(mileageTable.getPercent());

        try{
            mileageService.save(mileageUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(new MsgInt("El registro se ha actualizado con éxito.", HttpStatus.OK.value(), mileageUpdated.getId()));

        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Porcentaje según el promedio de kilómetros"
            ,notes = "Se obtiene un porcentaje según el rango al que pertenece el promedio de los kilómetros")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mileage/average/{average}")
    public ResponseEntity<MileageTable> getPercentForAverage(@PathVariable("average") long average){
        MileageTable mileageTable = mileageService.getKmRange(average).get();
        return new ResponseEntity(mileageTable, HttpStatus.OK);
    }

    @ApiOperation(value = "Eliminación de un registro de rango de kilometraje por ID"
            ,notes = "Se elimina un registro a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('ADMINCOTIZADORVO')")
    @DeleteMapping("/mileage/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            mileageService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
