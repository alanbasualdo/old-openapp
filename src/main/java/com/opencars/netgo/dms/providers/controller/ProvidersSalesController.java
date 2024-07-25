package com.opencars.netgo.dms.providers.controller;

import ch.qos.logback.core.net.server.Client;
import com.opencars.netgo.dms.providers.entity.ProvidersSales;
import com.opencars.netgo.dms.providers.service.ProvidersSalesService;
import com.opencars.netgo.msgs.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/providerssales")
@CrossOrigin()
@Api(tags = "Controlador de Proveedores - Compras")
public class ProvidersSalesController {

    @Autowired
    ProvidersSalesService providersSalesService;

    @ApiOperation(value = "Creación de un proveedor para módulo compras"
            ,notes = "Se envía un objeto de tipo providerSales a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody ProvidersSales providersSales, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        if (providersSalesService.existsByCuit(providersSales.getCuit()))
            return new ResponseEntity(new Msg("Ya existe un proveedor con ese cuit"), HttpStatus.BAD_REQUEST);

        String message = "";
        ProvidersSales newProvider =
                new ProvidersSales(
                        providersSales.getNombre(),
                        providersSales.getTelefono(),
                        providersSales.getCorreo(),
                        providersSales.getForm(),
                        providersSales.getCuit(),
                        providersSales.getFiscalType(),
                        providersSales.getCreatedBy()
                );

        try{
            providersSalesService.save(newProvider);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Msg("Proveedor Guardado", HttpStatus.CREATED.value(), newProvider.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de un proveedor"
            ,notes = "Se actualiza un proveedor a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @Valid @RequestBody ProvidersSales providersSales, BindingResult bindingResult){
        String message = "";
        try{
            ProvidersSales providerUpdated = providersSalesService.getOne(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Provider not found for this id :: " + id));
            providerUpdated.setNombre(providersSales.getNombre());
            providerUpdated.setTelefono(providersSales.getTelefono());
            providerUpdated.setCorreo(providersSales.getCorreo());
            providerUpdated.setCuit(providersSales.getCuit());
            providerUpdated.setFiscalType(providersSales.getFiscalType());
            providerUpdated.setEditBy(providersSales.getEditBy());

            providersSalesService.save(providerUpdated);

            return ResponseEntity.ok(providerUpdated);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Lista de proveedores"
            ,notes = "Se obtiene una lista de proveedores paginada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list")
    public Page<ProvidersSales> getProviders(@PageableDefault(size = 10, page = 0) Pageable pageable){

        Page<ProvidersSales> list = providersSalesService.getAll(pageable);

        return list;
    }

    @ApiOperation(value = "Proveedor por ID"
            ,notes = "Se obtiene un proveedor a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/provider/{id}")
    public ResponseEntity<Client> getById(@PathVariable("id") Long id){
        if(!providersSalesService.existsById(id))
            return new ResponseEntity(new Msg("no existe"), HttpStatus.NOT_FOUND);
        ProvidersSales providersSales = providersSalesService.getOne(id).get();
        return new ResponseEntity(providersSales, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Proveedores por coincidencia en Nombre"
            ,notes = "Se obtiene una lista de proveedores por coincidencia en el nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/name/{name}")
    public List<ProvidersSales> getProvidersByName(@PathVariable("name") String name){
        List<ProvidersSales> list = providersSalesService.getByName(name);
        return list;
    }

    @ApiOperation(value = "Lista de Proveedores por coincidencia en Cuit"
            ,notes = "Se obtiene una lista de proveedores por coincidencia en el cuit")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cuit/{cuit}")
    public List<ProvidersSales> getProvidersByName(@PathVariable("cuit") Long cuit){
        List<ProvidersSales> list = providersSalesService.getByCuit(cuit);
        return list;
    }
}
