package com.opencars.netgo.news.letters.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.news.letters.dto.LetterList;
import com.opencars.netgo.news.letters.dto.LetterListNotPublished;
import com.opencars.netgo.news.letters.dto.LettersSummary;
import com.opencars.netgo.news.letters.entity.Letter;
import com.opencars.netgo.news.letters.service.LettersService;
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
@Api(tags = "Controlador de Cartas de Presidencia")
public class LettersController {

    @Autowired
    LettersService lettersService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "Creación de una Carta de Presidencia"
            ,notes = "Se envía un objeto de tipo letter a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCCHH')")
    @PostMapping("/letters")
    public ResponseEntity<?> create(@RequestBody Letter letter){

        String message = "";

        LocalDateTime date = LocalDateTime.now();
        LocalDate shortDate = LocalDate.now();

        Letter newLetter =
                new Letter(
                        letter.getTitle(),
                        letter.getDescription(),
                        letter.getPublished()
                );


        String stringDate = LocalDate.now().toString();

        String[] parts = stringDate.split("-");
        int part0 = Integer.parseInt(parts[0]); // yyyy
        int part1 = Integer.parseInt(parts[1]); // MM
        int part2 = Integer.parseInt(parts[2]); // dd

        String month = "";
        switch (part1){
            case 1: {
                month = "enero";
                break;
            }
            case 2: {
                month = "febrero";
                break;
            }
            case 3: {
                month = "marzo";
                break;
            }
            case 4: {
                month = "abril";
                break;
            }
            case 5: {
                month = "mayo";
                break;
            }
            case 6: {
                month = "junio";
                break;
            }
            case 7: {
                month = "julio";
                break;
            }
            case 8: {
                month = "agosto";
                break;
            }
            case 9: {
                month = "septiembre";
                break;
            }
            case 10: {
                month = "octubre";
                break;
            }
            case 11: {
                month = "noviembre";
                break;
            }
            case 12: {
                month = "diciembre";
                break;
            }
        }

        String dateCurrent = part2 + " de " + month + " de " + part0;
        newLetter.setStringDate(dateCurrent);
        newLetter.setDate(date);
        newLetter.setShortDate(shortDate);

        try{
            lettersService.save(newLetter);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Msg("Carta de presidencia creada", HttpStatus.CREATED.value()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Actualización de una Carta de Presidencia"
            ,notes = "Se actualiza una carta de presidencia a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCCHH')")
    @PutMapping("/letters/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Letter letter){
        Letter letterUpdated = lettersService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Letter not found for this id :: " + id));

        LocalDateTime date = LocalDateTime.now();
        LocalDate shortDate = LocalDate.now();
        String stringDate = LocalDate.now().toString();

        String[] parts = stringDate.split("-");
        int part0 = Integer.parseInt(parts[0]); // yyyy
        int part1 = Integer.parseInt(parts[1]); // MM
        int part2 = Integer.parseInt(parts[2]); // dd

        String month = "";
        switch (part1){
            case 1: {
                month = "enero";
                break;
            }
            case 2: {
                month = "febrero";
                break;
            }
            case 3: {
                month = "marzo";
                break;
            }
            case 4: {
                month = "abril";
                break;
            }
            case 5: {
                month = "mayo";
                break;
            }
            case 6: {
                month = "junio";
                break;
            }
            case 7: {
                month = "julio";
                break;
            }
            case 8: {
                month = "agosto";
                break;
            }
            case 9: {
                month = "septiembre";
                break;
            }
            case 10: {
                month = "octubre";
                break;
            }
            case 11: {
                month = "noviembre";
                break;
            }
            case 12: {
                month = "diciembre";
                break;
            }
        }

        String dateCurrent = part2 + " de " + month + " de " + part0;

        letterUpdated.setTitle(letter.getTitle());
        letterUpdated.setDescription(letter.getDescription());
        letterUpdated.setStringDate(dateCurrent);
        letterUpdated.setDate(date);
        letterUpdated.setPublished(letter.getPublished());
        letterUpdated.setShortDate(shortDate);

        lettersService.save(letterUpdated);

        return ResponseEntity.ok(letterUpdated);
    }

    @ApiOperation(value = "Lista de Cartas de Presidencia"
            ,notes = "Se obtiene una lista de cartas de presidencia sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/letters")
    public List<LetterList> getLetterList(){

        List<LetterList> list = lettersService.getAllPublished();

        return list;

    }

    @ApiOperation(value = "Lista de Cartas de Presidencia en Estado Pendiente"
            ,notes = "Se obtiene una lista de cartas de presidencia en estado pendiente de publicación.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCCHH')")
    @GetMapping("/letters/notpublished")
    public List<LetterListNotPublished> getLetterListNotPublished(){

        List<LetterListNotPublished> list = lettersService.getAllNotPublished();

        return list;

    }

    @ApiOperation(value = "Carta de Presidencia por ID"
            ,notes = "Se obtiene una carta de presidencia a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/letters/{id}")
    public List<Letter> getById(@PathVariable("id") int id){
        Letter letter = lettersService.getOne(id).get();
        List<Letter> list = new ArrayList<>();
        list.add(letter);

        return list;
    }

    @ApiOperation(value = "Lista Semanal de Cartas de Presidencia"
            ,notes = "Se obtiene una lista de cartas de presidencia de los últimos 7 días")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/letters/summary")
    public List<LettersSummary> getLetterListForSummary(){

        List<LettersSummary> list = lettersService.getLettersSummary();

        return list;

    }

    @ApiOperation(value = "Lista de Cartas de Presidencia Actuales"
            ,notes = "Se envía un objeto de tipo letter a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/letters/current")
    public ResponseEntity<Letter> getCurrents(){
        List<Letter> list = lettersService.getCurrents();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @ApiOperation(value = "Contador de Carta de Presidencia"
            ,notes = "Se obtiene la cantidad de cartas de presidencia actuales")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/letters/current/count")
    public int getCountLetters(){
        int count = lettersService.countLettersByDate();
        return count;
    }

    @ApiOperation(value = "Contador de Cartas de Presidencia en Estado Pendiente"
            ,notes = "Se obtiene la cantidad de cartas de presidencia en estado pendiente de publicación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCCHH')")
    @GetMapping("/letters/count/notpublished")
    public int getCountLettersNotPublished(){
        int count = lettersService.countLettersNotPublished();
        return count;
    }

    @ApiOperation(value = "Lista de Cartas de Presidencia por Fecha"
            ,notes = "Se obtiene una lista de cartas de presidencia por su fecha")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/letters/date/{date}")
    public ResponseEntity<Letter> getByDate(@PathVariable("date") String date){

        //Recibo un string y lo formateo a LocalDate, porque no se puede pasar como LocalDate en la url
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);

        List<Letter> list = lettersService.getByShortDate(localDate, 1);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @ApiOperation(value = "Eliminación de una Carta de Presidencia por ID"
            ,notes = "Se elimina una carta de presidencia a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('CEO') or hasRole('ADMINCCHH')")
    @DeleteMapping("/letters/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            lettersService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Carta de Presidencia Eliminada", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Actualización de Likes"
            ,notes = "Se actualizan los likes de una carta de presidencia.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/letters/{id}/likes/user/{iduser}")
    public ResponseEntity<Letter> updateLikes(@PathVariable("id") int id, @PathVariable("iduser") int iduser) {
        try {

            Letter letter = lettersService.getOne(id).get();

            SortedSet<User> likes = new TreeSet<>();
            letter.getLikes().stream().forEach(data ->

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

            letter.setLikes(usersSet);
            lettersService.save(letter);
            return new ResponseEntity(new Msg("Likes actualizados", HttpStatus.OK.value()), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(new Msg("Error: " + e, HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Actualización de vistas de una carta de presidencua"
            ,notes = "Se incrementa en uno, la cantidad de vistas de una carta de presidencia.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/letters/{id}/views")
    public ResponseEntity<Letter> updateViews(@PathVariable("id") int id) {
        try {

            Letter letter = lettersService.getOne(id).get();

            int views = letter.getViews();
            views = views + 1;
            letter.setViews(views);
            lettersService.save(letter);
            return new ResponseEntity<Letter>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
