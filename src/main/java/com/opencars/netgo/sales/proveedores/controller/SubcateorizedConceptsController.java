package com.opencars.netgo.sales.proveedores.controller;

import com.opencars.netgo.msgs.MsgInt;
import com.opencars.netgo.sales.proveedores.entity.SubcateorizedConcepts;
import com.opencars.netgo.sales.proveedores.service.SubcateorizedConceptsService;
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
@RequestMapping("/api/concepts/subcategorizeds")
@CrossOrigin
@Api(tags = "Controlador de Subcategorización de Conceptos para el Módulo de Compras")
public class SubcateorizedConceptsController {

    @Autowired
    SubcateorizedConceptsService subcateorizedConceptsService;


    @ApiOperation(value = "Lista de subcategorizaciones de conceptos"
            ,notes = "Se obtiene una lista de subcategorizaciones de conceptos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list")
    public List<SubcateorizedConcepts> getAll(){
        List<SubcateorizedConcepts> list = subcateorizedConceptsService.list();
        return list;
    }

    @ApiOperation(value = "Subcategorización de concepto por ID"
            ,notes = "Se obtiene una subcategorización de concepto a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<SubcateorizedConcepts> getById(@PathVariable("id") int id){
        SubcateorizedConcepts subcateorizedConcepts = subcateorizedConceptsService.getOne(id).get();
        return new ResponseEntity(subcateorizedConcepts, HttpStatus.OK);
    }

    @ApiOperation(value = "Creación de una subcategorización de concepto"
            ,notes = "Se envía objeto de tipo SubcateorizedConcepts a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody SubcateorizedConcepts subcateorizedConcepts, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MsgInt("Alguno de los datos no coinciden con el formato esperado", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        String message = "";

        try{

            SubcateorizedConcepts newSubcateorizedConcepts = new SubcateorizedConcepts(
                    subcateorizedConcepts.getSubConcept(),
                    subcateorizedConcepts.getConcept(),
                    subcateorizedConcepts.getType()
            );

            subcateorizedConceptsService.save(newSubcateorizedConcepts);

            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgInt("Registro Guardado", HttpStatus.CREATED.value(), newSubcateorizedConcepts.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de una subcategorización de concepto"
            ,notes = "Se actualiza una subcategorización de concepto a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<MsgInt>  update(@PathVariable(value = "id") int id, @Valid @RequestBody SubcateorizedConcepts subcateorizedConcepts){
        SubcateorizedConcepts subConceptUpdated = subcateorizedConceptsService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubcateorizedConcepts not found for this id :: " + id));

        String message = "";

        subConceptUpdated.setSubConcept(subcateorizedConcepts.getSubConcept());
        subConceptUpdated.setConcept(subcateorizedConcepts.getConcept());
        subConceptUpdated.setType(subcateorizedConcepts.getType());

        try{
            subcateorizedConceptsService.save(subConceptUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(new MsgInt("El registro se ha actualizado con éxito.", HttpStatus.OK.value(), subConceptUpdated.getId()));

        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Eliminación de una subcategorización de concepto por ID"
            ,notes = "Se elimina un registro a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MsgInt> deleteById(@PathVariable int id) {
        try{
            subcateorizedConceptsService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new MsgInt("Registro Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MsgInt("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
