package com.opencars.netgo.support.tickets.controller;

import com.opencars.netgo.msgs.Message;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.tickets.entity.TicketsNewEntry;
import com.opencars.netgo.support.tickets.service.TicketsNewEntryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Tickets de Nuevo Ingreso")
public class TicketsNewEntryController {

    @Autowired
    TicketsNewEntryService ticketsNewEntryService;

    @ApiOperation(value = "Creación de un Ticket de Nuevo Ingreso"
            ,notes = "Se envía un objeto de tipo ticketsnewentry a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCCHH')")
    @PostMapping("/tickets/newentry")
    public ResponseEntity<?> create(@Valid @RequestBody TicketsNewEntry ticketsNewEntry, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Alguno de los datos no coinciden con el formato esperado"), HttpStatus.BAD_REQUEST);

        String message = "";

        TicketsNewEntry newTicket =
                new TicketsNewEntry(

                        ticketsNewEntry.getEntrydate(),
                        ticketsNewEntry.getResponsable(),
                        ticketsNewEntry.getName(),
                        ticketsNewEntry.getCuil(),
                        ticketsNewEntry.getBirthdate(),
                        ticketsNewEntry.getBranch(),
                        ticketsNewEntry.getPayroll(),
                        ticketsNewEntry.getPosition(),
                        ticketsNewEntry.getTools(),
                        ticketsNewEntry.getDescription()

                );

        try{
            ticketsNewEntryService.save(newTicket);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Message("NI Guardado", HttpStatus.CREATED.value(), newTicket.getId()));

        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }
}
