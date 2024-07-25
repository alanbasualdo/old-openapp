package com.opencars.netgo.users.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.users.entity.Position;
import com.opencars.netgo.users.service.PositionService;
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
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Posiciones-Puestos de Trabajo")
public class PositionController {

    @Autowired
    PositionService positionService;

    @ApiOperation(value = "Creación de una Posición"
            ,notes = "Se envía un objeto de tipo position a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/positions")
    public ResponseEntity<?> create(@Valid @RequestBody Position position, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Alguno de los datos no coinciden con el formato esperado"), HttpStatus.BAD_REQUEST);

        String message = "";

        Position newPosition =
                new Position(
                        position.getPosition(),
                        position.getSubSector(),
                        position.getLinea(),
                        position.getBranchs()

                );

        try{
            positionService.save(newPosition);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Msg("Puesto Guardado", HttpStatus.CREATED.value()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de una Posición"
            ,notes = "Se actualiza una posición a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/positions/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Position position){
        Position positionUpdated = positionService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found for this id :: " + id));

        positionUpdated.setPosition(position.getPosition());
        positionUpdated.setSubSector(position.getSubSector());
        positionUpdated.setLinea(position.getLinea());
        positionUpdated.setBranchs(position.getBranchs());
        positionService.save(positionUpdated);

        return ResponseEntity.ok(positionUpdated);
    }

    @ApiOperation(value = "Lista de posiciones sin Paginación"
            ,notes = "Se obtiene una lista de posiciones sin paginar")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/positions/notpaginated")
    public List<Position> getPositionsNotPaginated(){

        List<Position> list = positionService.getAll();

        return list;
    }

    @ApiOperation(value = "Lista de Posiciones"
            ,notes = "Se obtiene una lista de posiciones paginadas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/positions")
    public Page<Position> getPositions(@PageableDefault(size = 10, page = 0) Pageable pageable){

        Page<Position> list = positionService.getAllPosition(pageable);

        return list;
    }

    @ApiOperation(value = "Eliminación de una posición por ID"
            ,notes = "Se elimina un registro a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('ADMINIT')")
    @DeleteMapping("/positions/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            positionService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Eliminada", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Posición por Nombre"
            ,notes = "Se obtiene una posición específica a través de su nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/positions/name/{name}")
    public List<Position> getPositionsByName(@PathVariable("name") String name){

        List<Position> list = positionService.getByName(name);

        return list;
    }

    @ApiOperation(value = "Posición por ID"
            ,notes = "Se obtiene una posición específica a través de su id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/positions/id/{id}")
    public Position getPositionsById(@PathVariable("id") int id){

        Position position = positionService.getOne(id).get();

        return position;
    }
}
