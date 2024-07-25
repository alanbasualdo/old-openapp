package com.opencars.netgo.Notifications.controller;

import com.opencars.netgo.Notifications.entity.Notifications;
import com.opencars.netgo.Notifications.service.NotificationsService;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.msgs.ResponseNotifications;
import com.opencars.netgo.users.entity.User;
import com.opencars.netgo.users.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Notificaciones")
public class NotificationsController {

    @Autowired
    NotificationsService notificationsService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "Creación de una notificación"
            ,notes = "Se envía un objeto de tipo notifications a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/notifications")
    public ResponseEntity<?> create(@Valid @RequestBody Notifications notifications){

        Notifications newNotification =
                new Notifications(
                        notifications.getTitle(),
                        notifications.getBody(),
                        notifications.getIcon(),
                        notifications.getRoute(),
                        notifications.getUsers(),
                        notifications.getIdTicket()
                );

        try {
            Notifications nDB = notificationsService.save(newNotification);
            return ResponseEntity.status(HttpStatus.CREATED).body(nDB);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }

    }

    @ApiOperation(value = "Actualización de View"
            ,notes = "Se actualizan los views de una notificación.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/notification/{id}/view/user/{iduser}")
    public ResponseEntity<?> updateView(@PathVariable("id") Long id, @PathVariable("iduser") int iduser) {
        try {

            Notifications notification = notificationsService.getOne(id).get();

            SortedSet<User> views = new TreeSet<>();
            notification.getViews().stream().forEach(views::add);

            List<User> listViews = new ArrayList<>(views);

            boolean contain = false;

            for (User listView : listViews) {
                if (listView.getId() == iduser) {
                    contain = true;
                    break;
                }
            }

            SortedSet<User> usersSet = new TreeSet<>(listViews);

            User userToAdd = this.userService.getOne(iduser).get();

            if(!contain){
                usersSet.add(userToAdd);
            }

            notification.setViews(usersSet);
            notificationsService.save(notification);
            List<Notifications> list = notificationsService.getByUser(userService.getOne(iduser).get());
            return new ResponseEntity<>(new ResponseNotifications(list, HttpStatus.OK.value()), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new Msg("Error: " + e, HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Lista de Notificaciones por usuario"
            ,notes = "Se obtiene una lista de notificaciones para un usuario específico")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/notifications/user/{user}")
    public List<Notifications> getByUser(@PathVariable("user") User user){
        List<Notifications> users = notificationsService.getByUser(user);
        return users;
    }

    @ApiOperation(value = "Contador de Notificaciones sin leer de un usuario"
            ,notes = "Se obtiene la cantidad de notificaciones sin leer de un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/notifications/user/{user}/count")
    public int countNotificationsNotViewed(@PathVariable("user") User user){
        int count = notificationsService.getCountNotificationsNotViewed(user);
        return count;
    }

}
