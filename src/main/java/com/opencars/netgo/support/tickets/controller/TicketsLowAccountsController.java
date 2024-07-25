package com.opencars.netgo.support.tickets.controller;

import com.opencars.netgo.msgs.Message;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.tickets.entity.TicketsLowAccounts;
import com.opencars.netgo.support.tickets.service.TicketsLowAccountsService;
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
@Api(tags = "Controlador de Tickets de Baja de Cuentas")
public class TicketsLowAccountsController {

    @Autowired
    TicketsLowAccountsService ticketsLowAccountsService;

    @ApiOperation(value = "Creación de un Ticket de Baja de Cuentas"
            ,notes = "Se envía un objeto de tipo ticketslowaccounts a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCCHH')")
    @PostMapping("/tickets/lowaccounts")
    public ResponseEntity<?> create(@Valid @RequestBody TicketsLowAccounts ticketsLowAccounts, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Alguno de los datos no coinciden con el formato esperado"), HttpStatus.BAD_REQUEST);

        String message = "";

        TicketsLowAccounts newTicket =
                new TicketsLowAccounts(

                        ticketsLowAccounts.getColaborator(),
                        ticketsLowAccounts.getAccounts(),
                        ticketsLowAccounts.getRedirect(),
                        ticketsLowAccounts.getMembers(),
                        ticketsLowAccounts.getDescription()

                );

        try{
            ticketsLowAccountsService.save(newTicket);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Message("BC Guardado", HttpStatus.CREATED.value(), newTicket.getId()));

        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }
}
