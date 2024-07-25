package com.opencars.netgo.dms.quoter.controller;

import com.opencars.netgo.dms.quoter.entity.BrandsCars;
import com.opencars.netgo.dms.quoter.service.BrandsCarsService;
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
@Api(tags = "Controlador de Tabla de Marcas de Autos")
public class BrandsCarsController {

    @Autowired
    BrandsCarsService brandsService;

    @ApiOperation(value = "Lista de marcas de autos"
            ,notes = "Se obtiene una lista de marcas de autos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cars/brands/list")
    public List<BrandsCars> getAll(){
        List<BrandsCars> list = brandsService.list();
        return list;
    }

    @ApiOperation(value = "Marca de auto por ID"
            ,notes = "Se obtiene una marca de un auto a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cars/brands/{id}")
    public ResponseEntity<BrandsCars> getById(@PathVariable("id") int id){
        BrandsCars brandsCars = brandsService.getOne(id).get();
        return new ResponseEntity(brandsCars, HttpStatus.OK);
    }

    @ApiOperation(value = "Creación de una marca de auto"
            ,notes = "Se envía objeto de tipo brandsCars a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/cars/brands/create")
    public ResponseEntity<?> create(@Valid @RequestBody BrandsCars brandsCars, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MsgInt("Alguno de los datos no coinciden con el formato esperado", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        String message = "";

        try{

            BrandsCars newBrand = new BrandsCars(
                    brandsCars.getCodia(),
                    brandsCars.getBrand()
            );

            brandsService.save(newBrand);

            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgInt("Registro Guardado", HttpStatus.CREATED.value(), newBrand.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de un una marca de auto"
            ,notes = "Se actualiza una marca de auto a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/cars/brands/{id}")
    public ResponseEntity<MsgInt>  update(@PathVariable(value = "id") int id, @Valid @RequestBody BrandsCars brandsCars){
        BrandsCars brandUpdated = brandsService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found for this id :: " + id));

        String message = "";

        brandUpdated.setCodia(brandsCars.getCodia());
        brandUpdated.setBrand(brandsCars.getBrand());

        try{
            brandsService.save(brandUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(new MsgInt("El registro se ha actualizado con éxito.", HttpStatus.OK.value(), brandUpdated.getId()));

        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }
}
