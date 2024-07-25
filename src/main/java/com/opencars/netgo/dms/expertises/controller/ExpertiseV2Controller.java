package com.opencars.netgo.dms.expertises.controller;

import com.opencars.netgo.dms.expertises.dto.DataToExport;
import com.opencars.netgo.dms.expertises.entity.ExpertiseV2;
import com.opencars.netgo.dms.expertises.entity.Missing;
import com.opencars.netgo.dms.expertises.service.ExpertiseV2Service;
import com.opencars.netgo.dms.expertises.service.ItemsControlService;
import com.opencars.netgo.dms.expertises.service.MissingControlService;
import com.opencars.netgo.dms.quoter.entity.TypeOfCars;
import com.opencars.netgo.dms.quoter.service.CotizationService;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/expertisev2")
@CrossOrigin()
@Api(tags = "Controlador de Peritajes V2")
public class ExpertiseV2Controller {

    @Autowired
    ExpertiseV2Service expertiseV2Service;

    @Autowired
    CotizationService cotizationService;

    @Autowired
    MissingControlService missingControlService;

    @Autowired
    ItemsControlService itemsControlService;

    @ApiOperation(value = "Creación de un Peritaje"
            ,notes = "Se envía objeto de tipo expertisev2 a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINPERITAJES')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody ExpertiseV2 expertise, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Alguno de los datos no coinciden con el formato esperado. Revise todos los campos"), HttpStatus.BAD_REQUEST);
        String patent = expertise.getDomain().trim();
        if(!cotizationService.haveCotizationForDomain(patent))
            return new ResponseEntity(new Msg("No hay cotización para esta patente. Solicite cotización al área comercial."), HttpStatus.NOT_ACCEPTABLE);
        if(expertiseV2Service.existsByCotization(this.cotizationService.getLastCotizationForDomain(patent).get()))
            return new ResponseEntity(new Msg("Ya existe un peritaje con esta cotización. Solicite nueva cotización a área comercial para poder guardar el peritaje."), HttpStatus.NOT_ACCEPTABLE);

        String message = "";

        try{

            ExpertiseV2 newExpertise = new ExpertiseV2(
                    expertise.getDomain().trim(),
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
                    expertise.getState_accesories(),
                    expertise.getCant_cloths(),
                    expertise.getState_glass(),
                    expertise.getObs_glass(),
                    expertise.getCost_glass_rep(),
                    expertise.getState_patent(),
                    expertise.getObs_patent(),
                    expertise.getState_hail(),
                    expertise.getObs_hail(),
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
                    expertise.getState_missing(),
                    expertise.getAttacheds(),
                    expertise.getPhoto_card(),
                    expertise.getPhoto_km(),
                    expertise.getStrapchange(),
                    expertise.getServicekm(),
                    expertise.getOilconsumption(),
                    expertise.getWaterconsumption(),
                    expertise.getPhoto_service(),
                    expertise.getPhoto_belt(),
                    expertise.getUser(),
                    expertise.getAccesories(),
                    expertise.getInspect(),
                    expertise.getState_dynamic(),
                    expertise.getObs_dynamic()
            );

            newExpertise.setCotization(this.cotizationService.getLastCotizationForDomain(patent).get());
            TypeOfCars tipoVehiculo = newExpertise.getCotization().getTypeOfCar();
            List<Missing> listMissings = Arrays.asList(expertise.getMissings());

            listMissings = listMissings.stream().map(missing ->
                    new Missing(
                            missing.getId(),
                            missing.getMissing(),
                            this.missingControlService.getPriceByMissingAndTypeOfCars(missing, tipoVehiculo)
                    )
            ).collect(Collectors.toList());
            newExpertise.setMissings(listMissings.toArray(new Missing[listMissings.size()]));

            long costMissings = 0L;
            for (Missing listMissing : listMissings) {
                costMissings = costMissings + listMissing.getPrice();
            }
            newExpertise.setCost_missing_rep(costMissings);
            if(Objects.equals(expertise.getState_wheel().getId(), 2)) {
                newExpertise.setCost_wheel_rep(this.itemsControlService.findPriceByItemAndTypeOfCars("Volante", tipoVehiculo));
            }else{
                newExpertise.setCost_wheel_rep(0L);
            }
            if(expertise.getCant_cloths() > 0 && expertise.getCant_cloths() < 11) {
                newExpertise.setCost_cloths(this.itemsControlService.findPriceByItemAndTypeOfCars("Paño de pintura", tipoVehiculo) * expertise.getCant_cloths());
            }else{
                newExpertise.setCost_cloths(0L);
            }
            if(Objects.equals(expertise.getState_patent().getId(), 2) || Objects.equals(expertise.getState_patent().getId(), 3)) {
                newExpertise.setCost_patent_rep(this.itemsControlService.findPriceByItemAndTypeOfCars("Chapas patente", tipoVehiculo));
            }else{
                newExpertise.setCost_patent_rep(0L);
            }
            if(Objects.equals(expertise.getState_hail().getId(), 2)) {
                newExpertise.setCost_hail_rep(this.itemsControlService.findPriceByItemAndTypeOfCars("Granizo", tipoVehiculo));
            }else{
                newExpertise.setCost_hail_rep(0L);
            }
            if(Objects.equals(expertise.getState_batery().getId(), 2) || Objects.equals(expertise.getState_batery().getId(), 3)) {
                newExpertise.setCost_batery_rep(this.itemsControlService.findPriceByItemAndTypeOfCars("Batería", tipoVehiculo));
            }else{
                newExpertise.setCost_batery_rep(0L);
            }
            if(Objects.equals(expertise.getStrapchange().getId(), 1)){
                newExpertise.setCost_strapchange(this.itemsControlService.findPriceByItemAndTypeOfCars("Correa", tipoVehiculo));
            }else{
                newExpertise.setCost_strapchange(0L);
            }
            if(expertise.getServicekm() == 0){
                newExpertise.setCost_service(this.itemsControlService.findPriceByItemAndTypeOfCars("Servicio", tipoVehiculo));
            }else{
                newExpertise.setCost_service(0L);
            }
            if(Objects.equals(expertise.getState_dd().getId(), 2)) {
                newExpertise.setCost_dd((long) (this.itemsControlService.findPriceByItemAndTypeOfCars("Cubiertas", tipoVehiculo) * 0.6));
            }else if(Objects.equals(expertise.getState_dd().getId(), 3)) {
                newExpertise.setCost_dd(this.itemsControlService.findPriceByItemAndTypeOfCars("Cubiertas", tipoVehiculo));
            }else{
                newExpertise.setCost_dd(0L);
            }
            if(Objects.equals(expertise.getState_di().getId(), 2)) {
                newExpertise.setCost_di((long) (this.itemsControlService.findPriceByItemAndTypeOfCars("Cubiertas", tipoVehiculo) * 0.6));
            }else if(Objects.equals(expertise.getState_di().getId(), 3)) {
                newExpertise.setCost_di(this.itemsControlService.findPriceByItemAndTypeOfCars("Cubiertas", tipoVehiculo));
            }else{
                newExpertise.setCost_di(0L);
            }
            if(Objects.equals(expertise.getState_td().getId(), 2)) {
                newExpertise.setCost_td((long) (this.itemsControlService.findPriceByItemAndTypeOfCars("Cubiertas", tipoVehiculo) * 0.6));
            }else if(Objects.equals(expertise.getState_td().getId(), 3)) {
                newExpertise.setCost_td(this.itemsControlService.findPriceByItemAndTypeOfCars("Cubiertas", tipoVehiculo));
            }else{
                newExpertise.setCost_td(0L);
            }
            if(Objects.equals(expertise.getState_ti().getId(), 2)) {
                newExpertise.setCost_ti((long) (this.itemsControlService.findPriceByItemAndTypeOfCars("Cubiertas", tipoVehiculo) * 0.6));
            }else if(Objects.equals(expertise.getState_ti().getId(), 3)) {
                newExpertise.setCost_ti(this.itemsControlService.findPriceByItemAndTypeOfCars("Cubiertas", tipoVehiculo));
            }else{
                newExpertise.setCost_ti(0L);
            }

            newExpertise.setCostrep(
                    newExpertise.getCost_motor_rep() +
                    newExpertise.getCost_fluid_rep() +
                    newExpertise.getCost_box_rep() +
                    newExpertise.getCost_traccion4_rep() +
                    newExpertise.getCost_suspension_rep() +
                    newExpertise.getCost_brakes_rep() +
                    newExpertise.getCost_wearbrakes_rep() +
                    newExpertise.getCost_electric_rep() +
                    newExpertise.getCost_headlights_rep() +
                    newExpertise.getCost_upholstered_rep() +
                    newExpertise.getCost_wheel_rep() +
                    newExpertise.getCost_cloths() +
                    newExpertise.getCost_glass_rep() +
                    newExpertise.getCost_patent_rep() +
                    newExpertise.getCost_hail_rep() +
                    newExpertise.getCost_air_rep() +
                    newExpertise.getCost_batery_rep() +
                    newExpertise.getCost_missing_rep() +
                    newExpertise.getCost_strapchange() +
                    newExpertise.getCost_service() +
                    newExpertise.getCost_dd() +
                    newExpertise.getCost_di() +
                    newExpertise.getCost_td() +
                    newExpertise.getCost_ti());

            float valueWithoutRoundend = (newExpertise.getCotization().getPriceFinal() - newExpertise.getCostrep());

            float takingPriceRoundend = Math.round(
                    valueWithoutRoundend /
                            1000) * 1000L;
            newExpertise.setTakingprice((long) takingPriceRoundend);

            long adjustedPrice = (long) (valueWithoutRoundend - takingPriceRoundend);
            newExpertise.setAdjustedPrice(adjustedPrice);

            expertiseV2Service.save(newExpertise);

            return ResponseEntity.status(HttpStatus.CREATED).body(new Msg("Peritaje Guardado", HttpStatus.CREATED.value(), newExpertise.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Lista de Peritajes"
            ,notes = "Se obtiene una lista de peritajes paginada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USERPERITAJES') or hasRole('ADMINPERITAJES')")
    @GetMapping("/list")
    public Page<ExpertiseV2> getAll(@PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<ExpertiseV2> list = expertiseV2Service.findAll(pageable);
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
    public List<DataToExport> getAll(@PathVariable String initDate, @PathVariable String endDate){

        LocalDate inDate = LocalDate.parse(initDate);
        LocalDate enDate = LocalDate.parse(endDate);

        List<DataToExport> list = expertiseV2Service.getDataToExport(inDate, enDate);
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
    public ResponseEntity<ExpertiseV2> getById(@PathVariable("id") Long id){
        if(!expertiseV2Service.existsById(id))
            return new ResponseEntity(new Msg("no existe"), HttpStatus.NOT_FOUND);
        ExpertiseV2 expertise = expertiseV2Service.getOne(id).get();
        return new ResponseEntity(expertise, HttpStatus.OK);
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
    public ResponseEntity<?> signExpertise(@PathVariable Long id, @PathVariable String sign) {
        try {
            ExpertiseV2 expertise = expertiseV2Service.getOne(id).get();
            expertise.setSign(sign);
            expertiseV2Service.save(expertise);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Firmado con éxito", HttpStatus.OK.value()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("No se pudo firmar: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
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
    public List<ExpertiseV2> getExpertisesByClient(@PathVariable("name") String name){

        List<ExpertiseV2> list = expertiseV2Service.getByClient(name);

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
    public List<ExpertiseV2> getExpertisesByModel(@PathVariable("model") String model){

        List<ExpertiseV2> list = expertiseV2Service.getByModel(model);

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
    public List<ExpertiseV2> getExpertisesByDomain(@PathVariable("domain") String domain){

        List<ExpertiseV2> list = expertiseV2Service.getByDomain(domain);

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
            expertiseV2Service.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Peritaje Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
