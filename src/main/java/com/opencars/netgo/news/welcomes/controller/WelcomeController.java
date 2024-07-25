package com.opencars.netgo.news.welcomes.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.news.welcomes.dto.ListNames;
import com.opencars.netgo.news.welcomes.entity.Welcome;
import com.opencars.netgo.news.welcomes.service.WelcomeService;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Bienvenidas")
public class WelcomeController {

    @Autowired
    WelcomeService welcomeService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "Creación de una Bienvenida"
            ,notes = "Se envía un objeto de tipo welcome a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCCHH') or hasRole('ADMINMKT') or hasRole('ADMINIT')")
    @PostMapping("/welcomes")
    public ResponseEntity<Msg> create(@RequestBody Welcome welcome){

        String message = "";

        LocalDateTime date = LocalDateTime.now();
        LocalDate shortDate = LocalDate.now();
        Welcome newWelcome =
                new Welcome(
                        welcome.getName(),
                        welcome.getPosition(),
                        welcome.getImg(),
                        welcome.getBranch(),
                        welcome.getCompany(),
                        welcome.getMail(),
                        welcome.getCuil(),
                        welcome.getGender(),
                        welcome.getCategory(),
                        welcome.getText(),
                        welcome.getPublished()
                );

        System.out.println("welcome");
        System.out.println(welcome.getText());

        if(Objects.equals(welcome.getCategory(), "Colaborador") || Objects.equals(welcome.getCategory(), "Colaboradora")){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime entryDate = LocalDateTime.parse(welcome.getText(), formatter);

            newWelcome.setEntryDate(entryDate);

            newWelcome.setShortDate(LocalDate.from(entryDate));
        }else{
            newWelcome.setEntryDate(date);
            newWelcome.setShortDate(shortDate);
        }

        try{
            welcomeService.save(newWelcome);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Msg("Bienvenida Guardada", HttpStatus.CREATED.value()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }

    }

    @ApiOperation(value = "Actualización de la Bienvenida"
            ,notes = "Se actualiza una bienvenida")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('ADMINCCHH') or hasRole('ADMINMKT')")
    @PatchMapping(value = "/welcomes/{id}")
    public ResponseEntity<Msg> changeImgProfile(@PathVariable int id, @Valid @RequestBody Welcome welcome) {

        String message = "";
        LocalDateTime date = LocalDateTime.now();
        LocalDate shortDate = LocalDate.now();

        Welcome welcomeUpdated = welcomeService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
        welcomeUpdated.setName(welcome.getName());
        welcomeUpdated.setCuil(welcome.getCuil());
        welcomeUpdated.setMail(welcome.getMail());
        welcomeUpdated.setPosition(welcome.getPosition());
        welcomeUpdated.setBranch(welcome.getBranch());
        welcomeUpdated.setCompany(welcome.getCompany());
        welcomeUpdated.setImg(welcome.getImg());
        welcomeUpdated.setEntryDate(date);
        welcomeUpdated.setShortDate(shortDate);
        welcomeUpdated.setPublished(welcome.getPublished());

        if(welcomeUpdated.getPublished() == 1){
            if (!Objects.equals(welcomeUpdated.getImg(), "") && welcomeUpdated.getImg() != null ){
                try{
                    welcomeService.save(welcomeUpdated);
                    return ResponseEntity.status(HttpStatus.CREATED).body(new Msg("La bienvenida se ha publicado con éxito.", HttpStatus.CREATED.value()));
                }catch (Exception e) {
                    message = e.getMessage();
                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
                }
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(new Msg("No se puede publicar una bienvenida sin foto.", HttpStatus.OK.value()));
            }
        }else{
            try{
                welcomeService.save(welcomeUpdated);
                return ResponseEntity.status(HttpStatus.CREATED).body(new Msg("La bienvenida se ha actualizado.", HttpStatus.CREATED.value()));
            }catch (Exception e) {
                message = e.getMessage();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
            }
        }

    }

    @ApiOperation(value = "Lista de Bienvenidas Actuales"
            ,notes = "Se obtiene una lista de bienvenidas del día actual")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/welcomes/current")
    public ResponseEntity<User> getCurrents(){
        List<Welcome> list = welcomeService.getCurrents();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Nombres de Bienvenidas Publicadas"
            ,notes = "Se obtiene una lista con los nombres de los colaboradores que se les generó una bienvenida")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/welcomes/names")
    public List<ListNames> getWelcomesList(){

        List<ListNames> list = welcomeService.getPublished();

        return list;

    }

    @ApiOperation(value = "Lista de Nombres de Bienvenidas sin Publicar"
            ,notes = "Se obtiene una lista de bienvenidas en estado pendiente.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCCHH') or hasRole('ADMINMKT')")
    @GetMapping("/welcomes/names/notpublished")
    public List<ListNames> getWelcomesListNotPublished(){

        List<ListNames> list = welcomeService.getNotPublished();

        return list;

    }

    @ApiOperation(value = "Bienvenidas por ID"
            ,notes = "Se obtiene una bienvenida específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/welcomes/{id}")
    public List<Welcome> getById(@PathVariable("id") int id){
        List welcome = welcomeService.getById(id);
        return welcome;
    }

    @ApiOperation(value = "Contador de Bienvenidas Actuales"
            ,notes = "Se obtiene la cantidad de bienvenidas del día actual")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/welcomes/current/count")
    public int getCountWelcomes(){
        int count = welcomeService.countWelcomesPublished();
        return count;
    }

    @ApiOperation(value = "Lista Semanal de Bienvenidas"
            ,notes = "Se obtiene una lista de bienvenidas de los últimos 7 días")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/welcomes/summary")
    public List<Welcome> getWelcomesForSummary(){

        List<Welcome> list = welcomeService.getWelcomesSummary();

        return list;

    }

    @ApiOperation(value = "Contador de Bienvenidas en Estado Pendiente"
            ,notes = "Se obtiene la cantidad de bienvenidas que no han sido publicadas.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCCHH') or hasRole('ADMINMKT')")
    @GetMapping("/welcomes/count/notpublished")
    public int countWelcomesNotPublished(){
        int count = welcomeService.countWelcomesNotPublished();
        return count;
    }

    @ApiOperation(value = "Eliminación de Bienvenidas por ID"
            ,notes = "Se elimina una bienvenida a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('ADMINCCHH') or hasRole('ADMINMKT')")
    @DeleteMapping("/welcomes/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            welcomeService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Bienvenida Eliminada", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Actualización de Likes"
            ,notes = "Se actualizan los likes de una bienvenida.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/welcomes/{id}/likes/user/{iduser}")
    public ResponseEntity<Welcome> updateLikes(@PathVariable("id") int id, @PathVariable("iduser") int iduser) {
        try {

            Welcome welcome = welcomeService.getOne(id).get();

            SortedSet<User> likes = new TreeSet<>();
            welcome.getLikes().stream().forEach(data ->

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

            welcome.setLikes(usersSet);
            welcomeService.save(welcome);
            return new ResponseEntity(new Msg("Likes actualizados", HttpStatus.OK.value()), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(new Msg("Error: " + e, HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Actualización de vistas de una bienvenida"
            ,notes = "Se incrementa en uno, la cantidad de vistas de una bienvenida.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/welcomes/{id}/views")
    public ResponseEntity<Welcome> updateViews(@PathVariable("id") int id) {
        try {

            Welcome welcome = welcomeService.getOne(id).get();

            int views = welcome.getViews();
            views = views + 1;
            welcome.setViews(views);
            welcomeService.save(welcome);
            return new ResponseEntity<Welcome>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
