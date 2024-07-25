package com.opencars.netgo.dms.finanzas.controller;

import com.opencars.netgo.dms.finanzas.entity.*;
import com.opencars.netgo.dms.finanzas.service.FinanzasService;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Finanzas")
public class FinanzasController {

    @Autowired
    FinanzasService finanzasService;

    private static final Logger logger = LogManager.getLogger(FinanzasController.class);

    @ApiOperation(value = "Actualización del valor del Dólar"
            ,notes = "Se actualiza el valor del dólar.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINFINANZAS')")
    @PutMapping("/dolar/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Dolar dolar){
        Dolar dolarUpdated = finanzasService.getByIdOptional(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dolar not found for this id :: " + id));;

        int dolarPreview = finanzasService.getByIdOptional(id).get().getValue();

        LocalDateTime date = LocalDateTime.now();

        dolarUpdated.setValue(dolar.getValue());
        dolarUpdated.setDate(date);

        finanzasService.save(dolarUpdated);

        DolarRegister register = new DolarRegister(dolarPreview, dolarUpdated.getValue(), dolarUpdated.getDate());
        finanzasService.saveRegister(register);

        return ResponseEntity.ok(dolarUpdated);
    }

    @MessageMapping("/get-update")
    @SendTo("/dolar/get-update")
    public Dolar getDolarUpdate(Dolar dolar) {
        Dolar dolarUpdated = finanzasService.getByIdOptional(dolar.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Dolar not found for this id :: " + dolar.getId()));;

        int dolarPreview = finanzasService.getByIdOptional(dolar.getId()).get().getValue();

        LocalDateTime date = LocalDateTime.now();

        dolarUpdated.setValue(dolar.getValue());
        dolarUpdated.setDate(date);

        finanzasService.save(dolarUpdated);

        DolarRegister register = new DolarRegister(dolarPreview, dolarUpdated.getValue(), dolarUpdated.getDate());
        finanzasService.saveRegister(register);

        return dolarUpdated;
    }

    @MessageMapping("/get-dolar")
    @SendTo("/dolar/get-dolar")
    public Dolar getDolar() {
        Dolar dolar = finanzasService.getById(1).get(0);
        return dolar;
    }

    @ApiOperation(value = "Dolar por ID"
            ,notes = "Se obtiene el dolar por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/dolar/{id}")
    public List<Dolar> getById(@PathVariable("id") int id){
        List dolar = finanzasService.getById(id);
        return dolar;
    }

    @ApiOperation(value = "Lista de Registros de Actualización de Dólar"
            ,notes = "Se obtiene una lista paginada de los registros de actualización de dólar")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/dolar/registers")
    public Page<DolarRegister> getDolarRegisters(@PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){

        Page<DolarRegister> list = finanzasService.getAllRegisters(pageable);

        return list;
    }

    @ApiOperation(value = "Lista de Registros de Actualización de TNA Cheques"
            ,notes = "Se obtiene una lista paginada de los registros de actualización de la TNA de Cheques")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tnacheques/registers")
    public Page<TNAChequesRegister> getTNAChequesRegisters(@PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){

        Page<TNAChequesRegister> list = finanzasService.getAllTNAChequesRegisters(pageable);

        return list;
    }

    @ApiOperation(value = "Lista de Registros de Actualización de Tasas de Tarjetas"
            ,notes = "Se obtiene una lista paginada de los registros de actualización de las tasas de tarjetas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tasas/cards/registers")
    public Page<CardsTasasRegister> getAllTNAChequesRegisters(@PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){

        Page<CardsTasasRegister> list = finanzasService.getAllCardsTasasRegisters(pageable);

        return list;
    }

    @ApiOperation(value = "Actualización del valor del tasa"
            ,notes = "Se actualiza el valor de una tasa de interés por su id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINFINANZAS')")
    @PutMapping("/tasas/{id}/value/{value}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @PathVariable(value = "value") Double value){
        Tasas tasaUpdated = finanzasService.getTasaByIdOptional(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tasa not found for this id :: " + id));;

        Double tnaPreview = finanzasService.getTNAByIdOptional(id).get().getValue();
        LocalDateTime date = LocalDateTime.now();

        tasaUpdated.setValue(value);
        tasaUpdated.setDate(date);

        finanzasService.saveTasa(tasaUpdated);

        if (tasaUpdated.getId() == 1){
            TNAChequesRegister register = new TNAChequesRegister(tnaPreview, tasaUpdated.getValue(), tasaUpdated.getDate(), "Cheque Físico");
            finanzasService.saveRegisterTNA(register);
        }

        if (tasaUpdated.getId() > 1){
            CardsTasasRegister register = new CardsTasasRegister(tnaPreview, tasaUpdated.getValue(), tasaUpdated.getDate(), tasaUpdated.getType());
            finanzasService.saveRegisterTNACards(register);
        }

        if (tasaUpdated.getId() == 29){
            TNAChequesRegister register = new TNAChequesRegister(tnaPreview, tasaUpdated.getValue(), tasaUpdated.getDate(), "E-cheq");
            finanzasService.saveRegisterTNA(register);
        }

        return ResponseEntity.ok(tasaUpdated);
    }

    @ApiOperation(value = "Tasa por ID"
            ,notes = "Se obtiene una tasa de interés por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tasas/{id}")
    public List<Tasas> getTasaById(@PathVariable("id") int id){
        List tasas = finanzasService.getTasaById(id);
        return tasas;
    }

    @ApiOperation(value = "Lista de Tasas"
            ,notes = "Se obtiene una lista de tasas de intereses")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tasas")
    public List<Tasas> getTasas(){

        List<Tasas> list = finanzasService.getAll();

        return list;
    }

    @ApiOperation(value = "Actualización de Estado de una Tasa"
            ,notes = "Se actualiza el estado de una tasa a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('ADMINFINANZAS')")
    @PatchMapping("/tasas/{id}/state/{state}")
    public ResponseEntity<Msg> updateState(@PathVariable int id, @PathVariable int state) {

        String message = "";
        Set<User> likes = new HashSet<>();

        try {
            Tasas tasa = finanzasService.getTasaByIdOptional(id).get();
            tasa.setEnable(state);
            finanzasService.saveTasa(tasa);

            if(state == 1){
                return ResponseEntity.status(HttpStatus.OK).body(new Msg("La opción se ha activado", HttpStatus.OK.value()));
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(new Msg("La opción se ha desactivado", HttpStatus.OK.value()));
            }

        } catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

}
