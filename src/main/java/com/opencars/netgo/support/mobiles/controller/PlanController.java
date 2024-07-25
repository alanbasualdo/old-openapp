package com.opencars.netgo.support.mobiles.controller;

import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.mobiles.entity.Plan;
import com.opencars.netgo.support.mobiles.service.PlanService;
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

import java.util.List;

@RestController
@RequestMapping("/api/plan")
@CrossOrigin
@Api(tags = "Controlador de Planes de Celulares")
public class PlanController {

    @Autowired
    PlanService planService;

    @ApiOperation(value = "Creación de un Plan de Celular"
            ,notes = "Se envía objeto de tipo Plan a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Plan plan, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        Plan newPlan =
                new Plan(
                        plan.getPlan()
                );
        planService.save(newPlan);

        return new ResponseEntity(new Msg("Plan Guardado"), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Lista de planes de Celulares"
            ,notes = "Se obtiene una lista de marcas de celulares sin paginación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/list")
    public List<Plan> getList(){

        List<Plan> list = planService.getAll();

        return list;
    }

    @ApiOperation(value = "Actualización de un plan de Celulares"
            ,notes = "Se actualiza la información de un plan de celulares")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/plan/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Plan plan){
        Plan planUpdated = planService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found for this id :: " + id));;
        planUpdated.setPlan(plan.getPlan());
        planService.save(planUpdated);

        return ResponseEntity.ok(planUpdated);
    }

    @ApiOperation(value = "Plan de celular por ID"
            ,notes = "Se obtiene un plan de un celular a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/plan/{id}")
    public ResponseEntity<Plan> getById(@PathVariable("id") int id){
        if(!planService.existsById(id))
            return new ResponseEntity(new Msg("No existe"), HttpStatus.NOT_FOUND);
        Plan plan = planService.getOne(id).get();
        return new ResponseEntity(plan, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Planes de Celulares por Nombre"
            ,notes = "Se obtiene una lista de planes de celulares por coincidencia en el nombre")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/list/name/{name}")
    public List<Plan> getPlanByName(@PathVariable("name") String name){

        List<Plan> list = planService.getByName(name);

        return list;
    }
}
