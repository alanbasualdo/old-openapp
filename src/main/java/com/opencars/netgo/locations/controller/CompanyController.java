package com.opencars.netgo.locations.controller;

import com.opencars.netgo.locations.entity.Company;
import com.opencars.netgo.locations.service.CompanyService;
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
@Api(tags = "Controlador de Empresas")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @ApiOperation(value = "Creación de una Empresa"
            ,notes = "Se envía objeto de tipo company a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PostMapping("/company")
    public ResponseEntity<?> create(@Valid @RequestBody Company company, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        Company newCompany =
                new Company(

                        company.getName(),
                        company.getCuit(),
                        company.getLogo()

                );
        companyService.save(newCompany);

        return new ResponseEntity(new Msg("Compañía Guardada"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Actualización de una Compañía"
            ,notes = "Se actualiza la información de una compañía")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/company/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Company company){
        Company companyUpdated = companyService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + id));;
        companyUpdated.setCuit(company.getCuit());
        companyUpdated.setName(company.getName());
        companyUpdated.setLogo(company.getLogo());

        companyService.save(companyUpdated);

        return ResponseEntity.ok(companyUpdated);
    }

    @ApiOperation(value = "Lista de Empresas"
            ,notes = "Se obtiene una lista de empresas sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @GetMapping("/company")
    public List<Company> getCompanies(){

        List<Company> list = companyService.getAll();

        return list;
    }

    @ApiOperation(value = "Empresa por ID"
            ,notes = "Se obtiene una empresa a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @GetMapping("/company/{id}")
    public ResponseEntity<Company> getById(@PathVariable("id") int id){
        if(!companyService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        Company company = companyService.getOne(id).get();
        return new ResponseEntity(company, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Compañías por Nombre"
            ,notes = "Se obtiene una lista de compañías por coincidencia en el nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @GetMapping("/company/name/{name}")
    public List<Company> getCompaniesByName(@PathVariable("name") String name){

        List<Company> list = companyService.getByName(name);

        return list;
    }

}
