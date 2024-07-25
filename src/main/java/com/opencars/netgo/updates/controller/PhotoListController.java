package com.opencars.netgo.updates.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.updates.entity.PhotoList;
import com.opencars.netgo.updates.service.PhotoListService;
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
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Actualizaciones de la Intranet")
public class PhotoListController {

    @Autowired
    PhotoListService photoListService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "Creación de una actualización"
            ,notes = "Se envía un objeto de tipo PhotoList a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/intranet/update")
    public ResponseEntity<?> create(@Valid @RequestBody PhotoList photoList, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Alguno de los datos no coinciden con el formato esperado"), HttpStatus.BAD_REQUEST);
        String message = "";
        try{
            long idPhotolistSaved = photoListService.savePhotoListWithPhotos(photoList);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Msg("Actualización Guardada", HttpStatus.CREATED.value(), idPhotolistSaved));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Lista de Actualizaciones"
            ,notes = "Se obtiene una lista de actualizaciones paginada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/intranet/updates/list")
    public Page<PhotoList> getUpdates(@PageableDefault(size = 10, page = 0) Pageable pageable){

        Page<PhotoList> list = photoListService.getAllUpdates(pageable);

        return list;
    }

    @ApiOperation(value = "Actualización por ID"
            ,notes = "Se obtiene una actualización a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/intranet/update/{id}")
    public ResponseEntity<PhotoList> getById(@PathVariable("id") Long id){
        PhotoList photoList = photoListService.getOne(id).get();
        return new ResponseEntity(photoList, HttpStatus.OK);
    }

    @ApiOperation(value = "Actualización de vistas de una actualización"
            ,notes = "Se incrementa en uno, la cantidad de vistas de una actualización.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/intranet/update/{id}/views")
    public ResponseEntity<PhotoList> updateViews(@PathVariable("id") Long id) {
        try {

            PhotoList photoList = photoListService.getOne(id).get();

            int views = photoList.getViews();
            views = views + 1;
            photoList.setViews(views);
            photoListService.save(photoList);
            return new ResponseEntity<PhotoList>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Actualización de Likes"
            ,notes = "Se actualizan los likes de una actualización.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/intranet/update/{id}/likes/user/{iduser}")
    public ResponseEntity<PhotoList> updateLikes(@PathVariable("id") Long id, @PathVariable("iduser") int iduser) {
        try {

            PhotoList photoList = photoListService.getOne(id).get();

            SortedSet<User> likes = new TreeSet<>();
            photoList.getLikes().stream().forEach(data ->

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

            photoList.setLikes(usersSet);
            photoListService.save(photoList);
            return new ResponseEntity(new Msg("Likes actualizados", HttpStatus.OK.value()), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(new Msg("Error: " + e, HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Contador de Actualizaciones del día"
            ,notes = "Se otiene la cantidad de circulares publicadas el día actual")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/updates/current/count")
    public int getCountUpdatesCurrents(){
        int count = photoListService.countUpdatesByDate();
        return count;
    }
}
