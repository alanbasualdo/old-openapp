package com.opencars.netgo.locations.controller;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.locations.service.BranchService;
import com.opencars.netgo.msgs.Msg;
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
@Api(tags = "Controlador de Sucursales")
public class BranchsController {

    @Autowired
    BranchService branchService;

    @ApiOperation(value = "Creación de una Sucursal"
            ,notes = "Se envía objeto de tipo branch a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/branchs")
    public ResponseEntity<?> create(@Valid @RequestBody Branch branch, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        Branch newBranch =
                new Branch(

                       branch.getCity(),
                       branch.getProvince(),
                       branch.getStreet(),
                       branch.getPhone(),
                       branch.getBrandsCompany()

                );
        branchService.save(newBranch);

        return new ResponseEntity(new Msg("Sucursal Guardada"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Actualización de una Sucursal"
            ,notes = "Se actualiza la información de una sucursal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/branchs/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Branch branch){
        Branch branchUpdated = branchService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found for this id :: " + id));;
        branchUpdated.setCity(branch.getCity());
        branchUpdated.setPhone(branch.getPhone());
        branchUpdated.setProvince(branch.getProvince());
        branchUpdated.setStreet(branch.getStreet());
        branchUpdated.setBrandsCompany(branch.getBrandsCompany());

        branchService.save(branchUpdated);

        return ResponseEntity.ok(branchUpdated);
    }

    @ApiOperation(value = "Lista de Sucursales"
            ,notes = "Se obtiene una lista de sucursales sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/branchs")
    public List<Branch> getBranchs(){

        List<Branch> list = branchService.getAll();

        return list;
    }

    @ApiOperation(value = "Sucursal por ID"
            ,notes = "Se obtiene una sucursal a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/branchs/{id}")
    public ResponseEntity<Branch> getById(@PathVariable("id") int id){
        if(!branchService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        Branch branch = branchService.getOne(id).get();
        return new ResponseEntity(branch, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Sucursales por Ciudad"
            ,notes = "Se obtiene una lista de sucursales por coincidencia en la ciudad")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/branchs/city/{name}")
    public List<Branch> getBranchsByCity(@PathVariable("name") String name){

        List<Branch> list = branchService.getByCity(name);

        return list;
    }

}
