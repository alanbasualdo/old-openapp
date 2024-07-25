package com.opencars.netgo.benefitcars.controller;

import com.opencars.netgo.benefitcars.entity.BenefitCars;
import com.opencars.netgo.benefitcars.entity.StateInternal;
import com.opencars.netgo.benefitcars.entity.StateToColaborator;
import com.opencars.netgo.benefitcars.service.BenefitCarsService;
import com.opencars.netgo.benefitcars.service.StateInternalService;
import com.opencars.netgo.benefitcars.service.StateToColaboratorService;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.users.entity.User;
import com.opencars.netgo.users.service.UserService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Beneficio en Compras de Vehículos")
public class BenefitCarsController {

    @Autowired
    BenefitCarsService benefitCarsService;

    @Autowired
    UserService userService;

    @Autowired
    StateInternalService stateInternalService;

    @Autowired
    StateToColaboratorService stateToColaboratorService;

    @ApiOperation(value = "Creación de solicitud de beneficio en compra de vehículo"
            ,notes = "Se envía objeto de tipo benefitcars a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/benefitcars")
    public ResponseEntity<?> create(@Valid @RequestBody BenefitCars benefitCars, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Msg("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        String message = "";

        if (this.benefitCarsService.userGotBenefitInLastYear(benefitCars.getColaborator())){
            message = "No puede solicitar el beneficio debido a que ha obtenido uno en el último año. Recuerde que podrá solicitarlo una vez se haya cumplido un año desde la última obtención. Para verificar este dato, vaya a -> Gestión -> Compra de Vehículos -> Ver mis solicitudes -> Y aquí verifique la columna fecha de obtención.";
            return ResponseEntity.status(HttpStatus.OK).body(new Msg(message, HttpStatus.OK.value()));
        }else if(this.benefitCarsService.userRequestBenefitInLastYear(benefitCars.getColaborator())) {
            message = "No puede solicitar el beneficio debido a que tiene una solicitud en curso. Podrá crear una nueva solicitud una vez ésta haya sido resuelta. Para verificar este dato, vaya a -> Gestión -> Compra de Vehículos -> Ver mis solicitudes -> Y aquí verifique el estado de la solicitud en curso.";
            return ResponseEntity.status(HttpStatus.OK).body(new Msg(message, HttpStatus.ACCEPTED.value()));
        }else{
            LocalDateTime dateCurrent = LocalDateTime.now();

            BenefitCars newBenefit = new BenefitCars(
                    benefitCars.getColaborator(),
                    benefitCars.getDomain(),
                    benefitCars.getBrand(),
                    benefitCars.getModel(),
                    benefitCars.getFinancing()
            );

            newBenefit.setDateCreated(dateCurrent);
            newBenefit.setStateToColaborator(new StateToColaborator(1));
            newBenefit.setStateInternal(new StateInternal(1));

            try{
                benefitCarsService.save(newBenefit);
                return ResponseEntity.status(HttpStatus.CREATED).body(new Msg("Solicitud Guardada", HttpStatus.CREATED.value(), newBenefit.getId()));
            }catch (Exception e) {
                message = e.getMessage();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
            }
        }
    }

    @ApiOperation(value = "Lista completa solicitudes"
            ,notes = "Se obtiene una lista completa de las solicitudes del beneficio")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('SUPERVISORVO')")
    @GetMapping("/benefitcars/list")
    public List<BenefitCars> getAll(){

        List<BenefitCars> list = benefitCarsService.getAllForVOManager();

        return list;

    }

    @ApiOperation(value = "Lista completa de solicitudes para ser visualizadas por el Gerente General"
            ,notes = "Se obtiene una lista completa de las solicitudes con propuesta por responsable de VO, para ser visualizadas por el Gerente General")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('GERENTEGENERAL')")
    @GetMapping("/benefitcars/generalmanager/list")
    public List<BenefitCars> getAllForGeneralManager(){

        List<BenefitCars> list = benefitCarsService.getAllForGeneralManager();

        return list;

    }

    @ApiOperation(value = "Lista completa de solicitudes para ser visualizadas por CCHH"
            ,notes = "Se obtiene una lista completa de las solicitudes, para ser visualizadas por CCHH")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('CCHH')")
    @GetMapping("/benefitcars/cchhmanager/list")
    public List<BenefitCars> getAllForCCHHManager(){

        List<BenefitCars> list = benefitCarsService.getAllForCCHHManager();

        return list;

    }

    @ApiOperation(value = "Lista completa de solicitudes para ser visualizadas por el Presidente de la Cía"
            ,notes = "Se obtiene una lista completa de las solicitudes, para ser visualizadas por el Presidente de la Cía")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('PRESIDENTE')")
    @GetMapping("/benefitcars/president/list")
    public List<BenefitCars> getAllForPresident(){

        List<BenefitCars> list = benefitCarsService.getAllForPresident();

        return list;

    }

    @ApiOperation(value = "Solicitudes de un colaborador"
            ,notes = "Se obtiene una lista con las solicitudes del beneficio, de un colaborador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/benefitcars/colaborator/{colaborator}")
    public List<BenefitCars> getByColaborator(@PathVariable("colaborator") int colaborator){

        User user = userService.getOne(colaborator).get();

        List<BenefitCars> list = benefitCarsService.getByColaborator(user);

        return list;

    }

    @ApiOperation(value = "Lista de solicitudes para ser visualizadas por un jefe de sucursal"
            ,notes = "Se obtiene una lista con las solicitudes para ser visualizadas por un jefe de sucursal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/benefitcars/branchmanager/{colaborator}")
    public List<BenefitCars> getByBranchManager(@PathVariable("colaborator") int colaborator){

        User user = userService.getOne(colaborator).get();

        List<BenefitCars> list = benefitCarsService.getByBranchManager(user);

        return list;

    }

    @ApiOperation(value = "Lista de solicitudes para ser visualizadas por un gerente de área"
            ,notes = "Se obtiene una lista con las solicitudes para ser visualizadas por un gerente de área")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/benefitcars/areamanager/{colaborator}")
    public List<BenefitCars> getByAreaManager(@PathVariable("colaborator") int colaborator){

        User user = userService.getOne(colaborator).get();

        List<BenefitCars> list = benefitCarsService.getByAreaManager(user);

        return list;

    }

    @ApiOperation(value = "Lista de solicitudes para ser visualizadas por relaciones laborales"
            ,notes = "Se obtiene una lista con las solicitudes para ser visualizadas por relaciones laborales")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINRRLL')")
    @GetMapping("/benefitcars/rrll/list")
    public List<BenefitCars> getByRRLL(){

        List<BenefitCars> list = benefitCarsService.getAllForRRLL();

        return list;

    }

    @ApiOperation(value = "Autorización de un jefe de sucursal"
            ,notes = "Se actualiza el estado de una solicitud a autorizado por jefe de sucursal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('JEFESDESUCURSALES')")
    @PatchMapping(value = "/benefitcars/{id}/authorize/branchmanager/{authorizer}")
    public ResponseEntity<Msg> authorizeByBranchManager(@PathVariable int id, @PathVariable User authorizer) {

        LocalDateTime date = LocalDateTime.now();
        String message = "";

        try {

            StateInternal stateUpdated = stateInternalService.getOne(2).get();

            BenefitCars benefitUpdated = benefitCarsService.getOne(id).get();
            benefitUpdated.setStateInternal(stateUpdated);
            benefitUpdated.setAuthorizeBranchManager(authorizer.getName());
            benefitUpdated.setDateAuthorizeBranchManager(date);
            benefitUpdated.setCheckAuthorizeBranchManager(true);

            benefitCarsService.save(benefitUpdated);

            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Guardado", HttpStatus.OK.value()));


        } catch (Exception e) {
            message = "Error: " + e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Autorización del Gerente del Área"
            ,notes = "Se actualiza el estado de una solicitud a autorizado por gerente del área")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('GERENTEDEAREA')")
    @PatchMapping(value = "/benefitcars/{id}/authorize/areamanager/{authorizer}")
    public ResponseEntity<Msg> authorizeByAreaManager(@PathVariable int id, @PathVariable User authorizer) {

        LocalDateTime date = LocalDateTime.now();
        String message = "";

        try {

            StateInternal stateUpdated = stateInternalService.getOne(3).get();

            BenefitCars benefitUpdated = benefitCarsService.getOne(id).get();
            benefitUpdated.setStateInternal(stateUpdated);
            benefitUpdated.setAuthorizeAreaManager(authorizer.getName());
            benefitUpdated.setDateAuthorizeAreaManager(date);
            benefitUpdated.setCheckAuthorizeAreaManager(true);

            benefitCarsService.save(benefitUpdated);

            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Guardado", HttpStatus.OK.value()));


        } catch (Exception e) {
            message = "Error: " + e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Autorización y carga de propuesta del Responsable de VO"
            ,notes = "Se actualiza el estado de una solicitud a propuesta generada por Responsable de VO")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('SUPERVISORVO')")
    @PatchMapping(value = "/benefitcars/{id}/authorize/vomanager/{authorizer}/amount/{amount}/transf/{transf}/obs/{obs}")
    public ResponseEntity<Msg> authorizeByVOManager(@PathVariable int id, @PathVariable User authorizer, @PathVariable Long amount, @PathVariable Long transf, @PathVariable String obs) {

        LocalDateTime date = LocalDateTime.now();
        String message = "";

        try {

            StateInternal stateUpdated = stateInternalService.getOne(4).get();

            BenefitCars benefitUpdated = benefitCarsService.getOne(id).get();
            benefitUpdated.setStateInternal(stateUpdated);
            benefitUpdated.setAuthorizeVOManager(authorizer.getName());
            benefitUpdated.setDateAuthorizeVOManager(date);
            benefitUpdated.setCheckAuthorizeVOManager(true);
            benefitUpdated.setAmountToAuthorize(amount);
            benefitUpdated.setAmountFinal(amount);
            benefitUpdated.setTransfToAuthorize(transf);
            benefitUpdated.setTransfFinal(transf);
            benefitUpdated.setObsVOManager(obs);

            benefitCarsService.save(benefitUpdated);

            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Guardado", HttpStatus.OK.value()));


        } catch (Exception e) {
            message = "Error: " + e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Autorización y respuesta del Gerente General"
            ,notes = "Se actualiza el estado de una solicitud a autorizado por Gerente General")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('GERENTEGENERAL')")
    @PatchMapping(value = "/benefitcars/{id}/authorize/generalmanager/{authorizer}/amount/{amount}/transf/{transf}/obs/{obs}")
    public ResponseEntity<Msg> authorizeByGeneralManager(@PathVariable int id, @PathVariable User authorizer, @PathVariable Long amount, @PathVariable Long transf, @PathVariable String obs) {

        LocalDateTime date = LocalDateTime.now();
        String message = "";

        try {

            StateInternal stateUpdated = stateInternalService.getOne(5).get();

            BenefitCars benefitUpdated = benefitCarsService.getOne(id).get();
            benefitUpdated.setStateInternal(stateUpdated);
            benefitUpdated.setAuthorizeGeneralManager(authorizer.getName());
            benefitUpdated.setDateAuthorizeGeneralManager(date);
            benefitUpdated.setCheckAuthorizeGeneralManager(true);
            if(amount != 0){
                benefitUpdated.setAmountModifiedByGeneralManager(amount);
                benefitUpdated.setAmountFinal(amount);
            }
            if (transf != 0){
                benefitUpdated.setTransfModifiedByGeneralManager(transf);
                benefitUpdated.setTransfFinal(transf);
            }
            benefitUpdated.setObsGeneralManager(obs);

            benefitCarsService.save(benefitUpdated);

            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Guardado", HttpStatus.OK.value()));


        } catch (Exception e) {
            message = "Error: " + e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Autorización y respuesta de CCHH"
            ,notes = "Se actualiza el estado de una solicitud a autorizado por Capital Humano")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('CCHH')")
    @PatchMapping(value = "/benefitcars/{id}/authorize/cchhmanager/{authorizer}/obs/{obs}")
    public ResponseEntity<Msg> authorizeByCCHHManager(@PathVariable int id, @PathVariable User authorizer, @PathVariable String obs) {

        LocalDateTime date = LocalDateTime.now();
        String message = "";

        try {

            StateInternal stateUpdated = stateInternalService.getOne(6).get();

            BenefitCars benefitUpdated = benefitCarsService.getOne(id).get();
            benefitUpdated.setStateInternal(stateUpdated);
            benefitUpdated.setAuthorizeCCHH(authorizer.getName());
            benefitUpdated.setDateAuthorizeCCHH(date);
            benefitUpdated.setCheckAuthorizeCCHHManager(true);
            benefitUpdated.setObsCCHH(obs);

            benefitCarsService.save(benefitUpdated);

            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Guardado", HttpStatus.OK.value()));


        } catch (Exception e) {
            message = "Error: " + e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Autorización y respuesta del Presidente de la Cía"
            ,notes = "Se actualiza el estado de una solicitud a autorizado por Presidente de la Cía")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('PRESIDENTE')")
    @PatchMapping(value = "/benefitcars/{id}/authorize/president/{authorizer}/amount/{amount}/transf/{transf}/obs/{obs}")
    public ResponseEntity<Msg> authorizeByPresident(@PathVariable int id, @PathVariable User authorizer, @PathVariable Long amount, @PathVariable Long transf, @PathVariable String obs) {

        LocalDateTime date = LocalDateTime.now();
        String message = "";

        try {

            StateInternal stateUpdated = stateInternalService.getOne(7).get();

            BenefitCars benefitUpdated = benefitCarsService.getOne(id).get();
            benefitUpdated.setStateInternal(stateUpdated);
            benefitUpdated.setAuthorizePresident(authorizer.getName());
            benefitUpdated.setDateAuthorizePresident(date);
            benefitUpdated.setCheckAuthorizePresidentManager(true);
            if (amount != 0){
                benefitUpdated.setAmountModifiedByPresident(amount);
                benefitUpdated.setAmountFinal(amount);
            }
            if(transf != 0){
                benefitUpdated.setTransfModifiedByPresident(transf);
                benefitUpdated.setTransfFinal(transf);
            }
            benefitUpdated.setObsPresident(obs);
            if (benefitUpdated.getFinancing().equals("No")){
                benefitUpdated.setEndProposal("Ok");
            }

            benefitCarsService.save(benefitUpdated);

            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Guardado", HttpStatus.OK.value()));


        } catch (Exception e) {
            message = "Error: " + e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Validación y respuesta de Relaciones Laborales"
            ,notes = "Se actualiza el estado de una solicitud a Financiación Propuesta")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('ADMINRRLL')")
    @PatchMapping(value = "/benefitcars/{id}/validate/rrll/{authorizer}/amount/{amount}")
    public ResponseEntity<Msg> validateByRRLL(@PathVariable int id, @PathVariable User authorizer, @PathVariable String amount) {

        LocalDateTime date = LocalDateTime.now();
        String message = "";

        try {

            StateInternal stateUpdated = stateInternalService.getOne(8).get();

            BenefitCars benefitUpdated = benefitCarsService.getOne(id).get();
            benefitUpdated.setStateInternal(stateUpdated);
            benefitUpdated.setCheckAuthorizeRRLL(true);
            benefitUpdated.setRrll(authorizer.getName());
            benefitUpdated.setDateValidateRRLL(date);
            benefitUpdated.setFinalFinancing(amount);
            if (benefitUpdated.getFinancing().equals("Si")){
                benefitUpdated.setEndProposal("Ok");
            }

            benefitCarsService.save(benefitUpdated);

            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Guardado", HttpStatus.OK.value()));


        } catch (Exception e) {
            message = "Error: " + e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Envío de propuesta a colaborador"
            ,notes = "Se actualiza el estado de una solicitud a Propuesta Enviada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('SUPERVISORVO')")
    @PatchMapping(value = "/benefitcars/{id}/sendToColaborator")
    public ResponseEntity<Msg> sendToColaborator(@PathVariable int id) {

        String message = "";

        try {

            StateInternal stateUpdated = stateInternalService.getOne(9).get();
            StateToColaborator stateToColaborator = stateToColaboratorService.getOne(2).get();

            BenefitCars benefitUpdated = benefitCarsService.getOne(id).get();
            benefitUpdated.setStateInternal(stateUpdated);
            benefitUpdated.setStateToColaborator(stateToColaborator);
            benefitUpdated.setNotificateToColaborator("Notificado");
            benefitCarsService.save(benefitUpdated);

            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Guardado", HttpStatus.OK.value()));


        } catch (Exception e) {
            message = "Error: " + e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Acuerdo de propuesta del colaborador"
            ,notes = "Se actualiza el estado de una solicitud a Colaborador de Acuerdo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('USER')")
    @PatchMapping(value = "/benefitcars/{id}/agreement")
    public ResponseEntity<Msg> agreementColaborator(@PathVariable int id) {

        String message = "";

        try {

            StateInternal stateUpdated = stateInternalService.getOne(10).get();

            BenefitCars benefitUpdated = benefitCarsService.getOne(id).get();
            benefitUpdated.setStateInternal(stateUpdated);
            benefitUpdated.setAgreementColaborator("Estoy de acuerdo");
            benefitCarsService.save(benefitUpdated);

            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Guardado", HttpStatus.OK.value()));


        } catch (Exception e) {
            message = "Error: " + e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Beneficio Otorgado"
            ,notes = "Se actualiza el estado de una solicitud a Beneficio Otorgado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('SUPERVISORVO')")
    @PatchMapping(value = "/benefitcars/{id}/granting")
    public ResponseEntity<Msg> confirmGranting(@PathVariable int id) {

        LocalDate date = LocalDate.now();
        String message = "";

        try {

            StateInternal stateUpdated = stateInternalService.getOne(11).get();
            StateToColaborator stateToColaborator = stateToColaboratorService.getOne(3).get();

            BenefitCars benefitUpdated = benefitCarsService.getOne(id).get();
            benefitUpdated.setStateInternal(stateUpdated);
            benefitUpdated.setStateToColaborator(stateToColaborator);
            benefitUpdated.setDateGranting(date);
            benefitCarsService.save(benefitUpdated);

            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Guardado", HttpStatus.OK.value()));


        } catch (Exception e) {
            message = "Error: " + e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
