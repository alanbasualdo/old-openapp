package com.opencars.netgo.news.goodbyes.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.news.goodbyes.dto.GoodbyesSummary;
import com.opencars.netgo.news.goodbyes.entity.Goodbyes;
import com.opencars.netgo.news.goodbyes.service.GoodbyesService;
import com.opencars.netgo.users.entity.User;
import com.opencars.netgo.users.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Despedidas")
public class GoodbyesController {

    @Autowired
    GoodbyesService goodbyesService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "Creación de una Despedida"
            ,notes = "Se envía objeto de tipo goodbye a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCCHH') or hasRole('ADMINMKT')")
    @PostMapping("/goodbyes")
    public ResponseEntity<?> create(@RequestBody Goodbyes goodbyes){

        String message = "";

        LocalDate date = LocalDate.now();
        Goodbyes newGoodbye =
                new Goodbyes(
                        goodbyes.getName(),
                        goodbyes.getImgUser(),
                        goodbyes.getBody(),
                        goodbyes.getFooterText(),
                        goodbyes.getCuil()
                );

        newGoodbye.setDate(date);

        try{
            goodbyesService.save(newGoodbye);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Msg("Despedida creada", HttpStatus.CREATED.value()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Lista de Despedidas Actuales"
            ,notes = "Se obtiene una lista de despedidas del día actual")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/goodbyes/current")
    public ResponseEntity<Goodbyes> getCurrents(){
        List<Goodbyes> list = goodbyesService.getCurrents();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @ApiOperation(value = "Contador de Despedidas Actuales"
            ,notes = "Se obtiene la cantidad de despedidas actuales")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/goodbyes/current/count")
    public int getCountGoodbyes(){
        List<Goodbyes> list = goodbyesService.getCurrents();
        int count = list.size();
        return count;
    }

    @ApiOperation(value = "Despedida por Nombre"
            ,notes = "Se obtiene una despedida a través de su nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/goodbyes/{name}")
    public List<Goodbyes> getByName(@PathVariable("name") String name){

        List<Goodbyes> list = goodbyesService.getByName(name);

        return list;
    }

    @ApiOperation(value = "Lista Semanal de Despedidas"
            ,notes = "Se obtiene una lista con las despedidas de los últimos 7 días")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/goodbyes/summary")
    public List<GoodbyesSummary> getWelcomesForSummary(){

        List<GoodbyesSummary> list = goodbyesService.getGoodbyesSummary();

        return list;

    }

    @ApiOperation(value = "Actualización de Likes"
            ,notes = "Se actualizan los likes de una despedida.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/goodbyes/{id}/likes/user/{iduser}")
    public ResponseEntity<Goodbyes> updateLikes(@PathVariable("id") int id, @PathVariable("iduser") int iduser) {
        try {

            Goodbyes goodbyes = goodbyesService.getOne(id).get();

            SortedSet<User> likes = new TreeSet<>();
            goodbyes.getLikes().stream().forEach(data ->

                    likes.add(data)
            );

            List<User> listLikes = new ArrayList<>(likes);

            boolean contain = false;

            for (int i = 0; i < listLikes.size(); i++) {
                if (listLikes.get(i).getId() == iduser) {
                    listLikes.remove(i);
                    contain = true;
                }
            }

            SortedSet<User> usersSet = new TreeSet<>(listLikes);

            User userToAdd = this.userService.getOne(iduser).get();

            if(!contain){
                usersSet.add(userToAdd);
            }

            goodbyes.setLikes(usersSet);
            goodbyesService.save(goodbyes);
            return new ResponseEntity(new Msg("Likes actualizados", HttpStatus.OK.value()), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(new Msg("Error: " + e, HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Actualización de vistas de una despedida"
            ,notes = "Se incrementa en uno, la cantidad de vistas de una despedida.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/goodbyes/{id}/views")
    public ResponseEntity<Goodbyes> updateViews(@PathVariable("id") int id) {
        try {

            Goodbyes goodbyes = goodbyesService.getOne(id).get();

            int views = goodbyes.getViews();
            views = views + 1;
            goodbyes.setViews(views);
            goodbyesService.save(goodbyes);
            return new ResponseEntity<Goodbyes>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

