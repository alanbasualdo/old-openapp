package com.opencars.netgo.dms.expertises.controller;

import com.opencars.netgo.dms.expertises.dto.DataToExport;
import com.opencars.netgo.dms.expertises.dto.DataToExportOld;
import com.opencars.netgo.dms.expertises.entity.Expertise;
import com.opencars.netgo.dms.expertises.service.ExpertiseService;
import com.opencars.netgo.msgs.Msg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expertise")
@CrossOrigin()
@Api(tags = "Controlador de Peritajes")
public class ExpertiseController {

    @Autowired
    ExpertiseService expertiseService;

    @ApiOperation(value = "Lista de Peritajes"
            ,notes = "Se obtiene una lista de peritajes paginada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USERPERITAJES') or hasRole('ADMINPERITAJES')")
    @GetMapping("/list")
    public Page<Expertise> getAll(@PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Expertise> list = expertiseService.findAll(pageable);
        return list;
    }

    @ApiOperation(value = "Lista de Peritajes para Exportar"
            ,notes = "Se obtiene una lista de peritajes para exportar")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('EXPORTADORPERITAJES')")
    @GetMapping("/export/list/{initDate}/{endDate}")
    public List<DataToExportOld> getAll(@PathVariable String initDate, @PathVariable String endDate){

        LocalDate inDate = LocalDate.parse(initDate);
        LocalDate enDate = LocalDate.parse(endDate);

        List<DataToExportOld> list = expertiseService.getDataToExport(inDate, enDate);

        return list;
    }

    @ApiOperation(value = "Peritaje por ID"
            ,notes = "Se obtiene un peritaje a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USERPERITAJES') or hasRole('ADMINPERITAJES')")
    @GetMapping("/detail/{id}")
    public ResponseEntity<Expertise> getById(@PathVariable("id") Long id){
        if(!expertiseService.existsById(id))
            return new ResponseEntity(new Msg("no existe"), HttpStatus.NOT_FOUND);
        Expertise expertise = expertiseService.getOne(id).get();
        return new ResponseEntity(expertise, HttpStatus.OK);
    }

