package com.opencars.netgo.support.tickets.controller;

import com.opencars.netgo.Notifications.entity.Notifications;
import com.opencars.netgo.Notifications.service.NotificationsService;
import com.opencars.netgo.auth.entity.Sesiones;
import com.opencars.netgo.auth.repository.SesionesRepository;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.pushnotificacions.controller.PushNotificationController;
import com.opencars.netgo.pushnotificacions.dto.PushNotificationRequest;
import com.opencars.netgo.support.tickets.dto.*;
import com.opencars.netgo.support.tickets.entity.*;
import com.opencars.netgo.support.tickets.service.TicketsCategoriesService;
import com.opencars.netgo.support.tickets.service.TicketsService;
import com.opencars.netgo.support.tickets.service.TicketsSubCategoryService;
import com.opencars.netgo.users.entity.User;
import com.opencars.netgo.users.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Tickets")
public class TicketsController {

    @Autowired
    TicketsService ticketsService;

    @Autowired
    PushNotificationController pushNotificationController;

    @Autowired
    UserService userService;

    @Autowired
    SesionesRepository sesionesRepository;

    @Autowired
    TicketsCategoriesService ticketsCategoriesService;

    @Autowired
    TicketsSubCategoryService ticketsSubCategoryService;

    @Autowired
    NotificationsService notificationsService;

    private String route = "";

