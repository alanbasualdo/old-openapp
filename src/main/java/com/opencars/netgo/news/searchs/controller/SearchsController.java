package com.opencars.netgo.news.searchs.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.msgs.MsgInt;
import com.opencars.netgo.news.searchs.dto.ListPositions;
import com.opencars.netgo.news.searchs.entity.Searchs;
import com.opencars.netgo.news.searchs.service.SearchsService;
import com.opencars.netgo.users.entity.User;
import com.opencars.netgo.users.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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
@Api(tags = "Controlador de Búsquedas de Personal")
public class SearchsController {

    @Autowired
    SearchsService searchsService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "Creación de una Búsqueda de Personal"
            ,notes = "Se envía un objeto de tipo searchs a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINMKT') or hasRole('ADMINCCHH')")
    @PostMapping("/searchs")
    public  ResponseEntity<MsgInt> create(@Valid @RequestBody Searchs searchs, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);

        String message = "";

        LocalDateTime date = LocalDateTime.now();

        Searchs newSearch =
                new Searchs(
                        searchs.getPosition(),
                        searchs.getFlyer(),
                        searchs.getState(),
                        searchs.getCompany()
                );

        newSearch.setDate(date);

        try{
            searchsService.save(newSearch);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MsgInt("Búsqueda Guardada", HttpStatus.CREATED.value(), newSearch.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de una Búsqueda"
            ,notes = "Se actualiza una búsqueda a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINMKT')")
    @PutMapping("/searchs/{id}")
    public ResponseEntity<MsgInt>  update(@PathVariable(value = "id") int id, @Valid @RequestBody Searchs searchs){
        Searchs searchUpdated = searchsService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Search not found for this id :: " + id));

        String message = "";
        LocalDateTime date = LocalDateTime.now();

        searchUpdated.setPosition(searchs.getPosition());
        searchUpdated.setFlyer(searchs.getFlyer());
        searchUpdated.setState(searchs.getState());
        searchUpdated.setDate(date);

        try{
            searchsService.save(searchUpdated);

            if(Objects.equals(searchs.getState(), "Inactiva")) {

                return ResponseEntity.status(HttpStatus.OK).body(new MsgInt("La búsqueda se ha actualizado con éxito.", HttpStatus.OK.value(), searchUpdated.getId()));

            } else{

                return ResponseEntity.status(HttpStatus.OK).body(new MsgInt("La búsqueda se ha publicado con éxito.", HttpStatus.OK.value()));

            }

        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de Estado de una Búsqueda"
            ,notes = "Se actualiza el estado de una búsqueda a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('ADMINMKT')")
    @PatchMapping("/searchs/{id}/state/{state}")
    public ResponseEntity<Msg> updateState(@PathVariable int id, @PathVariable String state) {

        String message = "";
        SortedSet<User> likes = new TreeSet<>();

        try {
            Searchs search = searchsService.getOne(id).get();
            search.setState(state);

            if(state.equals("Activa")){

                searchsService.save(search);
                return ResponseEntity.status(HttpStatus.OK).body(new Msg("La búsqueda se ha activado", HttpStatus.OK.value()));
            }else{
                search.setLikes(likes);
                search.setViews(0);
                searchsService.save(search);
                return ResponseEntity.status(HttpStatus.OK).body(new Msg("La búsqueda se ha desactivado", HttpStatus.OK.value()));
            }



        } catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Lista de Búsquedas Activas"
            ,notes = "Se obtiene una lista de búsquedas activas.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/searchs/actives")
    public List<Searchs> getActives(){

        List<Searchs> list = searchsService.getActives();

        return list;

    }

    @ApiOperation(value = "Búsqueda por ID"
            ,notes = "Se obtiene una búsqueda específica a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/searchs/id/{id}")
    public List<Searchs> getById(@PathVariable("id") int id){

        List<Searchs> list = searchsService.getById(id);

        return list;
    }

    @ApiOperation(value = "Lista de Búsquedas Inactivas"
            ,notes = "Se obtiene una lista de búsquedas inactivas.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/searchs/inactives")
    public List<Searchs> getInactives(){

        List<Searchs> list = searchsService.getInactives();

        return list;

    }

    @ApiOperation(value = "Contador de Búsquedas en Estado Activas"
            ,notes = "Se obtiene la cantidad de búsquedas activas.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/searchs/count/actives")
    public int countWelcomesActives(){
        int count = searchsService.countByState("Activa");
        return count;
    }

    @ApiOperation(value = "Eliminación de Búsquedas por ID"
            ,notes = "Se elimina una búsqueda de personal a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('ADMINCCHH') or hasRole('ADMINMKT')")
    @DeleteMapping("/searchs/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            searchsService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Búsqueda Eliminada", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Actualización de Likes"
            ,notes = "Se actualizan los likes de una búsqueda.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/searchs/{id}/likes/user/{iduser}")
    public ResponseEntity<Searchs> updateLikes(@PathVariable("id") int id, @PathVariable("iduser") int iduser) {
        try {

            Searchs search = searchsService.getOne(id).get();

            SortedSet<User> likes = new TreeSet<>();
            search.getLikes().stream().forEach(data ->

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

            search.setLikes(usersSet);
            searchsService.save(search);
            return new ResponseEntity(new Msg("Likes actualizados", HttpStatus.OK.value()), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(new Msg("Error: " + e, HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Actualización de vistas de una búsqueda"
            ,notes = "Se incrementa en uno, la cantidad de vistas de una búsqueda.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/searchs/{id}/views")
    public ResponseEntity<Searchs> updateViews(@PathVariable("id") int id) {
        try {

            Searchs search = searchsService.getOne(id).get();

            int views = search.getViews();
            views = views + 1;
            search.setViews(views);
            searchsService.save(search);

            return new ResponseEntity<Searchs>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Lista de Posiciones de Búsquedas Activas"
            ,notes = "Se obtiene una lista con las posiciones de búsquedas activas.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/searchs/positions")
    public List<ListPositions> getWelcomesList(){

        List<ListPositions> list = searchsService.getPositionsActives();

        return list;

    }

}
