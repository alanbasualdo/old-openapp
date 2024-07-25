package com.opencars.netgo.sales.proveedores.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.msgs.MsgInt;
import com.opencars.netgo.sales.proveedores.entity.SalesProviders;
import com.opencars.netgo.sales.proveedores.entity.StatesProveedores;
import com.opencars.netgo.sales.proveedores.entity.TableContableProviders;
import com.opencars.netgo.sales.proveedores.service.SalesProvidersService;
import com.opencars.netgo.sales.proveedores.service.StatesProveedoresService;
import com.opencars.netgo.sales.proveedores.service.TableContableProvidersService;
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
@Api(tags = "Controlador de Tabla de Analista Contables para Compras-Proveedores")
public class TableContableProvidersController {

    @Autowired
    TableContableProvidersService tableContableProvidersService;

    @Autowired
    SalesProvidersService salesProvidersService;

    @Autowired
    StatesProveedoresService statesProveedoresService;

    @ApiOperation(value = "Lista de analistas contables por marca"
            ,notes = "Se obtiene una lista de analista contables por marca para proceso de compras")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/proveedores/analysts/list")
    public List<TableContableProviders> getAll(){
        List<TableContableProviders> list = tableContableProvidersService.list();
        return list;
    }

    @ApiOperation(value = "Analista Contable por marca, por ID"
            ,notes = "Se obtiene un analista contable, a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/proveedores/analysts/{id}")
    public ResponseEntity<TableContableProviders> getById(@PathVariable("id") int id){
        TableContableProviders tableContable = tableContableProvidersService.getOne(id).get();
        return new ResponseEntity(tableContable, HttpStatus.OK);
    }

    @ApiOperation(value = "Creación de un analista contable para proceso de compras"
            ,notes = "Se envía objeto de tipo TableContable a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/proveedores/analysts/create")
    public ResponseEntity<?> create(@Valid @RequestBody TableContableProviders tableContableProviders, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MsgInt("Alguno de los datos no coinciden con el formato esperado", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        String message = "";

        try{

            TableContableProviders newAnalyst = new TableContableProviders(
                    tableContableProviders.getBranch(),
                    tableContableProviders.getAnalyst()
            );

            tableContableProvidersService.save(newAnalyst);

            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgInt("Registro Guardado", HttpStatus.CREATED.value(), newAnalyst.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de un una analista contable por marca para proceso de compras"
            ,notes = "Se actualiza un analista contable de marca, a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/proveedores/analysts/{id}")
    public ResponseEntity<MsgInt>  update(@PathVariable(value = "id") int id, @Valid @RequestBody TableContableProviders tableContableProviders){
        TableContableProviders analystUpdated = tableContableProvidersService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Analyst not found for this id :: " + id));

        String message = "";

        List<SalesProviders> listPendingsForAnalyst = this.salesProvidersService.getPendingsForAnalyst(analystUpdated.getAnalyst(), analystUpdated.getBranch());

        analystUpdated.setBranch(tableContableProviders.getBranch());
        analystUpdated.setAnalyst(tableContableProviders.getAnalyst());

        try{
            tableContableProvidersService.save(analystUpdated);
            for (SalesProviders sale : listPendingsForAnalyst) {
                this.salesProvidersService.passSalesPendingsToOtherAnalyst(sale.getId(), tableContableProviders.getAnalyst());
            }
            return ResponseEntity.status(HttpStatus.OK).body(new MsgInt("El registro se ha actualizado con éxito.", HttpStatus.OK.value(), analystUpdated.getId()));

        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Eliminación de un analista contable de proceso de compras por ID"
            ,notes = "Se elimina un registro a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/proveedores/analysts/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            tableContableProvidersService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
