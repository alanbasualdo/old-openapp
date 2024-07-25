package com.opencars.netgo.dms.calidad.controller;

import com.opencars.netgo.dms.calidad.entity.ReportRegister;
import com.opencars.netgo.dms.calidad.service.ReportCalidadService;
import com.opencars.netgo.msgs.Msg;
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

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Reportes")
public class CalidadController {

    @Autowired
    ReportCalidadService reportCalidadService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "Reporte de Calidad por ID"
            ,notes = "Se obtiene un reporte de calidad por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/calidad/reports/{id}")
    public List<ReportRegister> getById(@PathVariable("id") int id){
        List reportRegisters = reportCalidadService.getById(id);
        return reportRegisters;
    }

    @ApiOperation(value = "Actualización de Likes"
            ,notes = "Se actualizan los likes de un reporte.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/calidad/reports/{id}/likes/user/{iduser}")
    public ResponseEntity<ReportRegister> updateLikes(@PathVariable("id") int id, @PathVariable("iduser") int iduser) {
        try {

            ReportRegister reportRegister = reportCalidadService.getOne(id).get();

            SortedSet<User> likes = new TreeSet<>(reportRegister.getLikes());

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

            reportRegister.setLikes(usersSet);
            reportCalidadService.save(reportRegister);
            return new ResponseEntity(new Msg("Likes actualizados", HttpStatus.OK.value()), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(new Msg("Error: " + e, HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Actualización de vistas de un reporte"
            ,notes = "Se incrementa en uno, la cantidad de vistas de un reporte.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/calidad/reports/{id}/views")
    public ResponseEntity<ReportRegister> updateViews(@PathVariable("id") int id) {
        try {

            ReportRegister reportRegister = reportCalidadService.getOne(id).get();

            int views = reportRegister.getViews();
            views = views + 1;
            reportRegister.setViews(views);
            reportCalidadService.save(reportRegister);
            return new ResponseEntity<ReportRegister>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
