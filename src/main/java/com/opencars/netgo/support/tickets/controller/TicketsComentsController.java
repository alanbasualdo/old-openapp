package com.opencars.netgo.support.tickets.controller;

import com.opencars.netgo.msgs.CommentResponse;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.tickets.entity.TicketsComents;
import com.opencars.netgo.support.tickets.service.TicketsComentsService;
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

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Seguimientos de Tickets")
public class TicketsComentsController {

    @Autowired
    TicketsComentsService ticketsComentsService;

    @ApiOperation(value = "Creación de un Seguimiento de Ticket"
            ,notes = "Se envía un objeto de tipo ticketscoments a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/tickets/comment")
    public ResponseEntity<?> create(@Valid @RequestBody TicketsComents ticketsComents, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Alguno de los datos no coinciden con el formato esperado"), HttpStatus.BAD_REQUEST);

        String message = "";

        LocalDateTime date = LocalDateTime.now();
        TicketsComents newComment =
                new TicketsComents(

                        ticketsComents.getComment(),
                        ticketsComents.getAttacheds(),
                        ticketsComents.getCreator()

                );

        newComment.setDate(date);

        try{
            ticketsComentsService.save(newComment);
            return ResponseEntity.status(HttpStatus.CREATED).body(new CommentResponse("Comentario Guardado", HttpStatus.CREATED.value(), newComment));

        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

}
