package com.opencars.netgo.locations.controller;

import com.opencars.netgo.locations.entity.BrandsCompany;
import com.opencars.netgo.locations.service.BrandsCompanyService;
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
@Api(tags = "Controlador de Marcas de Vehículos para Compañías")
public class BrandsCompanyController {

    @Autowired
    BrandsCompanyService brandsCompanyService;

    @ApiOperation(value = "Creación de una Marca de Vehículo para una Empresa"
            ,notes = "Se envía objeto de tipo BrandsCompany a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PostMapping("/brandscompany")
    public ResponseEntity<?> create(@Valid @RequestBody BrandsCompany brandsCompany, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        BrandsCompany newBrandCompany =
                new BrandsCompany(
                        brandsCompany.getName(),
                        brandsCompany.getCompany()

                );
        brandsCompanyService.save(newBrandCompany);

        return new ResponseEntity(new Msg("Marca Guardada"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Actualización de una Marca de Vehículo para una Compañía"
            ,notes = "Se actualiza la información de una marca")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/brandscompany/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody BrandsCompany brandsCompany){
        BrandsCompany brandCompanyUpdated = brandsCompanyService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("BrandCompany not found for this id :: " + id));;
        brandCompanyUpdated.setName(brandsCompany.getName());
        brandCompanyUpdated.setCompany(brandsCompany.getCompany());

        brandsCompanyService.save(brandCompanyUpdated);

        return ResponseEntity.ok(brandCompanyUpdated);
    }

    @ApiOperation(value = "Lista de Marcas de Vehículos para Compañías"
            ,notes = "Se obtiene una lista de marcas de vehículos para compañías sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @GetMapping("/brandscompany")
    public List<BrandsCompany> getBrandsCompanies(){

        List<BrandsCompany> list = brandsCompanyService.getAll();

        return list;
    }

    @ApiOperation(value = "Marca de Vehículo para Compañía por ID"
            ,notes = "Se obtiene una marca de vehículo para compañía, a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @GetMapping("/brandscompany/{id}")
    public ResponseEntity<BrandsCompany> getById(@PathVariable("id") int id){
        if(!brandsCompanyService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        BrandsCompany brandsCompany = brandsCompanyService.getOne(id).get();
        return new ResponseEntity(brandsCompany, HttpStatus.OK);
    }
}
