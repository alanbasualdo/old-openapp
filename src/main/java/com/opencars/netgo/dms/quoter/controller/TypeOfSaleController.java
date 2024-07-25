package com.opencars.netgo.dms.quoter.controller;

import com.opencars.netgo.dms.quoter.entity.TypeOfSale;
import com.opencars.netgo.dms.quoter.service.TypeOfSaleService;
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
@Api(tags = "Controlador de Tabla de Tipo de Ventas para cotizador VO")
public class TypeOfSaleController {


    @Autowired
    TypeOfSaleService typeOfSaleService;

    @ApiOperation(value = "Lista de tipos de ventas"
            ,notes = "Se obtiene una lista de tipos de venta de autos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/sales/types/list")
    public List<TypeOfSale> getAll(){
        List<TypeOfSale> list = typeOfSaleService.list();
        return list;
    }

    @ApiOperation(value = "Registro de tipo de venta de auto por ID"
            ,notes = "Se obtiene un registro de tipo de venta de un auto a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/sales/types/{id}")
    public ResponseEntity<TypeOfSale> getById(@PathVariable("id") int id){
        TypeOfSale typeOfSale = typeOfSaleService.getOne(id).get();
        return new ResponseEntity(typeOfSale, HttpStatus.OK);
    }

    @ApiOperation(value = "Creación de un tipo de venta de auto"
            ,notes = "Se envía objeto de tipo TypeOfSale a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/sales/types/create")
    public ResponseEntity<?> create(@Valid @RequestBody TypeOfSale typeOfSale, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MsgInt("Alguno de los datos no coinciden con el formato esperado", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        String message = "";

        try{

            TypeOfSale newTypeOfSale= new TypeOfSale(
                    typeOfSale.getType(),
                    typeOfSale.getPercent()
            );

            typeOfSaleService.save(newTypeOfSale);

            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgInt("Registro Guardado", HttpStatus.CREATED.value(), newTypeOfSale.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de un registro de tipo de venta de auto"
            ,notes = "Se actualiza un registro de un tipo de venta de auto a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/sales/types/{id}")
    public ResponseEntity<MsgInt>  update(@PathVariable(value = "id") int id, @Valid @RequestBody TypeOfSale typeOfSale){
        TypeOfSale typeOfSaleUpdated = typeOfSaleService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("TypeOfSale not found for this id :: " + id));

        String message = "";

        typeOfSaleUpdated.setType(typeOfSale.getType());
        typeOfSaleUpdated.setPercent(typeOfSale.getPercent());

        try{
            typeOfSaleService.save(typeOfSaleUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(new MsgInt("El registro se ha actualizado con éxito.", HttpStatus.OK.value(), typeOfSaleUpdated.getId()));

        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Eliminación de un registro de tipo de venta, por ID"
            ,notes = "Se elimina un registro de tipo de venta, a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('ADMINCOTIZADORVO')")
    @DeleteMapping("/sales/types/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            typeOfSaleService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
