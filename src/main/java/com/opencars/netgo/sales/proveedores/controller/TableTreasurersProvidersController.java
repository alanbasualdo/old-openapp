package com.opencars.netgo.sales.proveedores.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.msgs.MsgInt;
import com.opencars.netgo.sales.proveedores.entity.SalesProviders;
import com.opencars.netgo.sales.proveedores.entity.StatesProveedores;
import com.opencars.netgo.sales.proveedores.entity.TableTreasurersProviders;
import com.opencars.netgo.sales.proveedores.service.SalesProvidersService;
import com.opencars.netgo.sales.proveedores.service.StatesProveedoresService;
import com.opencars.netgo.sales.proveedores.service.TableTreasurersProvidersService;
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
@Api(tags = "Controlador de Tabla de Tesoreros para Módulo Compras")
public class TableTreasurersProvidersController {

    @Autowired
    TableTreasurersProvidersService tableTreasurersProvidersService;

    @Autowired
    SalesProvidersService salesProvidersService;

    @Autowired
    StatesProveedoresService statesProveedoresService;

    @ApiOperation(value = "Lista de tesoreros por sucursal"
            ,notes = "Se obtiene una lista de tesoreros por sucursal para proceso de compras")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/proveedores/treasurers/list")
    public List<TableTreasurersProviders> getAll(){
        List<TableTreasurersProviders> list = tableTreasurersProvidersService.list();
        return list;
    }

    @ApiOperation(value = "Tesorero por sucursal, por ID"
            ,notes = "Se obtiene un tesorero, a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/proveedores/treasurers/{id}")
    public ResponseEntity<TableTreasurersProviders> getById(@PathVariable("id") int id){
        TableTreasurersProviders tableTreasurers = tableTreasurersProvidersService.getOne(id).get();
        return new ResponseEntity(tableTreasurers, HttpStatus.OK);
    }

    @ApiOperation(value = "Creación de un tesorero para proceso de compras"
            ,notes = "Se envía objeto de tipo TableTreasurers a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/proveedores/treasurers/create")
    public ResponseEntity<?> create(@Valid @RequestBody TableTreasurersProviders tableTreasurers, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MsgInt("Alguno de los datos no coinciden con el formato esperado", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        String message = "";

        try{

            TableTreasurersProviders newTreasurer = new TableTreasurersProviders(
                    tableTreasurers.getBranch(),
                    tableTreasurers.getTreasurer()
            );

            tableTreasurersProvidersService.save(newTreasurer);

            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgInt("Registro Guardado", HttpStatus.CREATED.value(), newTreasurer.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de un un tesorero por sucursal para proceso de compras"
            ,notes = "Se actualiza un tesorero de sucursal, a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/proveedores/treasurers/{id}")
    public ResponseEntity<MsgInt>  update(@PathVariable(value = "id") int id, @Valid @RequestBody TableTreasurersProviders tableTreasurers){
        TableTreasurersProviders treasurerUpdated = tableTreasurersProvidersService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Treasurer not found for this id :: " + id));

        String message = "";

        List<SalesProviders> listPendingsForTreasurer = this.salesProvidersService.getPendingsForTreasurer(treasurerUpdated.getTreasurer(), treasurerUpdated.getBranch());

        treasurerUpdated.setBranch(tableTreasurers.getBranch());
        treasurerUpdated.setTreasurer(tableTreasurers.getTreasurer());

        try{
            tableTreasurersProvidersService.save(treasurerUpdated);
            for (SalesProviders sale : listPendingsForTreasurer) {
                this.salesProvidersService.passSalesPendingsToOtherTreasurer(sale.getId(), tableTreasurers.getTreasurer());
            }
            return ResponseEntity.status(HttpStatus.OK).body(new MsgInt("El registro se ha actualizado con éxito.", HttpStatus.OK.value(), treasurerUpdated.getId()));

        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Eliminación de un tesorero de proceso de compras por ID"
            ,notes = "Se elimina un registro a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/proveedores/treasurers/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            tableTreasurersProvidersService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
