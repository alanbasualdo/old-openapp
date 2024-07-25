package com.opencars.netgo.support.mobiles.controller;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.mobiles.entity.Mobiles;
import com.opencars.netgo.support.mobiles.service.MobilesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mobiles")
@CrossOrigin
@Api(tags = "Controlador de Celulares")
public class MobilesController {

    @Autowired
    MobilesService mobilesService;

    @ApiOperation(value = "Creación de un celular"
            ,notes = "Se envía un objeto de tipo Mobiles a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Mobiles mobile, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        if (mobilesService.existsBySn(mobile.getSn()))
            return new ResponseEntity(new Msg("Ya existe un dispositivo para ese número de serie"), HttpStatus.BAD_REQUEST);
        Mobiles newMobile =
                new Mobiles(
                        mobile.getSn(),
                        mobile.getModel(),
                        mobile.getInvoice(),
                        mobile.getUser(),
                        mobile.getAssigned(),
                        mobile.getBranch(),
                        mobile.getObservation(),
                        mobile.getLine(),
                        mobile.getBuyDate()
                );

        mobilesService.save(newMobile);

        return new ResponseEntity(newMobile, HttpStatus.CREATED);

    }

    @ApiOperation(value = "Actualización de un celular"
            ,notes = "Se actualiza la información de un celular")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Mobiles mobile){

        try{
            Mobiles mobileUpdated = mobilesService.getOne(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Mobile not found for this id :: " + id));;
            mobileUpdated.setSn(mobile.getSn());
            mobileUpdated.setModel(mobile.getModel());
            mobileUpdated.setInvoice(mobile.getInvoice());
            mobileUpdated.setUser(mobile.getUser());
            mobileUpdated.setAssigned(mobile.getAssigned());
            mobileUpdated.setBranch(mobile.getBranch());
            mobileUpdated.setObservation(mobile.getObservation());
            mobileUpdated.setLine(mobile.getLine());
            mobileUpdated.setBuyDate(mobile.getBuyDate());

            mobilesService.save(mobileUpdated);

            return ResponseEntity.ok(mobileUpdated);
        }catch (Exception e){
           System.out.println(e.getMessage());
           return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @ApiOperation(value = "Lista de Celulares"
            ,notes = "Se obtiene una lista paginada de celulares")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/list")
    public Page<Mobiles> getMobiles(@PageableDefault(size = 10, page = 0) Pageable pageable){

        Page<Mobiles> list = mobilesService.getMobilesAssignedsPaginated("Asignado", pageable);

        return list;
    }

    @ApiOperation(value = "Asignación/Desasignación de Celulares"
            ,notes = "Se genera un parche al objeto celulares, cambiando el estado de asignación y el usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('ADMINIT')")
    @PatchMapping("/mobiles/{id}/assigned")
    public ResponseEntity<Mobiles> patchMobile(@PathVariable(value = "id") int id, @PathVariable(value = "assigned") String assigned) {

        try {
            Mobiles mobileUpdated = mobilesService.getOne(id).get();
            mobileUpdated.setAssigned(assigned);
            mobilesService.save(mobileUpdated);
            return new ResponseEntity<Mobiles>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Obtención de celular por Estado"
            ,notes = "Se obtiene una lista de celulares por un estado enviado por parámetro.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/mobiles/state/{state}")
    public List<Mobiles> getMobilesByState(@PathVariable("state") String state){

        List<Mobiles> list = mobilesService.getByAssignState(state);

        return list;
    }

    @ApiOperation(value = "Celular por ID"
            ,notes = "Se obtiene un celular específico a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mobiles/{id}")
    public ResponseEntity<Mobiles> getById(@PathVariable("id") int id){
        if(!mobilesService.existsById(id))
            return new ResponseEntity(new Msg("no existe"), HttpStatus.NOT_FOUND);
        Mobiles mobile = mobilesService.getOne(id).get();
        return new ResponseEntity(mobile, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Celulares por Nombre de Usuario"
            ,notes = "Se obtiene una lista de celulares por coincidencia en el nombre de usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mobiles/name/{name}")
    public List<Mobiles> getMobilesByNameUser(@PathVariable("name") String name){

        List<Mobiles> list = mobilesService.getByNameUser(name);

        return list;
    }

    @ApiOperation(value = "Lista de Celulares por Coincidencia en Observación"
            ,notes = "Se obtiene una lista de celulares por coincidencia en observación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mobiles/observation/{coincidence}")
    public List<Mobiles> getMobilesByCoincidenceInObservation(@PathVariable("coincidence") String coincidence){

        List<Mobiles> list = mobilesService.getByCoincidenceInObservation(coincidence);

        return list;
    }

    @ApiOperation(value = "Lista de Celulares por Usuario"
            ,notes = "Se obtiene una lista de celulares por usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mobiles/user/{id}")
    public List<Mobiles> getMobilesByUserId(@PathVariable("id") int id){

        List<Mobiles> list = mobilesService.getByUserId(id);

        return list;
    }

    @ApiOperation(value = "Lista de Celulares por Uso"
            ,notes = "Se obtiene una lista de celulares por uso")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mobiles/use/list")
    public List<Mobiles> getMobilesByUse(){

        List<Mobiles> list = mobilesService.getByUse();

        return list;
    }

    @ApiOperation(value = "Contador Totalidad de Celulares"
            ,notes = "Se obtiene la cantidad total de celulares")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/mobiles/total/count")
    public int getCountTotalMobiles(){
        List<Mobiles> list = mobilesService.getMobilesList();
        int count = list.size();
        return count;
    }

    @ApiOperation(value = "Contador de Celulares Asignados"
            ,notes = "Se obtiene la cantidad de celulares asignados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/mobiles/assigned/count")
    public int getCountAssignedMobiles(){
        List<Mobiles> list = mobilesService.getByAssignState("Asignada");
        int count = list.size();
        return count;
    }

    @ApiOperation(value = "Contador de Celulares En Stock"
            ,notes = "Se obtiene la cantidad de celulares sin asignar")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/mobiles/unassigned/count")
    public int getCountUnassignedMobiles(){
        List<Mobiles> list = mobilesService.getByAssignState("Sin asignar");
        int count = list.size();
        return count;
    }

    @ApiOperation(value = "Contador de Celulares en Devolución"
            ,notes = "Se obtiene la cantidad de celulares en estado de devolución")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/mobiles/devolution/count")
    public int getCountDevolutionMobiles(){
        List<Mobiles> list = mobilesService.getByAssignState("Devolucion");
        int count = list.size();
        return count;
    }

    @ApiOperation(value = "Lista de Celulares por Sucursal"
            ,notes = "Se obtiene una lista de celulares por sucursal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/mobiles/branch/{branch}")
    public List<Mobiles> getPcsByBranch(@PathVariable("branch") Branch branch){

        List<Mobiles> pcs = mobilesService.getByBranch(branch);
        return pcs;
    }
}
