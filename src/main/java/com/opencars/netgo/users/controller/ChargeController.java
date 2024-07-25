package com.opencars.netgo.users.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.users.entity.Charge;
import com.opencars.netgo.users.repository.ChargeRepository;
import com.opencars.netgo.users.service.ChargeService;
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
@Api(tags = "Controlador de Nombres de Puestos de Trabajo")
public class ChargeController {

    @Autowired
    ChargeService chargeService;
    @Autowired
    private ChargeRepository chargeRepository;

    @ApiOperation(value = "Creación del nombre de una Posición"
            ,notes = "Se envía un objeto de tipo charge a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/charge")
    public ResponseEntity<?> create(@Valid @RequestBody Charge charge, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Alguno de los datos no coinciden con el formato esperado"), HttpStatus.BAD_REQUEST);
        if (chargeService.existsByName(charge.getName()))
            return new ResponseEntity(new Msg("Ese nombre de puesto ya existe"), HttpStatus.BAD_REQUEST);

        String message = "";

        Charge newCharge =
                new Charge(
                        charge.getName()

                );

        try{
            chargeRepository.save(newCharge);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Msg("Nombre de Puesto Guardado", HttpStatus.CREATED.value()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización del nombre de un Puesto de Traajo"
            ,notes = "Se actualiza el nombre de un puesto a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/charge/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Charge charge){
        Charge chargeUpdated = chargeService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Charge not found for this id :: " + id));;
        chargeUpdated.setName(charge.getName());
        chargeService.save(chargeUpdated);

        return ResponseEntity.ok(chargeUpdated);
    }

    @ApiOperation(value = "Lista de nombres de puestos sin Paginación"
            ,notes = "Se obtiene una lista de nombres de puestos sin paginar")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/charge/notpaginated")
    public List<Charge> getChargesNotPaginated(){

        List<Charge> list = chargeService.getAll();

        return list;
    }

    @ApiOperation(value = "Lista de Nombres de Puestos de Trabajo"
            ,notes = "Se obtiene una lista de nombres de puestos de trabajo, paginada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/charges")
    public Page<Charge> getCharges(@PageableDefault(size = 10, page = 0) Pageable pageable){

        Page<Charge> list = chargeService.getAllCharges(pageable);

        return list;
    }

    @ApiOperation(value = "Eliminación de un nombre de puesto de trabajo por ID"
            ,notes = "Se elimina un registro a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('ADMINIT')")
    @DeleteMapping("/charges/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            chargeService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Puesto de Trabajo por Nombre"
            ,notes = "Se obtiene un puesto de trabajo específico a través de su nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/charges/name/{name}")
    public List<Charge> getChargesByName(@PathVariable("name") String name){

        List<Charge> list = chargeService.getByName(name);

        return list;
    }

    @ApiOperation(value = "Nombre de puesto de trabajo por ID"
            ,notes = "Se obtiene un nombre de puesto de trabajo específico a través de su id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/charges/id/{id}")
    public Charge getChargeById(@PathVariable("id") int id){

        Charge charge = chargeService.getOne(id).get();

        return charge;
    }
}
