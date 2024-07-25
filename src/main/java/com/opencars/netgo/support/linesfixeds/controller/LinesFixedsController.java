package com.opencars.netgo.support.linesfixeds.controller;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.linesfixeds.entity.LinesFixeds;
import com.opencars.netgo.support.linesfixeds.service.LinesFixedsService;
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
@RequestMapping("/api/linesfixeds")
@CrossOrigin
@Api(tags = "Controlador de Tipos de Líneas Fijas")
public class LinesFixedsController {

    @Autowired
    LinesFixedsService linesFixedsService;

    @ApiOperation(value = "Creación de una Línea Fija"
            ,notes = "Se envía objeto de tipo LinesFixeds a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/line")
    public ResponseEntity<?> create(@Valid @RequestBody LinesFixeds linesFixeds, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        LinesFixeds newLine =
                new LinesFixeds(
                        linesFixeds.getLine(),
                        linesFixeds.getType(),
                        linesFixeds.getUseLine(),
                        linesFixeds.getReceiptNumber(),
                        linesFixeds.getProvider(),
                        linesFixeds.getInvoiced(),
                        linesFixeds.getObservation(),
                        linesFixeds.getBranch()
                );
        linesFixedsService.save(newLine);

        return new ResponseEntity(new Msg("Línea Fija Guardada"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Lista de Líneas Fijas"
            ,notes = "Se obtiene una lista de líneas fijas paginada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT') or hasRole('VIEWPERMISSIONS') or hasRole('VIEWDLS')")
    @GetMapping("/lines")
    public Page<LinesFixeds> getLines(@PageableDefault(size = 10, page = 0) Pageable pageable){

        Page<LinesFixeds> list = linesFixedsService.getAll(pageable);

        return list;
    }

    @ApiOperation(value = "Actualización de una Línea Fija"
            ,notes = "Se actualiza la información de una línea fija")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/line/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody LinesFixeds linesFixeds){
        LinesFixeds lineUpdated = linesFixedsService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("LinesFixeds not found for this id :: " + id));;
        lineUpdated.setLine(linesFixeds.getLine());
        lineUpdated.setType(linesFixeds.getType());
        lineUpdated.setUseLine(linesFixeds.getUseLine());
        lineUpdated.setReceiptNumber(linesFixeds.getReceiptNumber());
        lineUpdated.setProvider(linesFixeds.getProvider());
        lineUpdated.setInvoiced(linesFixeds.getInvoiced());
        lineUpdated.setObservation(linesFixeds.getObservation());
        lineUpdated.setBranch(linesFixeds.getBranch());
        linesFixedsService.save(lineUpdated);

        return ResponseEntity.ok(lineUpdated);
    }

    @ApiOperation(value = "Línea Fija por ID"
            ,notes = "Se obtiene una línea fija a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/line/{id}")
    public ResponseEntity<LinesFixeds> getById(@PathVariable("id") int id){
        if(!linesFixedsService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        LinesFixeds linesFixeds = linesFixedsService.getOne(id).get();
        return new ResponseEntity(linesFixeds, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Líneas Fijas por Sucursal"
            ,notes = "Se obtiene una lista de líneas fijas por sucursal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/lines/branch/{branch}")
    public List<LinesFixeds> getLinesByBranch(@PathVariable("branch") Branch branch){

        List<LinesFixeds> list = linesFixedsService.getByBranch(branch.getId());

        return list;
    }

    @ApiOperation(value = "Lista de Líneas Fijas por Sucursal y tipo"
            ,notes = "Se obtiene una lista de líneas fijas por sucursal y tipo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/lines/branch/{branch}/type/{type}")
    public List<LinesFixeds> getLinesByBranchAndType(@PathVariable("branch") Branch branch, @PathVariable("type") String type){

        List<LinesFixeds> list = linesFixedsService.getByBranchAndType(branch.getId(), type);

        return list;
    }

    @ApiOperation(value = "Lista de Líneas Fijas por Coincidencia en el número"
            ,notes = "Se obtiene una lista de líneas fijas por coincidencia en el número")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/lines/coincidence/{coincidence}")
    public List<LinesFixeds> getLinesByCoincidence(@PathVariable("coincidence") String coincidence){

        List<LinesFixeds> list = linesFixedsService.getByCoincidence(coincidence);

        return list;
    }

    @ApiOperation(value = "Lista de Líneas Fijas por Coincidencia en el uso"
            ,notes = "Se obtiene una lista de líneas fijas por coincidencia en el uso")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/lines/use/coincidence/{coincidence}")
    public List<LinesFixeds> getLinesByCoincidenceInUse(@PathVariable("coincidence") String coincidence){

        List<LinesFixeds> list = linesFixedsService.getByCoincidenceInUse(coincidence);

        return list;
    }

    @ApiOperation(value = "Lista de Líneas Fijas por Coincidencia en el uso y tipo"
            ,notes = "Se obtiene una lista de líneas fijas por coincidencia en el uso")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/lines/use/coincidence/{coincidence}/type/{type}")
    public List<LinesFixeds> getLinesByCoincidenceInUseAndType(@PathVariable("coincidence") String coincidence, @PathVariable("type") String type){

        List<LinesFixeds> list = linesFixedsService.getByCoincidenceInUseAndType(coincidence, type);

        return list;
    }

    @ApiOperation(value = "Lista de Líneas Fijas por Coincidencia en el tipo"
            ,notes = "Se obtiene una lista de líneas fijas por coincidencia en el tipo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/lines/type/{type}")
    public List<LinesFixeds> getLinesByType(@PathVariable("type") String type){

        List<LinesFixeds> list = linesFixedsService.getByCoincidenceInType(type);

        return list;
    }
}
