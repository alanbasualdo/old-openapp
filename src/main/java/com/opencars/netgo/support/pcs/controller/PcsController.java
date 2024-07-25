package com.opencars.netgo.support.pcs.controller;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.support.pcs.entity.Pcs;
import com.opencars.netgo.support.pcs.service.PcsService;
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

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Inventario de Pcs")
public class PcsController {

    @Autowired
    PcsService pcsService;

    @ApiOperation(value = "Creación de una Pc"
            ,notes = "Se envía un objeto de tipo pcs a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PostMapping("/pcs")
    public ResponseEntity<?> create(@Valid @RequestBody Pcs pc, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        if (pcsService.existsBySn(pc.getSn()))
            return new ResponseEntity(new Msg("Ya existe un dispositivo para ese número de serie"), HttpStatus.BAD_REQUEST);
        Pcs newPc =
                new Pcs(

                        pc.getSn(),
                        pc.getType(),
                        pc.getBuyDate(),
                        pc.getInvoice(),
                        pc.getModel(),
                        pc.getProcessor(),
                        pc.getMemory(),
                        pc.getDisk(),
                        pc.getUser(),
                        pc.getAssigned(),
                        pc.getCompany(),
                        pc.getBranch()

                );

        pcsService.save(newPc);

        return new ResponseEntity(newPc, HttpStatus.CREATED);

    }

    @ApiOperation(value = "Actualización de una Pc"
            ,notes = "Se actualiza la información de una pc")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @PutMapping("/pcs/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @Valid @RequestBody Pcs pcs){
        Pcs pcUpdated = pcsService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pc not found for this id :: " + id));;
        pcUpdated.setSn(pcs.getSn());
        pcUpdated.setType(pcs.getType());
        pcUpdated.setBuyDate(pcs.getBuyDate());
        pcUpdated.setInvoice(pcs.getInvoice());
        pcUpdated.setModel(pcs.getModel());
        pcUpdated.setProcessor(pcs.getProcessor());
        pcUpdated.setMemory(pcs.getMemory());
        pcUpdated.setDisk(pcs.getDisk());
        pcUpdated.setUser(pcs.getUser());
        pcUpdated.setAssigned(pcs.getAssigned());
        pcUpdated.setCompany(pcs.getCompany());
        pcUpdated.setBranch(pcs.getBranch());

        pcsService.save(pcUpdated);

        return ResponseEntity.ok(pcUpdated);
    }

    @ApiOperation(value = "Lista de Pcs"
            ,notes = "Se obtiene una lista paginada de pcs")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/pcs")
    public Page<Pcs> getPcs(@PageableDefault(size = 10, page = 0) Pageable pageable){

        Page<Pcs> list = pcsService.getPcsAssignedsPaginated("Asignada", pageable);

        return list;
    }

    @ApiOperation(value = "Asignación/Desasignación de Pc"
            ,notes = "Se genera un parche al objeto pcs, cambiando el estado de asignación y el usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('ADMINIT')")
    @PatchMapping("/pcs/{id}/assigned")
    public ResponseEntity<Pcs> patchPc(@PathVariable(value = "id") int id, @PathVariable(value = "assigned") String assigned) {

        try {
            Pcs pcsUpdated = pcsService.getOne(id).get();
            pcsUpdated.setAssigned(assigned);
            pcsService.save(pcsUpdated);
            return new ResponseEntity<Pcs>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Obtención de Pc por Estado"
            ,notes = "Se obtiene una lista de pcs por un estado enviado por parámetro.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/pcs/state/{state}")
    public List<Pcs> getPcsByState(@PathVariable("state") String state){

        List<Pcs> list = pcsService.getByAssignState(state);

        return list;
    }

    @ApiOperation(value = "Pc por ID"
            ,notes = "Se obtiene una pc específica a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/pcs/{id}")
    public ResponseEntity<Pcs> getById(@PathVariable("id") int id){
        if(!pcsService.existsById(id))
            return new ResponseEntity(new Msg("no existe"), HttpStatus.NOT_FOUND);
        Pcs pc = pcsService.getOne(id).get();
        return new ResponseEntity(pc, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de Pcs por Nombre de Usuario"
            ,notes = "Se obtiene una lista de pcs por coincidencia en el nombre de usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/pcs/name/{name}")
    public List<Pcs> getPcsByNameUser(@PathVariable("name") String name){

        List<Pcs> list = pcsService.getByNameUser(name);

        return list;
    }

    @ApiOperation(value = "Lista de Pcs por Número de Serie"
            ,notes = "Se obtiene una lista de pcs por coincidencia en el número de serie")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/pcs/sn/{sn}")
    public List<Pcs> getPcsBySN(@PathVariable("sn") String sn){

        List<Pcs> list = pcsService.getBySN(sn);

        return list;
    }

    @ApiOperation(value = "Contador Totalidad de Pcs"
            ,notes = "Se obtiene la cantidad total de pcs")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/pcs/total/count")
    public int getCountTotalPcs(){
        List<Pcs> list = pcsService.getPcList();
        int count = list.size();
        return count;
    }

    @ApiOperation(value = "Contador de Pcs Asignadas"
            ,notes = "Se obtiene la cantidad de pcs asignadas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/pcs/assigned/count")
    public int getCountAssignedPcs(){
        List<Pcs> list = pcsService.getByAssignState("Asignada");
        int count = list.size();
        return count;
    }

    @ApiOperation(value = "Contador de Pcs En Stock"
            ,notes = "Se obtiene la cantidad de pcs sin asignar")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/pcs/unassigned/count")
    public int getCountUnassignedPcs(){
        List<Pcs> list = pcsService.getByAssignState("Sin asignar");
        int count = list.size();
        return count;
    }

    @ApiOperation(value = "Contador de Pcs en Devolución"
            ,notes = "Se obtiene la cantidad de pcs en estado de devolución")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINIT')")
    @GetMapping("/pcs/devolution/count")
    public int getCountDevolutionPcs(){
        List<Pcs> list = pcsService.getByAssignState("Devolucion");
        int count = list.size();
        return count;
    }

    @ApiOperation(value = "Lista de Pcs|Notebooks por Sucursal"
            ,notes = "Se obtiene una lista de Pcs|Notebooks por sucursal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/pcs/branch/{branch}")
    public List<Pcs> getPcsByBranch(@PathVariable("branch") Branch branch){

        List<Pcs> pcs = pcsService.getPcsByBranch(branch);
        return pcs;
    }

}
