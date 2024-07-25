package com.opencars.netgo.news.recognitions.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.news.recognitions.dto.RecognitionsList;
import com.opencars.netgo.news.recognitions.entity.Recognition;
import com.opencars.netgo.news.recognitions.service.RecognitionsService;
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
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Reconocimientos")
public class RecognitionsController {

    @Autowired
    RecognitionsService recognitionsService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "Creación de un Reconocimiento"
            ,notes = "Se envía un objeto de tipo recognition a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCCHH') or hasRole('ADMINMKT')")
    @PostMapping("/recognitions")
    public ResponseEntity<?> create(@RequestBody Recognition recognition){

        System.out.println(recognition.getLeader());
        LocalDateTime date = LocalDateTime.now();
        LocalDate shortDate = LocalDate.now();

        String message = "";

        Recognition newRecognition =
                new Recognition(
                        recognition.getType(),
                        recognition.getTitle(),
                        recognition.getBody(),
                        recognition.getLeader(),
                        recognition.getColaborators(),
                        recognition.getManagers(),
                        recognition.getFooterText(),
                        recognition.getPublished()
                );
        newRecognition.setCreatedAt(date);
        newRecognition.setShortDate(shortDate);

        try{
            recognitionsService.save(newRecognition);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Msg("Reconocimiento Guardado", HttpStatus.CREATED.value()));
        }catch (Exception e) {
            message = e.getMessage();
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de un Reconocimiento"
            ,notes = "Se actualiza un reconocimiento a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCCHH') or hasRole('ADMINMKT')")
    @PutMapping("/recognitions/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Recognition recognition){
        Recognition recognitionUpdated = recognitionsService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Letter not found for this id :: " + id));

        LocalDateTime date = LocalDateTime.now();
        LocalDate shortDate = LocalDate.now();

        recognitionUpdated.setType(recognition.getType());
        recognitionUpdated.setTitle(recognition.getTitle());
        recognitionUpdated.setBody(recognition.getBody());
        recognitionUpdated.setCreatedAt(date);
        recognitionUpdated.setLeader(recognition.getLeader());
        recognitionUpdated.setColaborators(recognition.getColaborators());
        recognitionUpdated.setManagers(recognition.getManagers());
        recognitionUpdated.setFooterText(recognition.getFooterText());
        recognitionUpdated.setPublished(recognition.getPublished());
        recognitionUpdated.setShortDate(shortDate);

        recognitionsService.save(recognitionUpdated);

        return ResponseEntity.ok(recognitionUpdated);
    }

    @ApiOperation(value = "Lista de Reconocimientos"
            ,notes = "Se obtiene una lista de reconocimientos sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/recognitions/state/{state}")
    public List<RecognitionsList> getRecognitionsList(@PathVariable("state") int state){

        List<RecognitionsList> list = recognitionsService.getAllByPublishedState(state);

        return list;

    }

    @ApiOperation(value = "Lista de Reconocimientos Actuales"
            ,notes = "Se obtiene una lista de reconocimientos del día actual")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/recognitions/current")
    public ResponseEntity<Recognition> getCurrents(){
        List<Recognition> list = recognitionsService.getCurrents();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @ApiOperation(value = "Contador de Reconocimientos Actuales"
            ,notes = "Se obtiene la cantidad de reconocimientos actuales")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/recognitions/current/count")
    public int getCountRecognitions(){
        int count = recognitionsService.countRecognitionsByDate();
        return count;
    }

    @ApiOperation(value = "Lista de Reconocimientos por Título"
            ,notes = "Se obtiene una lista de reconocimientos por título")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/recognitions/{title}")
    public List<Recognition> getByTitle(@PathVariable("title") String title){
        List <Recognition> list= recognitionsService.getByTitle(title);
        return list;
    }

    @ApiOperation(value = "Reconocimiento por ID"
            ,notes = "Se obtiene un reconocimiento específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/recognitions/id/{id}")
    public List<Recognition> getById(@PathVariable("id") int id){
        Recognition recognition = recognitionsService.getOne(id).get();
        List<Recognition> list = new ArrayList<>();
        list.add(recognition);

        return list;
    }

    @ApiOperation(value = "Lista Semanal de Reconocimientos"
            ,notes = "Se obtiene una lista de reconocimientos de los últimos 7 días")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/recognitions/summary")
    public List<RecognitionsList> getRecognitionsForSummary(){

        List<RecognitionsList> list = recognitionsService.getRecognitionsSummary();

        return list;

    }

    @ApiOperation(value = "Contador de Reconocimientos en Estado Pendiente"
            ,notes = "Se obtiene la cantidad de reconocimientos que aún no han sido publicados.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCCHH') or hasRole('ADMINMKT')")
    @GetMapping("/recognitions/count/notpublished")
    public int countRecognitionsNotPublisheds(){
        int count = recognitionsService.countRecognitionsNotPublisheds();
        return count;
    }

    @ApiOperation(value = "Eliminación de Reconocimiento por ID"
            ,notes = "Se elimina un reconocimiento a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('ADMINCCHH') or hasRole('ADMINMKT')")
    @DeleteMapping("/recognitions/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            recognitionsService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Reconocimiento Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Actualización de Likes"
            ,notes = "Se actualizan los likes de un reconocimiento.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/recognitions/{id}/likes/user/{iduser}")
    public ResponseEntity<Recognition> updateLikes(@PathVariable("id") int id, @PathVariable("iduser") int iduser) {
        try {

            Recognition recognition = recognitionsService.getOne(id).get();

            SortedSet<User> likes = new TreeSet<>();
            recognition.getLikes().stream().forEach(data ->

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

            recognition.setLikes(usersSet);
            recognitionsService.save(recognition);
            return new ResponseEntity(new Msg("Likes actualizados", HttpStatus.OK.value()), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(new Msg("Error: " + e, HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Actualización de vistas de un reconocimiento"
            ,notes = "Se incrementa en uno, la cantidad de vistas de un reconocimiento.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/recognitions/{id}/views")
    public ResponseEntity<Recognition> updateViews(@PathVariable("id") int id) {
        try {

            Recognition recognition = recognitionsService.getOne(id).get();

            int views = recognition.getViews();
            views = views + 1;
            recognition.setViews(views);
            recognitionsService.save(recognition);
            return new ResponseEntity<Recognition>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
