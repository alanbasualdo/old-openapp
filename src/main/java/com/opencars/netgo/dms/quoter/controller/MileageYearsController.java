package com.opencars.netgo.dms.quoter.controller;

import com.opencars.netgo.dms.quoter.entity.MileageYears;
import com.opencars.netgo.dms.quoter.service.MileageYearsService;
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
@Api(tags = "Controlador de Tabla de Porcentajes por año y kilometraje para cotizador")
public class MileageYearsController {

    @Autowired
    MileageYearsService mileageYearsService;

    @ApiOperation(value = "Lista de registros de porcentajes por año del auto y kilometraje"
            ,notes = "Se obtiene una lista de porcentajes de descuento por año de auto y kilometraje")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mileage/years/list")
    public List<MileageYears> getAll(){
        List<MileageYears> list = mileageYearsService.list();
        return list;
    }

    @ApiOperation(value = "Registro de porcentaje de descuento por año y kilometraje, por ID"
            ,notes = "Se obtiene un registro de descuento por año de un auto y kilometraje a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mileage/years/{id}")
    public ResponseEntity<MileageYears> getById(@PathVariable("id") int id){
        MileageYears mileageYears = mileageYearsService.getOne(id).get();
        return new ResponseEntity(mileageYears, HttpStatus.OK);
    }

    @ApiOperation(value = "Creación de un registro de descuento por año de auto y kilometraje"
            ,notes = "Se envía objeto de tipo MileageYears a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/mileage/years/create")
    public ResponseEntity<?> create(@Valid @RequestBody MileageYears mileageYears, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MsgInt("Alguno de los datos no coinciden con el formato esperado", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        String message = "";

        try{

            MileageYears newMileageYear = new MileageYears(
                    mileageYears.getYear(),
                    mileageYears.getInitKm(),
                    mileageYears.getEndKm(),
                    mileageYears.getPercent()
            );

            mileageYearsService.save(newMileageYear);

            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgInt("Registro Guardado", HttpStatus.CREATED.value(), newMileageYear.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de un registro de descuento por año de auto y kilometraje"
            ,notes = "Se actualiza un registro de descuento por año de auto y kilometraje a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/mileage/years/{id}")
    public ResponseEntity<MsgInt>  update(@PathVariable(value = "id") int id, @Valid @RequestBody MileageYears mileageYears){
        MileageYears mileageYearsUpdated = mileageYearsService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("MileageYear not found for this id :: " + id));

        String message = "";

        mileageYearsUpdated.setYear(mileageYears.getYear());
        mileageYearsUpdated.setInitKm(mileageYears.getInitKm());
        mileageYearsUpdated.setEndKm(mileageYears.getEndKm());
        mileageYearsUpdated.setPercent(mileageYears.getPercent());

        try{
            mileageYearsService.save(mileageYearsUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(new MsgInt("El registro se ha actualizado con éxito.", HttpStatus.OK.value(), mileageYearsUpdated.getId()));

        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Porcentaje según el año del auto y el kilómetraje"
            ,notes = "Se obtiene un porcentaje según el año del auto y el kilometraje")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mileage/years/{year}/km/{km}")
    public ResponseEntity<MileageYears> getPercentForYearAndKms(@PathVariable("year") int year, @PathVariable("km") long km){
        MileageYears mileageYears = mileageYearsService.getPercentForYearsAndKm(year, km).get();
        return new ResponseEntity(mileageYears, HttpStatus.OK);
    }

    @ApiOperation(value = "Eliminación de un registro de porcentaje por año y kms, por ID"
            ,notes = "Se elimina un registro de porcentaje por año y kms, a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('ADMINCOTIZADORVO')")
    @DeleteMapping("/mileage/years/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            mileageYearsService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

}