    @ApiOperation(value = "Creación de un Peritaje"
            ,notes = "Se envía objeto de tipo expertise a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINPERITAJES')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Expertise expertise, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Alguno de los datos no coinciden con el formato esperado"), HttpStatus.BAD_REQUEST);

        LocalDate date = LocalDate.now();

        String message = "";

        try{

            Expertise newExpertise = new Expertise(
                    expertise.getName_client(),
                    expertise.getPhone_client(),
                    expertise.getAssesor(),
                    expertise.getDomain(),
                    expertise.getMark(),
                    expertise.getModel(),
                    expertise.getYear(),
                    expertise.getKm(),
                    expertise.getColor(),
                    expertise.getMotor(),
                    expertise.getTransmision(),
                    expertise.getTraction(),
                    expertise.getFuel(),
                    expertise.getState_motor(),
                    expertise.getObs_motor(),
                    expertise.getCost_motor_rep(),
                    expertise.getState_fluid(),
                    expertise.getObs_fluid(),
                    expertise.getCost_fluid_rep(),
                    expertise.getState_box(),
                    expertise.getObs_box(),
                    expertise.getCost_box_rep(),
                    expertise.getState_traccion4(),
                    expertise.getObs_traccion4(),
                    expertise.getCost_traccion4_rep(),
                    expertise.getState_suspension(),
                    expertise.getObs_suspension(),
                    expertise.getCost_suspension_rep(),
                    expertise.getState_brakes(),
                    expertise.getObs_brakes(),
                    expertise.getCost_brakes_rep(),
                    expertise.getState_wearbrakes(),
                    expertise.getObs_wearbrakes(),
                    expertise.getCost_wearbrakes_rep(),
                    expertise.getState_electric(),
                    expertise.getObs_electric(),
                    expertise.getCost_electric_rep(),
                    expertise.getState_headlights(),
                    expertise.getObs_headlights(),
                    expertise.getCost_headlights_rep(),
                    expertise.getState_upholstered(),
                    expertise.getObs_upholstered(),
                    expertise.getCost_upholstered_rep(),
                    expertise.getState_wheel(),
                    expertise.getObs_wheel(),
                    expertise.getCost_wheel_rep(),
                    expertise.getState_accesories(),
                    expertise.getObs_accesories(),
                    expertise.getCant_cloths(),
                    expertise.getCost_cloths(),
                    expertise.getState_glass(),
                    expertise.getObs_glass(),
                    expertise.getCost_glass_rep(),
                    expertise.getState_patent(),
                    expertise.getObs_patent(),
                    expertise.getCost_patent_rep(),
                    expertise.getState_hail(),
                    expertise.getObs_hail(),
                    expertise.getCost_hail_rep(),
                    expertise.getState_air(),
                    expertise.getObs_air(),
                    expertise.getCost_air_rep(),
                    expertise.getState_dd(),
                    expertise.getMark_dd(),
                    expertise.getState_di(),
                    expertise.getMark_di(),
                    expertise.getState_td(),
                    expertise.getMark_td(),
                    expertise.getState_ti(),
                    expertise.getMark_ti(),
                    expertise.getState_batery(),
                    expertise.getObs_batery(),
                    expertise.getCost_batery_rep(),
                    expertise.getState_missing(),
                    expertise.getObs_missing(),
                    expertise.getCost_missing_rep(),
                    expertise.getAttacheds(),
                    expertise.getPhoto_card(),
                    expertise.getOperationType(),
                    expertise.getPhoto_km(),
                    expertise.getStrapchange(),
                    expertise.getServicekm(),
                    expertise.getOilconsumption(),
                    expertise.getWaterconsumption(),
                    expertise.getCostrep(),
                    expertise.getAppraisal(),
                    expertise.getRepdays(),
                    expertise.getTakingprice(),
                    expertise.getPhoto_service(),
                    expertise.getPhoto_belt(),
                    expertise.getCost_strapchange(),
                    expertise.getCost_service(),
                    expertise.getCost_dd(),
                    expertise.getCost_di(),
                    expertise.getCost_td(),
                    expertise.getCost_ti(),
                    expertise.getUser()
            );

            newExpertise.setDate(date);

            expertiseService.save(newExpertise);

            return ResponseEntity.status(HttpStatus.CREATED).body(new Msg("Peritaje Guardado", HttpStatus.CREATED.value(), newExpertise.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Firma de un peritaje"
            ,notes = "Se firma un peritaje a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/{id}/sign/{sign}")
    public ResponseEntity<Expertise> signExpertise(@PathVariable Long id, @PathVariable String sign) {
        try {
            Expertise expertise = expertiseService.getOne(id).get();
            expertise.setSign(sign);
            expertiseService.save(expertise);
            return new ResponseEntity<Expertise>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Lista de Peritajes por cliente"
            ,notes = "Se obtiene una lista de peritajes por coincidencia en el nombre de cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USERPERITAJES') or hasRole('ADMINPERITAJES')")
    @GetMapping("/client/{name}")
    public List<Expertise> getExpertisesByClient(@PathVariable("name") String name){

        List<Expertise> list = expertiseService.getByClient(name);

        return list;
    }

    @ApiOperation(value = "Peritaje por unidad"
            ,notes = "Se obtiene el peritaje correspondiente a una unidad")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USERPERITAJES') or hasRole('ADMINPERITAJES')")
    @GetMapping("/model/{model}")
    public List<Expertise> getExpertisesByModel(@PathVariable("model") String model){

        List<Expertise> list = expertiseService.getByModel(model);

        return list;
    }

    @ApiOperation(value = "Peritaje por Patente"
            ,notes = "Se obtiene el peritaje correspondiente a una patente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USERPERITAJES') or hasRole('ADMINPERITAJES')")
    @GetMapping("/domain/{domain}")
    public List<Expertise> getExpertisesByDomain(@PathVariable("domain") String domain){

        List<Expertise> list = expertiseService.getByDomain(domain);

        return list;
    }

    @ApiOperation(value = "Eliminación de un Peritaje por ID"
            ,notes = "Se elimina un peritaje a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('SUPERVISORPERITAJES')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable Long id) {
        try{
            expertiseService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Peritaje Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
