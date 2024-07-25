package com.opencars.netgo.sales.viaticos.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.msgs.MsgInt;
import com.opencars.netgo.sales.viaticos.entity.StatesCompras;
import com.opencars.netgo.sales.viaticos.entity.TableTreasurers;
import com.opencars.netgo.sales.viaticos.entity.Viaticos;
import com.opencars.netgo.sales.viaticos.service.StatesComprasService;
import com.opencars.netgo.sales.viaticos.service.TableTreasurersService;
import com.opencars.netgo.sales.viaticos.service.ViaticosService;
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
@Api(tags = "Controlador de Tabla de Tesoreros")
public class TableTreasurersController {

    @Autowired
    TableTreasurersService tableTreasurersService;

    @Autowired
    ViaticosService viaticosService;

    @Autowired
    StatesComprasService statesComprasService;

    @ApiOperation(value = "Lista de tesoreros por sucursal"
            ,notes = "Se obtiene una lista de tesoreros por sucursal para proceso de compras")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viaticos/treasurers/list")
    public List<TableTreasurers> getAll(){
        List<TableTreasurers> list = tableTreasurersService.list();
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
    @GetMapping("/viaticos/treasurers/{id}")
    public ResponseEntity<TableTreasurers> getById(@PathVariable("id") int id){
        TableTreasurers tableTreasurers = tableTreasurersService.getOne(id).get();
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
    @PostMapping("/viaticos/treasurers/create")
    public ResponseEntity<?> create(@Valid @RequestBody TableTreasurers tableTreasurers, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MsgInt("Alguno de los datos no coinciden con el formato esperado", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        String message = "";

        try{

            TableTreasurers newTreasurer = new TableTreasurers(
                    tableTreasurers.getBranch(),
                    tableTreasurers.getTreasurer()
            );

            tableTreasurersService.save(newTreasurer);

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
    @PutMapping("/viaticos/treasurers/{id}")
    public ResponseEntity<MsgInt>  update(@PathVariable(value = "id") int id, @Valid @RequestBody TableTreasurers tableTreasurers){
        TableTreasurers treasurerUpdated = tableTreasurersService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Treasurer not found for this id :: " + id));

        String message = "";

        List<Viaticos> listPendingsForTreasurer = this.viaticosService.getListByTreasurerAndBranchAndStateNotPaid(treasurerUpdated.getTreasurer(), treasurerUpdated.getBranch());

        treasurerUpdated.setBranch(tableTreasurers.getBranch());
        treasurerUpdated.setTreasurer(tableTreasurers.getTreasurer());

        try{
            tableTreasurersService.save(treasurerUpdated);
            for (Viaticos viaticos : listPendingsForTreasurer) {
                this.viaticosService.passViaticosPendingsToOtherTreasurer(viaticos.getId(), tableTreasurers.getTreasurer());
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
    @DeleteMapping("/viaticos/treasurers/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            tableTreasurersService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

}
