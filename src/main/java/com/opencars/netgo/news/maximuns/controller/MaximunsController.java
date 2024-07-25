package com.opencars.netgo.news.maximuns.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.news.maximuns.entity.Maximuns;
import com.opencars.netgo.news.maximuns.service.MaximunsService;
import com.opencars.netgo.support.pcs.entity.Pcs;
import com.opencars.netgo.users.entity.User;
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

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Maximas")
public class MaximunsController {

    @Autowired
    MaximunsService maximunsService;

    @ApiOperation(value = "Creación de una Máxima"
            ,notes = "Se envía un objeto de tipo maximuns a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINCCHH')")
    @PostMapping("/maximuns")
    public ResponseEntity<?> create(@Valid @RequestBody Maximuns maximun, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);

        //Si la máxima está destacada, pongo el resto como no destacadas
        if (maximun.getOutstanding() == 1){

            List<Maximuns> list = maximunsService.getAll();

            list.stream().forEach(data ->
                    this.updateOutstanding(data.getId(), 0)
            );
        }

        Maximuns newMaximun =
                new Maximuns(
                        maximun.getImg(),
                        maximun.getOutstanding(),
                        maximun.getNumbermaximun(),
                        maximun.getOrientation()

                );
        maximunsService.save(newMaximun);

        return ResponseEntity.ok(newMaximun
        );


    }

    @ApiOperation(value = "Actualización de Imagen"
            ,notes = "Se actualiza la imagen de la máxima")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('ADMINCCHH')")
    @PatchMapping("/maximuns/{id}/img/{img}/outstanding/{outstanding}")
    public ResponseEntity<Maximuns> updateImg(@PathVariable int id, @PathVariable String img, @PathVariable int outstanding) {
        try {

            //Si la máxima está destacada, pongo el resto como no destacadas
            if (outstanding == 1){

                List<Maximuns> list = maximunsService.getAll();

                list.stream().forEach(data ->
                        this.updateOutstanding(data.getId(), 0)
                );
            }

            Maximuns maximun = maximunsService.getOne(id).get();
            if(!Objects.equals(img, "destacar")){
                maximun.setImg(img);
            }
            maximun.setOutstanding(outstanding);
            maximunsService.save(maximun);
            return new ResponseEntity<Maximuns>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Lista de Máximas"
            ,notes = "Se obtiene una lista de máximas sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/maximuns")
    public List<Maximuns> getMaximuns(){

        List<Maximuns> list = maximunsService.getAll();

        return list;
    }

    @ApiOperation(value = "Máximas Destacadas"
            ,notes = "Se obtiene una lista de máximas destacadas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/maximuns/outstanding")
    public List<Maximuns> getOutstanding(){

        List<Maximuns> list = maximunsService.getOutstanding(1);

        return list;
    }

    @ApiOperation(value = "Actualización de Destacada"
            ,notes = "Se actualiza la máxima destacada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('ADMINCCHH')")
    @PatchMapping("/maximuns/{id}/outstanding/{outstanding}")
    public ResponseEntity<Maximuns> updateOutstanding(@PathVariable int id, @PathVariable int outstanding) {
        try {
            Maximuns maximuns = maximunsService.getOne(id).get();
            maximuns.setOutstanding(outstanding);
            maximunsService.save(maximuns);
            return new ResponseEntity<Maximuns>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