    @ApiOperation(value = "Creación de un Ticket"
            ,notes = "Se envía un objeto de tipo ticket a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/tickets")
    public ResponseEntity<?> create(@Valid @RequestBody Tickets ticket, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Alguno de los datos no coinciden con el formato esperado"), HttpStatus.BAD_REQUEST);
        String message = "";
        LocalDateTime date = LocalDateTime.now();
        Tickets newTicket =
                new Tickets(
                        ticket.getTitle(),
                        ticket.getDescription(),
                        ticket.getCategory(),
                        ticket.getSubCategory(),
                        ticket.getSubSubCategory(),
                        ticket.getAttacheds(),
                        ticket.getState(),
                        ticket.getApplicant(),
                        new TreeSet<>(ticket.getObservers()),
                        ticket.getNewEntry(),
                        ticket.getLowAccount()
                );
        newTicket.setOpeningDate(date);
        newTicket.setEditDate(date);
        try{
            ticketsService.save(newTicket);
             /*
            TicketsCategories category = ticketsCategoriesService.getOne(newTicket.getCategory().getId()).get();
            List<User> listTechnicians = userService.getTechniciansByCategory(category.getRol().getRolName());

            User applicant = userService.getOne(newTicket.getApplicant().getId()).get();

            TicketsSubcategories subcategory = ticketsSubCategoryService.getOne(newTicket.getSubCategory().getId()).get();

            this.route = "/" + category.getCategory().toLowerCase() + "/" + subcategory.getSubCategory().toLowerCase();

            SortedSet<User> techs = new TreeSet<>();
            SortedSet<User> obs = new TreeSet<>();
            Notifications nTechs = this.notificationsService.newNotification(
                    category.getCategory() + " | Nuevo Ticket - N° " + newTicket.getId() + " - " + newTicket.getTitle(),
                    "Creado por: " + applicant.getName() + "\n\n" + newTicket.getDescription(),
                    route,
                    techs
            );
            Notifications nObs = this.notificationsService.newNotification(
                    category.getCategory() + " | Nuevo Ticket - N° " + newTicket.getId() + " - " + newTicket.getTitle(),
                    "Creado por: " + applicant.getName() + "\n\n" + newTicket.getDescription(),
                    "/soporte/gestiondetickets/espectador",
                    obs
            );
            this.notificationsService.save(nTechs);
            this.notificationsService.save(nObs);
            for (User tech : listTechnicians) {
               // techs.add(tech);
                List<Sesiones> sesionesUser = sesionesRepository.findActivesByColaborator(tech);
                if (sesionesUser.size() > 0) {
                    for (Sesiones sesiones : sesionesUser) {
                        PushNotificationRequest request = new PushNotificationRequest(
                                //nTechs.getId().toString(),
                                "0",
                                category.getCategory() + " | Nuevo Ticket - N° " + newTicket.getId() + " - " + newTicket.getTitle(),
                                "Creado por: " + applicant.getName() + "\n\n" + newTicket.getDescription(),
                                route,
                                "topic",
                                sesiones.getNavToken()
                        );
                        this.pushNotificationController.sendTokenNotification(request);
                    }
                }
            }
            Set<User> observers = ticket.getObservers();
            User[] observersArray = observers.toArray(new User[observers.size()]);
            List<User> observerList = Arrays.asList(observersArray);
            for (User user : observerList) {
                if (!listTechnicians.contains(userService.getOne(user.getId()).get())){
                    //obs.add(user);
                    List<Sesiones> sessionsList = sesionesRepository.findActivesByColaborator(user);
                    if (sessionsList.size() > 0) {
                        for (Sesiones sesiones : sessionsList) {
                            PushNotificationRequest request = new PushNotificationRequest(
                                    //nObs.getId().toString(),
                                    "0",
                                    category.getCategory() + " | Nuevo Ticket - N° " + newTicket.getId() + " - " + newTicket.getTitle(),
                                    "Creado por: " + applicant.getName() + "\n\n" + newTicket.getDescription(),
                                    "/soporte/gestiondetickets/espectador",
                                    "topic",
                                    sesiones.getNavToken()
                            );
                            this.pushNotificationController.sendTokenNotification(request);
                        }
                    }
                }
            }*/
            //this.notificationsService.updateUsers(nTechs.getId(), techs);
            //this.notificationsService.updateUsers(nObs.getId(), obs);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Msg("Ticket Guardado", HttpStatus.CREATED.value(), newTicket.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }

    }

    //Comentarios
    @ApiOperation(value = "Actualización de Seguimientos de un Ticket"
            ,notes = "Se actualiza el arreglo con los seguimientos del ticket")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/tickets/{id}/coments/usercomment/{usercomment}")
    public ResponseEntity<?> updateComents(@PathVariable(value = "id") Long id, @RequestBody Tickets ticket, @PathVariable(value = "usercomment") int usercomment) {
        LocalDateTime date = LocalDateTime.now();
        try {

            User userComent = userService.getOne(usercomment).get();

            Tickets ticketUpdated = ticketsService.getOne(id).get();
            SortedSet<TicketsComents> coments = new TreeSet<>(ticket.getComents());
            SortedSet<User> techs = new TreeSet<>();
            SortedSet<User> obs = new TreeSet<>();
            SortedSet<User> applicant = new TreeSet<>();

            ticketUpdated.setComents(coments);
            ticketUpdated.setEditDate(date);
            ticketsService.save(ticketUpdated);

            //Sesiones técnico asignado

            List<Sesiones> sessionsTechnician = sesionesRepository.findActivesByColaborator(ticketUpdated.getResolved());
            TicketsCategories category = ticketsCategoriesService.getOne(ticketUpdated.getCategory().getId()).get();
            TicketsSubcategories subcategory = ticketsSubCategoryService.getOne(ticket.getSubCategory().getId()).get();

            List<TicketsCategories> listCategories = ticketsCategoriesService.getAll();

            Set<User> observers = ticketUpdated.getObservers();
            User[] observersArray = observers.toArray(new User[observers.size()]);
            TicketsComents[] comentsArray = ticketUpdated.getComents().toArray(new TicketsComents[ticketUpdated.getComents().size()]);
            List<TicketsComents> comentsList = Arrays.asList(comentsArray);
            comentsList.sort(Comparator.comparing(TicketsComents::getId));
            String mensaje = "";
            for (int l = 0; l < comentsList.size(); l++) {
                if (l + 1 == comentsList.size()){
                    mensaje = comentsList.get(l).getComment();
                }
            }

            for (User user : observersArray) {
                if (!Arrays.asList(observersArray).contains(userService.getOne(ticketUpdated.getApplicant().getId()).get()) &&
                        !Arrays.asList(observersArray).contains(userService.getOne(ticketUpdated.getResolved().getId()).get())){
                    obs.add(user);
                }
            }

            Notifications nObs = this.notificationsService.newNotification(
                    category.getCategory() + " | Ticket - N° " + ticketUpdated.getId() + " - " + ticketUpdated.getTitle(),
                    userComent.getName()  + " ha respondido \n\n" + mensaje,
                    "/soporte/gestiondetickets/espectador",
                    obs,
                    ticketUpdated.getId()
            );
            this.notificationsService.save(nObs);

            for (User user: obs) {
                List<Sesiones> sessionsList = sesionesRepository.findActivesByColaborator(user);
                if (!sessionsList.isEmpty()) {
                    for (Sesiones sesiones : sessionsList) {
                        if (user.getId() != usercomment){
                            PushNotificationRequest request = new PushNotificationRequest(
                                    ticketUpdated.getId().toString(),
                                    ticketUpdated.getCategory().getCategory() + " | Ticket N° " + ticketUpdated.getId() + " " + ticketUpdated.getTitle(),
                                    userComent.getName() + " ha respondido \n\n" + mensaje,
                                    "/soporte/gestiondetickets/espectador",
                                    "topic",
                                    sesiones.getNavToken(),
                                    nObs.getId()

                            );
                            this.pushNotificationController.sendTokenNotification(request);
                        }

                    }
                }
            }

            if (ticketUpdated.getApplicant().getId() != usercomment) {
                applicant.add(ticketUpdated.getApplicant());
                Notifications nApplicant = this.notificationsService.newNotification(
                        category.getCategory() + " | Ticket - N° " + ticketUpdated.getId() + " - " + ticketUpdated.getTitle(),
                        userComent.getName()  + " ha respondido \n\n" + mensaje,
                        "/soporte/gestiondetickets/mistickets",
                        applicant,
                        ticketUpdated.getId()
                );
                this.notificationsService.save(nApplicant);
                List<Sesiones> applicantSessions = sesionesRepository.findActivesByColaborator(ticketUpdated.getApplicant());
                if (!applicantSessions.isEmpty()) {
                    for (Sesiones sesion : applicantSessions) {
                        PushNotificationRequest request = new PushNotificationRequest(
                                ticketUpdated.getId().toString(),
                                ticketUpdated.getCategory().getCategory() + " |Ticket N° " + ticketUpdated.getId() + " " + ticketUpdated.getTitle(),
                                userComent.getName() + " ha respondido \n\n" + mensaje,
                                "/soporte/gestiondetickets/mistickets",
                                "topic",
                                sesion.getNavToken(),
                                nApplicant.getId()

                        );

                        this.pushNotificationController.sendTokenNotification(request);

                    }
                }
            }

            listCategories.forEach(c -> {
                if (ticketUpdated.getCategory().getId() == c.getId()){
                    if(ticketUpdated.getCategory().getId() == 1 && ticketUpdated.getSubCategory().getId() > 3){
                        this.route = "/" + c.getCategory().toLowerCase() + "/soporte";
                    }else{
                        this.route = "/" + c.getCategory().toLowerCase() + "/" + subcategory.getSubCategory().toLowerCase();
                    }
                }
            });

            if (ticketUpdated.getResolved().getId() != usercomment) {
                techs.add(ticketUpdated.getResolved());
                Notifications nTechs = this.notificationsService.newNotification(
                        category.getCategory() + " | Ticket - N° " + ticketUpdated.getId() + " - " + ticketUpdated.getTitle(),
                        userComent.getName()  + " ha respondido \n\n" + mensaje,
                        route,
                        techs,
                        ticketUpdated.getId()
                );
                this.notificationsService.save(nTechs);
                if (!sessionsTechnician.isEmpty()) {
                    for (Sesiones sesiones : sessionsTechnician) {
                        PushNotificationRequest request = new PushNotificationRequest(
                                ticketUpdated.getId().toString(),
                                category.getCategory() + " | Ticket N° " + ticketUpdated.getId() + " " + ticketUpdated.getTitle(),
                                userComent.getName() + " ha respondido \n\n" + mensaje,
                                route,
                                "topic",
                                sesiones.getNavToken(),
                                nTechs.getId()
                        );

                        this.pushNotificationController.sendTokenNotification(request);
                    }
                }
            }

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Actualizar observadores
    @ApiOperation(value = "Actualización de Observadores de un Ticket"
            ,notes = "Se actualiza el arreglo con los observadores del ticket")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/tickets/{id}/observers")
    public ResponseEntity<?> updateObservers(@PathVariable(value = "id") Long id, @Valid @RequestBody Tickets ticket) {

        String message = "";

        LocalDateTime date = LocalDateTime.now();

        try {
            Tickets ticketUpdated = ticketsService.getOne(id).get();
            SortedSet<User> observers = new TreeSet<>();
            ticket.getObservers().stream().forEach(data ->
                    observers.add(data)
            );
            ticketUpdated.setObservers(observers);
            ticketUpdated.setEditDate(date);
            ticketsService.save(ticketUpdated);

            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Observadores actualizados", HttpStatus.OK.value()));


        } catch (Exception e) {

            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    //Asignar ticket
    @ApiOperation(value = "Actualización de Técnico de un Ticket"
            ,notes = "Se actualiza el técnico del ticket")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('TECNICOIT') or hasRole('TECNICOPVTA') or hasRole('TECNICOINFRA') or hasRole('TECNICOADM') or hasRole('TECNICOQA') or hasRole('TECNICOCOMPRAS')")
    @PatchMapping("/tickets/{id}/resolved/{resolved}")
    public ResponseEntity<Msg> updateResolved(@PathVariable Long id, @PathVariable User resolved) {
        LocalDateTime date = LocalDateTime.now();
        try {
            Tickets ticket = ticketsService.getOne(id).get();
            ticket.setResolved(resolved);
            ticket.setEditDate(date);
            ticket.setState(new TicketsStates(2));
            ticketsService.save(ticket);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Técnico asignado correctamente.", HttpStatus.OK.value()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Error al asignar técnico.", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    //Cambiar estado de ticket
    @ApiOperation(value = "Actualización de Estado de un Ticket"
            ,notes = "Se actualiza el estado de un ticket")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/tickets/{id}/state/{state}")
    public ResponseEntity<?> changeState(@PathVariable Long id, @PathVariable int state) {

        LocalDateTime date = LocalDateTime.now();
        try {
            Tickets ticket = ticketsService.getOne(id).get();
            ticket.setState(new TicketsStates(state));
            switch (state){
                case 2:
                    ticket.setClosingDate(null);
                case 3:
                    ticket.setClosingDate(null);
                    break;
                case 4:
                    ticket.setClosingDate(date);
                    break;
            }
            ticket.setEditDate(date);
            ticketsService.save(ticket);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Actualización de SubSubcategoría de un Ticket"
            ,notes = "Se actualiza la subsubcategoría de un ticket, a fin de recategorizarlo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('ADMINIT') or hasRole('ADMINPVTA') or hasRole('ADMININFRAESTRUCTURA') or hasRole('ADMINADM') or hasRole('ADMINFINANZAS') or hasRole('ADMINMKT') or hasRole('ADMINQA') or hasRole('ADMINCALIDAD') or hasRole('ADMINCOMPRAS') or hasRole('ADMINLOGISTICA')")
    @PatchMapping("/tickets/{id}/subcategory/{subcategory}/subsubcategory/{subsubcategory}")
    public ResponseEntity<Msg> changeSubSubcategory(@PathVariable Long id, @PathVariable int subcategory, @PathVariable int subsubcategory) {

        try {
            Tickets ticket = ticketsService.getOne(id).get();
            ticket.setSubCategory(new TicketsSubcategories(subcategory));
            ticket.setSubSubCategory(new TicketsSubSubCategories(subsubcategory));
            ticketsService.save(ticket);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Msg("Subcategoría actualizada", HttpStatus.OK.value()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Error", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    //Asignar un ticket de nuevo ingreso
    @ApiOperation(value = "Vinculación con Ticket de Nuevo Ingreso"
            ,notes = "Se actualiza el ticket, vinculando un ticket de nuevo ingreso")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('ADMINCCHH')")
    @PatchMapping("/tickets/{id}/subcategory/{subcategory}/{subticket}")
    public ResponseEntity<?> addNewEntry(@PathVariable Long id, @PathVariable int subcategory, @PathVariable int subticket) {

        switch (subcategory){
            case 1:
                Tickets ticket = ticketsService.getOne(id).get();
                ticket.setNewEntry(new TicketsNewEntry(subticket));
                ticketsService.save(ticket);
                break;

        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Ticket por ID"
            ,notes = "Se obtiene un ticket específico a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tickets/{id}")
    public ResponseEntity<Tickets> getById(@PathVariable("id") Long id){
        if(!ticketsService.existsById(id))
            return new ResponseEntity(new Msg("no existe"), HttpStatus.NOT_FOUND);
        Tickets ticket = ticketsService.getOne(id).get();
        return new ResponseEntity(ticket, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Tickets por Creador"
            ,notes = "Se obtiene una lista de tickets a partir del creador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tickets/applicant/{applicant}")
    public Page<TicketsResume> getByApplicant(@PathVariable("applicant") User applicant, @PageableDefault(size = 20, page = 0, sort = "editDate", direction = Sort.Direction.DESC) Pageable pageable){
        Page<TicketsResume> tickets = ticketsService.getByApplicant(applicant, pageable);
        return tickets;
    }

    @ApiOperation(value = "Lista de Tickets por Observador"
            ,notes = "Se obtiene una lista de tickets para un observador específico")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tickets/observers/{observer}")
    public Page<TicketsResume> getByObserverId(@PathVariable("observer") User observer, @PageableDefault(size = 20, page = 0, sort = "editDate", direction = Sort.Direction.DESC) Pageable pageable){
        Page<TicketsResume> tickets = ticketsService.getByObserver(observer, pageable);
        return tickets;
    }

    @ApiOperation(value = "Lista de Tickets por Categoría"
            ,notes = "Se obtiene una lista de tickets por categoría y subcategoría con paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT') or hasRole('ADMINPVTA') or hasRole('ADMININFRAESTRUCTURA') or hasRole('ADMINADM') or hasRole('ADMINFINANZAS') or hasRole('ADMINMKT') or hasRole('ADMINQA') or hasRole('ADMINCALIDAD') or hasRole('ADMINCOMPRAS') or hasRole('ADMINLOGISTICA')")
    @GetMapping("/tickets/category/{category}/subcategory/{subcategory}")
    public Page<TicketsResume> getTickets(@PathVariable("category") TicketsCategories category, @PathVariable("subcategory") TicketsSubcategories subcategory, @PageableDefault(size = 20, page = 0, sort = "editDate", direction = Sort.Direction.DESC) Pageable pageable){

        Page<TicketsResume> list = ticketsService.getByCategoryAndSubcategory(category, subcategory, pageable);

        return list;
    }

    @ApiOperation(value = "Lista de Tickets por Categoría, Subcategoría y Estado"
            ,notes = "Se obtiene una lista de tickets por categoría, subcategoría y estado con paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT') or hasRole('ADMINPVTA') or hasRole('ADMININFRAESTRUCTURA') or hasRole('ADMINADM') or hasRole('ADMINFINANZAS') or hasRole('ADMINMKT') or hasRole('ADMINQA') or hasRole('ADMINCALIDAD') or hasRole('ADMINCOMPRAS') or hasRole('ADMINLOGISTICA')")
    @GetMapping("/tickets/category/{category}/subcategory/{subcategory}/state/{state}")
    public Page<TicketsResume> getTicketsByCategoryAndState(@PathVariable("category") TicketsCategories category, @PathVariable("subcategory") TicketsSubcategories subcategory, @PathVariable("state") TicketsStates state, @PageableDefault(size = 20, page = 0, sort = "editDate", direction = Sort.Direction.DESC) Pageable pageable){

        Page<TicketsResume> list = ticketsService.getByCategorySubcategoryAndState(category, subcategory, state, pageable);

        return list;
    }

    @ApiOperation(value = "Lista de Tickets de Soporte IT"
            ,notes = "Se obtiene una lista de tickets de soporte IT")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/tickets/category/{category}/supportit")
    public Page<TicketsResume> getTicketsSupportIT(@PathVariable("category") TicketsCategories category,@PageableDefault(size = 20, page = 0, sort = "editDate", direction = Sort.Direction.DESC) Pageable pageable){

        Page<TicketsResume> list = ticketsService.getTicketsSupportIT(category, pageable);

        return list;
    }

    @ApiOperation(value = "Lista de Tickets de Soporte IT por Estado"
            ,notes = "Se obtiene una lista de tickets de soporte IT por Estado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/tickets/category/{category}/state/{state}/supportit")
    public Page<TicketsResume> getTicketsSupportIT(@PathVariable("category") TicketsCategories category, @PathVariable("state") TicketsStates state, @PageableDefault(size = 20, page = 0, sort = "editDate", direction = Sort.Direction.DESC) Pageable pageable){

        Page<TicketsResume> list = ticketsService.getTicketsSupportITByState(category, state, pageable);

        return list;
    }

    @ApiOperation(value = "Lista de Tickets por Categoría y solicitante"
            ,notes = "Se obtiene una lista de tickets por categoría y solicitante")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT') or hasRole('ADMINPVTA') or hasRole('ADMININFRAESTRUCTURA') or hasRole('ADMINADM') or hasRole('ADMINFINANZAS') or hasRole('ADMINMKT') or hasRole('ADMINQA') or hasRole('ADMINCALIDAD') or hasRole('ADMINCOMPRAS') or hasRole('ADMINLOGISTICA')")
    @GetMapping("/tickets/category/{category}/applicant/{applicant}")
    public List<TicketsResume> getByCategoryAndCoincidenceInApplicantName(@PathVariable("category") TicketsCategories category, @PathVariable("applicant") User applicant){

        List<TicketsResume> list = ticketsService.getByCategoryAndCoincidenceInApplicantName(category, applicant);

        return list;
    }

    @ApiOperation(value = "Lista de Tickets por categoría y coincidencia en el id"
            ,notes = "Se obtiene una lista de tickets por categoría y coincidencia en el número de id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT') or hasRole('ADMINPVTA') or hasRole('ADMININFRAESTRUCTURA') or hasRole('ADMINADM') or hasRole('ADMINFINANZAS') or hasRole('ADMINMKT') or hasRole('ADMINQA') or hasRole('ADMINCALIDAD') or hasRole('ADMINCOMPRAS') or hasRole('ADMINLOGISTICA')")
    @GetMapping("/tickets/category/{category}/coincidence/id/{id}")
    public List<TicketsResume> getByIdCoincidence(@PathVariable("category") TicketsCategories category, @PathVariable("id") Long id){

        List<TicketsResume> list = ticketsService.getByIdCoincidence(category, id);
        return list;
    }

    @ApiOperation(value = "Lista de Tickets por Técnico"
            ,notes = "Se obtiene una lista de tickets por técnico asignado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT') or hasRole('ADMINPVTA') or hasRole('ADMININFRAESTRUCTURA') or hasRole('ADMINADM') or hasRole('ADMINFINANZAS') or hasRole('ADMINMKT') or hasRole('ADMINQA') or hasRole('ADMINCALIDAD') or hasRole('ADMINCOMPRAS') or hasRole('ADMINLOGISTICA')")
    @GetMapping("/tickets/technician/{technician}")
    public List<TicketsResume> getByTechnician(@PathVariable("technician") User technician){

        List<TicketsResume> list = ticketsService.getByTechnician(technician);
        return list;

    }

    @ApiOperation(value = "Contador de Tickets por Categoría, SubCategoría y Estado"
            ,notes = "Se obtiene la cantidad de tickets de una subcategoría por su estado, pasada por parámetro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT') or hasRole('ADMINPVTA') or hasRole('ADMININFRAESTRUCTURA') or hasRole('ADMINADM') or hasRole('ADMINFINANZAS') or hasRole('ADMINMKT') or hasRole('ADMINQA') or hasRole('ADMINCALIDAD') or hasRole('ADMINCOMPRAS') or hasRole('ADMINLOGISTICA')")
    @GetMapping("/tickets/category/{category}/subcategory/{subcategory}/state/{state}/count")
    public int countTicketsByCategorySubcategoryAndState(@PathVariable("category") TicketsCategories category, @PathVariable("subcategory") TicketsSubcategories subcategory, @PathVariable("state") TicketsStates state){
        int count = ticketsService.countTicketsByCategorySubcategoryAndState(category, subcategory, state);
        return count;
    }

    @ApiOperation(value = "Contador de Tickets de IT"
            ,notes = "Se obtiene la cantidad de tickets de IT por su estado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT') or hasRole('ADMINPVTA') or hasRole('ADMININFRAESTRUCTURA') or hasRole('ADMINADM') or hasRole('ADMINFINANZAS') or hasRole('ADMINMKT') or hasRole('ADMINQA') or hasRole('ADMINCALIDAD') or hasRole('ADMINCOMPRAS') or hasRole('ADMINLOGISTICA')")
    @GetMapping("/tickets/category/{category}/it/state/{state}/count")
    public int countTicketsIT(@PathVariable("category") TicketsCategories category, @PathVariable("state") TicketsStates state){
        int count = ticketsService.countTicketsIT(category, state);
        return count;
    }

    @ApiOperation(value = "Contador de Tickets de un Observador por Estado"
            ,notes = "Se obtiene la cantidad de tickets de un observador, por estado.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tickets/observer/{observer}/state/{state}/count")
    public int countTicketsObserverByState(@PathVariable("observer") User observer, @PathVariable("state") TicketsStates state){
        int count = ticketsService.countTicketsObserverByState(observer, state);
        return count;
    }

    @ApiOperation(value = "Contador de Tickets por Creador y Estado"
            ,notes = "Se obtiene la cantidad de tickets de un usuario, por estado.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tickets/applicant/{applicant}/state/{state}/count")
    public int countTicketsApplicantByState(@PathVariable("applicant") User applicant, @PathVariable("state") TicketsStates state){
        int count = ticketsService.countTicketsApplicantByState(applicant, state);
        return count;
    }

    @ApiOperation(value = "Contador de Tickets por Categoría y Técnico"
            ,notes = "Se obtiene la cantidad de tickets de una categoría, gestionados por un técnico")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT') or hasRole('ADMINPVTA') or hasRole('ADMININFRAESTRUCTURA') or hasRole('ADMINADM') or hasRole('ADMINFINANZAS') or hasRole('ADMINMKT') or hasRole('ADMINQA') or hasRole('ADMINCALIDAD') or hasRole('ADMINCOMPRAS') or hasRole('ADMINLOGISTICA')")
    @GetMapping("/tickets/category/{category}/technician/{technician}/count")
    public int countTicketsByCategoryAndTechnician(@PathVariable("category") TicketsCategories category, @PathVariable("technician") User technician){
        int count = ticketsService.countTicketsByCategoryAndTechnician(category, technician);
        return count;
    }

    @ApiOperation(value = "Obtener desempeño de técnicos de una categoría"
            ,notes = "Se obtiene el detalle del desempeño de un técnico por una categoría pasada por parámetro.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT') or hasRole('ADMINPVTA') or hasRole('ADMININFRAESTRUCTURA') or hasRole('ADMINADM') or hasRole('ADMINFINANZAS') or hasRole('ADMINMKT') or hasRole('ADMINQA') or hasRole('ADMINCALIDAD') or hasRole('ADMINCOMPRAS') or hasRole('ADMINLOGISTICA')")
    @GetMapping("/tickets/technicians/performance/category/{category}")
    public List<CardTechnician> getPerformanceTechnicians(@PathVariable("category") TicketsCategories category){
        List<CardTechnician> list = ticketsService.getPerformanceTechniciansByCategory(category);
        return list;
    }

    @ApiOperation(value = "Obtener desempeño de técnicos en general"
            ,notes = "Se obtiene el detalle del desempeño de los técnicos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tickets/technicians/performance")
    public List<CardTechniciansResume> getPerformanceTechniciansGeneral(){
        List<CardTechniciansResume> list = ticketsService.getAllTechnicians();
        return list;
    }

    @ApiOperation(value = "Obtener reporte de origen de los tiempos"
            ,notes = "Se obtiene un reporte con la cantidad de tickets por categoría desde el oriden de los tiempos.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT') or hasRole('ADMINPVTA') or hasRole('ADMININFRAESTRUCTURA') or hasRole('ADMINADM') or hasRole('ADMINFINANZAS') or hasRole('ADMINMKT') or hasRole('ADMINQA') or hasRole('ADMINCALIDAD') or hasRole('ADMINCOMPRAS') or hasRole('ADMINLOGISTICA')")
    @GetMapping("/tickets/reports/origin")
    public ReportGraphic getReportOrigin(){
        ReportGraphic reportGraphic = ticketsService.getReportTotalTime();
        return reportGraphic;
    }

    @ApiOperation(value = "Obtener reporte de Tickets del último mes"
            ,notes = "Se obtiene un reporte con la cantidad de tickets por categoría del último mes.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT') or hasRole('ADMINPVTA') or hasRole('ADMININFRAESTRUCTURA') or hasRole('ADMINADM') or hasRole('ADMINFINANZAS') or hasRole('ADMINMKT') or hasRole('ADMINQA') or hasRole('ADMINCALIDAD') or hasRole('ADMINCOMPRAS') or hasRole('ADMINLOGISTICA')")
    @GetMapping("/tickets/reports/lastmonth")
    public ReportGraphic getReportLastMonth(){
        ReportGraphic reportGraphic = ticketsService.getReportLastMonth();
        return reportGraphic;
    }

    @ApiOperation(value = "Obtener reporte de Tickets del día actual"
            ,notes = "Se obtiene un reporte con la cantidad de tickets del día actual")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT') or hasRole('ADMINPVTA') or hasRole('ADMININFRAESTRUCTURA') or hasRole('ADMINADM') or hasRole('ADMINFINANZAS') or hasRole('ADMINMKT') or hasRole('ADMINQA') or hasRole('ADMINCALIDAD') or hasRole('ADMINCOMPRAS') or hasRole('ADMINLOGISTICA')")
    @GetMapping("/tickets/reports/currentday")
    public ReportGraphic getReportCurrentDay(){
        ReportGraphic reportGraphic = ticketsService.getReportCurrentDay();
        return reportGraphic;
    }

    @ApiOperation(value = "Obtener reporte de Tickets por Categoría"
            ,notes = "Se obtiene un reporte con la cantidad de tickets de una categoría pasada por parámetro, por subcategorías")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tickets/reports/category/{category}")
    public ReportGraphic getReportByCategory(@PathVariable("category") TicketsCategories category){
        ReportGraphic reportGraphic = ticketsService.getReportByCategory(category);
        return reportGraphic;
    }

    @ApiOperation(value = "Obtener reporte de Mes a Mes por categoría"
            ,notes = "Se obtiene un reporte con la cantidad de tickets por mes, de la categoría enviada por parámetro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tickets/reports/category/{category}/monthly")
    public LineReport getReportPVTAMonthToMonth(@PathVariable("category") TicketsCategories category){
        LineReport reportGraphic = ticketsService.getReportMonthlyByCategory(category);
        return reportGraphic;
    }

    @ApiOperation(value = "Eliminación de Ticket por ID"
            ,notes = "Se elimina un ticket a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/tickets/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable long id) {
        try{
            ticketsService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Ticket Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

}
