package com.opencars.netgo.sales.viaticos.controller;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.locations.entity.Company;
import com.opencars.netgo.locations.service.SubSectorService;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.msgs.MsgInt;
import com.opencars.netgo.sales.viaticos.dto.*;
import com.opencars.netgo.sales.viaticos.entity.StatesCompras;
import com.opencars.netgo.sales.viaticos.entity.Viaticos;
import com.opencars.netgo.sales.viaticos.service.*;
import com.opencars.netgo.support.tickets.dto.LineReportLong;
import com.opencars.netgo.support.tickets.dto.ReportGraphic;
import com.opencars.netgo.support.tickets.dto.ReportGraphicLong;
import com.opencars.netgo.support.tickets.dto.ReportMultipleLong;
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
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static com.opencars.netgo.support.tickets.service.TicketsService.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Viaticos")
public class ViaticosController {

    @Autowired
    ViaticosService viaticosService;

    @Autowired
    AuthorizerSuperiorService authorizerSuperiorService;

    @Autowired
    AuthorizerToSuperiorService authorizerToSuperiorService;

    @Autowired
    AuthorizerToSuperior2Service authorizerToSuperior2Service;

    @Autowired
    StatesComprasService statesComprasService;

    @Autowired
    TableContableService tableContableService;

    @Autowired
    TableTreasurersService tableTreasurersService;

    @Autowired
    UserService userService;

    @Autowired
    SubSectorService subSectorService;

    @ApiOperation(value = "Creación de un viático"
            ,notes = "Se envía objeto de tipo Viaticos a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/viaticos/create")
    public ResponseEntity<?> create(@Valid @RequestBody Viaticos viaticos, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MsgInt("Alguno de los datos no coinciden con el formato esperado", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        String message = "";
        User authorizer;
        User analyst;
        User treasurer;

        LocalDateTime date = LocalDateTime.now();

        try{

            Viaticos newViatico = new Viaticos(
                    viaticos.getAmount(),
                    viaticos.getBranch(),
                    viaticos.getAttacheds(),
                    viaticos.getConcept(),
                    viaticos.getColaborator(),
                    viaticos.getObservation(),
                    viaticos.getAdvance(),
                    viaticos.getAmountAdvance(),
                    viaticos.getPaidTo()
            );

            newViatico.setState(new StatesCompras(1));

            User colaborator = this.userService.getOne(viaticos.getColaborator().getId()).get();
            User authorizerSuperior = this.authorizerSuperiorService.getOne(1).get().getColaborator();

            //Si esta en la lista de autorizantes de provincia, le asigno un autorizante random de caba
            if (authorizerToSuperiorService.existsByColaborator(viaticos.getColaborator())){
                //Obtengo la lista de caba
                List<User> listCaba = authorizerToSuperior2Service.listColaborators();

                // Seleccionar un colaborador aleatorio de la lista de caba
                Random random = new Random();
                User randomAuthorizer = listCaba.get(random.nextInt(listCaba.size()));

                authorizer = randomAuthorizer;

            //Si esta en la lista de autorizantes de caba, le asigno un autorizante random de provincia
            }else if (authorizerToSuperior2Service.existsByColaborator(viaticos.getColaborator())){
                List<User> listProv = authorizerToSuperiorService.listColaborators();

                // Seleccionar un colaborador aleatorio de la lista de caba
                Random random = new Random();
                User randomAuthorizer = listProv.get(random.nextInt(listProv.size()));

                authorizer = randomAuthorizer;
            }else{
                //Managers contiene usuarios de línea -1, 0 y 2
                //Si el colaborador está en la lista de Managers, lo autoriza Gerente General(o quien está configurado como Superior)
                List<User> managers = this.userService.getManagers();
                if (managers.contains(colaborator)){
                    authorizer = authorizerSuperior;
                }else{
                    Long amountMonthly = 0L;
                    //Obtengo el consumo mensual del colaborador
                    if(this.viaticosService.getSumAmountMonthlyForColaborator(colaborator) != null){
                        amountMonthly = this.viaticosService.getSumAmountMonthlyForColaborator(colaborator);
                    }

                    //Si es mayor a 100mil *Aumentar a 500mil* lo autoriza Gerente General(o quien está configurado como Superior)
                    if (amountMonthly > 500000){
                        authorizer = authorizerSuperior;
                    }else{
                        if (colaborator.getPositions().first().getSubSector().getName().equals("Comercial") || colaborator.getPositions().first().getSubSector().getName().equals("Comercial-PDA")){
                            //Obtengo los datos de la primera posición del usuario para asignar el autorizante, que podrá ser jefe o gerente del área según la línea del colaborador y el monto
                            authorizer = this.viaticosService.getAuthorizerForAmountAndSubsectorComercialOrPDAorMaestranza(colaborator.getPositions().first().getLinea(), colaborator.getPositions().first().getSubSector(), viaticos.getAmount(), colaborator.getPositions().first().getBranchs().first());
                        }else if (colaborator.getPositions().first().getSubSector().getName().equals("Maestranza")){
                            //Obtengo los datos de la primera posición del usuario para asignar el autorizante, que podrá ser jefe o gerente del área según la línea del colaborador y el monto
                            authorizer = this.viaticosService.getAuthorizerForAmountAndSubsectorComercialOrPDAorMaestranza(colaborator.getPositions().first().getLinea(), subSectorService.getByName("Comercial").get(), viaticos.getAmount(), colaborator.getPositions().first().getBranchs().first());
                            //Obtengo los datos de la primera posición del usuario para asignar el autorizante, que podrá ser jefe o gerente del área según la línea del colaborador y el monto
                        }else if (colaborator.getPositions().first().getSubSector().getName().equals("Garantías")){
                            authorizer = this.viaticosService.getAuthorizerForAmountAndSubsector(colaborator.getPositions().first().getLinea(), subSectorService.getByName("Postventa").get() , viaticos.getAmount(), colaborator.getBranch());
                        }else if (colaborator.getPositions().first().getSubSector().getName().equals("Análisis de Tesorería")){
                            authorizer = this.viaticosService.getAuthorizerForAmountAndSubsector(colaborator.getPositions().first().getLinea(), subSectorService.getByName("Tesorería").get() , viaticos.getAmount(), colaborator.getBranch());
                        }else{
                            authorizer = this.viaticosService.getAuthorizerForAmountAndSubsector(colaborator.getPositions().first().getLinea(), colaborator.getPositions().first().getSubSector(), viaticos.getAmount(), colaborator.getBranch());
                        }
                    }
                }
            }

            newViatico.setAuthorizer(authorizer);

            double amountFinal = 0;
            double amountDif = 0;
            String legend = "";
            if (viaticos.getAdvance().equals("Si")){
                amountFinal = viaticos.getAmountAdvance() - viaticos.getAmount();
                if(amountFinal > 0){
                    legend = "Monto a devolver: " + amountFinal;
                    amountDif = amountFinal;
                }else{
                    amountDif = amountFinal;
                    amountFinal = Math.abs(amountFinal);
                    legend = "Monto a transferir: " + amountFinal;
                }
            }

            newViatico.setLegend(legend);
            newViatico.setAmountDif(amountDif);

            if (tableContableService.existByBranch(viaticos.getBranch())){
                analyst = tableContableService.getByBranch(viaticos.getBranch()).get().getAnalyst();
                newViatico.setAnalyst(analyst);
            }

            if (tableTreasurersService.existByBranch(viaticos.getBranch())){
                treasurer = tableTreasurersService.getByBranch(viaticos.getBranch()).get().getTreasurer();
                newViatico.setTreasurer(treasurer);
            }

            newViatico.setDateInit(date);

            viaticosService.save(newViatico);

            return ResponseEntity.status(HttpStatus.CREATED).body(new Msg("Registro Guardado", HttpStatus.CREATED.value(), newViatico.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Obtener lista de todas los viáticos pendientes"
            ,notes = "Se obtiene una lista de todas los viáticos pendientes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viaticos/list/pendings/all")
    public List<Viaticos> getAllPendingsForClose(){
        List<Viaticos> list = viaticosService.getAllPendingsForClose();
        return list;
    }

    @ApiOperation(value = "Obtener lista de analistas contables"
            ,notes = "Se obtiene una lista de analistas contables")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viaticos/list/analysts")
    public List<User> getAnalystsList(){
        List<User> list = viaticosService.listAnalysts();
        return list;
    }

    @ApiOperation(value = "Obtener lista de tesoreros"
            ,notes = "Se obtiene una lista de tesoreros")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viaticos/list/treasurers")
    public List<User> getTreasurersList(){
        List<User> list = viaticosService.listTreasurers();
        return list;
    }

    @ApiOperation(value = "Lista de viáticos pendientes de carga por analistas"
            ,notes = "Se obtiene una lista de viáticos pendientes de carga por analistas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viaticos/analysts/pendings")
    public Page<Viaticos> getAllPendingsForAnalysts(@PageableDefault(size = 20, page = 0, sort = "dateAuthorized", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Viaticos> list = viaticosService.getByAnalystsPendings(pageable);
        return list;
    }

    @ApiOperation(value = "Lista de viáticos pendientes de pago por tesoreros"
            ,notes = "Se obtiene una lista de viáticos pendientes de pago por tesoreros")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viaticos/treasurers/pendings")
    public Page<Viaticos> getAllPendingsForTreasurers(@PageableDefault(size = 20, page = 0, sort = "dateLoaded", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Viaticos> list = viaticosService.getByTreasurersPendings(pageable);
        return list;
    }

    @ApiOperation(value = "Lista de viáticos por colaborador"
            ,notes = "Se obtiene una lista de viáticos de un colaborador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viaticos/user/{user}")
    public Page<Viaticos> getAllFromUser(@PathVariable("user") User user, @PageableDefault(size = 20, page = 0, sort = "dateInit", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Viaticos> list = viaticosService.getByColaborator(user, pageable);
        return list;
    }

    @ApiOperation(value = "Lista de viáticos sin autorizar, por autorizante"
            ,notes = "Se obtiene una lista de viáticos sin autorizar, por autorizante")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viaticos/unauthorizeds/authorizer/{authorizer}")
    public Page<Viaticos> getUnauthorizedFromAutorizer(@PathVariable("authorizer") User authorizer, @PageableDefault(size = 20, page = 0, sort = "dateInit", direction = Sort.Direction.ASC) Pageable pageable){
        Page<Viaticos> list = viaticosService.getByAuthorizerAndState(authorizer, new StatesCompras(1), pageable);
        return list;
    }

    @ApiOperation(value = "Lista de viáticos sin cargar, por analista contable"
            ,notes = "Se obtiene una lista de viáticos sin cargar en quiter, por analista contable")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viaticos/unloads/analyst/{analyst}")
    public Page<Viaticos> getUnloadFromAnalyst(@PathVariable("analyst") User analyst, @PageableDefault(size = 20, page = 0, sort = "dateInit", direction = Sort.Direction.ASC) Pageable pageable){
        Page<Viaticos> list = viaticosService.getByAnalystAndState(analyst, new StatesCompras(2), pageable);
        return list;
    }

    @ApiOperation(value = "Lista de viáticos rechazados, por autorizante"
            ,notes = "Se obtiene una lista de viáticos rechazados, por autorizante")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viaticos/rejecteds/authorizer/{authorizer}")
    public Page<Viaticos> getRejectedsByAutorizer(@PathVariable("authorizer") User authorizer, @PageableDefault(size = 20, page = 0, sort = "dateEnd", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Viaticos> list = viaticosService.getByAuthorizerAndState(authorizer, new StatesCompras(5), pageable);
        return list;
    }

    @ApiOperation(value = "Lista de viáticos rechazados, por analista contable"
            ,notes = "Se obtiene una lista de viáticos rechazados, por analista contable")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viaticos/rejecteds/analyst/{analyst}")
    public Page<Viaticos> getRejectedsByAnalyst(@PathVariable("analyst") User analyst, @PageableDefault(size = 20, page = 0, sort = "dateEnd", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Viaticos> list = viaticosService.getByAnalystAndState(analyst, new StatesCompras(5), pageable);
        return list;
    }

    @ApiOperation(value = "Lista de viáticos autorizados por autorizante"
            ,notes = "Se obtiene una lista de viáticos autorizados por autorizante")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viaticos/authorizeds/authorizer/{authorizer}")
    public Page<Viaticos> getAuthorizedsFromAutorizer(@PathVariable("authorizer") User authorizer, @PageableDefault(size = 20, page = 0, sort = "dateAuthorized", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Viaticos> list = viaticosService.getAllNotRejectedAndAuthorizeds(authorizer, pageable);
        return list;
    }

    @ApiOperation(value = "Lista de viáticos cargados en Quiter, por analista contable"
            ,notes = "Se obtiene una lista de viáticos cargados en Quiter, por analista contable")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viaticos/loads/analyst/{analyst}")
    public Page<Viaticos> getLoadsFromAnalyst(@PathVariable("analyst") User analyst, @PageableDefault(size = 20, page = 0, sort = "dateLoaded", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Viaticos> list = viaticosService.getAllNotRejectedAndLoaded(analyst, pageable);
        return list;
    }

    @ApiOperation(value = "Lista de viáticos sin pagar, por tesorero"
            ,notes = "Se obtiene una lista de viáticos sin pagar, por tesorero")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('TESORERO') or hasRole('SUPERVISORTESORERIAS')")
    @GetMapping("/viaticos/pendings/treasurers/{treasurer}")
    public Page<Viaticos> getPendingsByTreasurer(@PathVariable("treasurer") User treasurer, @PageableDefault(size = 20, page = 0, sort = "dateLoaded", direction = Sort.Direction.ASC) Pageable pageable){
        Page<Viaticos> list = viaticosService.getByTreasurerAndState(treasurer, new StatesCompras(3), pageable);
        return list;
    }

    @ApiOperation(value = "Lista de viáticos pagados, por tesorero"
            ,notes = "Se obtiene una lista de viáticos pagados, por tesorero")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('TESORERO') or hasRole('SUPERVISORTESORERIAS')")
    @GetMapping("/viaticos/paids/treasurers/{treasurer}")
    public Page<Viaticos> getPaidsByTreasurer(@PathVariable("treasurer") User treasurer, @PageableDefault(size = 20, page = 0, sort = "dateLoaded", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Viaticos> list = viaticosService.getByTreasurerAndState(treasurer, new StatesCompras(4), pageable);
        return list;
    }

    @ApiOperation(value = "Autorización de un viático"
            ,notes = "Se actualiza el estado de un viático a autorizado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('AUTORIZANTEVIATICOS')")
    @PatchMapping(value = "/viaticos/{id}/authorize/{authorizer}")
    public ResponseEntity<Msg> authorizeViatico(@PathVariable int id, @PathVariable User authorizer) {

        LocalDateTime date = LocalDateTime.now();
        String message = "";

        try {

            StatesCompras stateUpdated = statesComprasService.getOne(2).get();

            Viaticos viaticoUpdated = viaticosService.getOne(id).get();
            viaticoUpdated.setState(stateUpdated);
            viaticoUpdated.setDateAuthorized(date);
            viaticoUpdated.setAuthorizer(authorizer);

            viaticosService.save(viaticoUpdated);

            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Guardado", HttpStatus.OK.value()));


        } catch (Exception e) {
            message = "Error: " + e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Traspaso de viáticos pendientes a otro autorizante"
            ,notes = "Se actualiza el autorizante de un viático")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('ADMINIT')")
    @PatchMapping(value = "/viaticos/authorizefrom/{authorizerfrom}/authorizeto/{authorizerto}")
    public ResponseEntity<Msg> changeAuthorizerViatico(@PathVariable User authorizerfrom, @PathVariable User authorizerto) {

        String message = "";
        try {

            List<Viaticos> listPendingsForAuthorizer = this.viaticosService.getListByAuthorizerPendings(authorizerfrom);
            for (Viaticos viaticos : listPendingsForAuthorizer) {
                this.viaticosService.passViaticosPendingsToOtherAuthorizer(viaticos.getId(), authorizerto);
            }

            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Guardado", HttpStatus.OK.value()));

        } catch (Exception e) {
            message = "Error: " + e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Cambio de estado de un viático a pagado"
            ,notes = "Se actualiza el estado de un viático a pagado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('TESORERO')")
    @PatchMapping(value = "/viaticos/{id}/paidout")
    public ResponseEntity<Msg> paidOutViatico(@PathVariable int id) {

        LocalDateTime date = LocalDateTime.now();
        String message = "";

        try {

            StatesCompras stateUpdated = statesComprasService.getOne(4).get();

            Viaticos viaticoUpdated = viaticosService.getOne(id).get();
            viaticoUpdated.setState(stateUpdated);
            viaticoUpdated.setDateEnd(date);

            viaticosService.save(viaticoUpdated);

            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Guardado", HttpStatus.OK.value()));


        } catch (Exception e) {
            message = "Error: " + e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Rechazo de un viático"
            ,notes = "Rechazo de un viático")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('AUTORIZANTEVIATICOS') or hasRole('ANALISTACONTABLE')")
    @PatchMapping(value = "/viaticos/{id}/rejection/reason/{reason}/by/{user}")
    public ResponseEntity<Msg> rejectionViatico(@PathVariable int id, @PathVariable String reason, @PathVariable User user) {

        LocalDateTime date = LocalDateTime.now();
        String message = "";

        StatesCompras stateUpdated = statesComprasService.getOne(5).get();

        try {

            Viaticos viaticoUpdated = viaticosService.getOne(id).get();
            viaticoUpdated.setState(stateUpdated);
            viaticoUpdated.setReasonRejection(reason);
            viaticoUpdated.setDateEnd(date);
            viaticoUpdated.setRejection(1);
            viaticoUpdated.setRejectedBy(user);

            viaticosService.save(viaticoUpdated);

            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Guardado", HttpStatus.OK.value()));


        } catch (Exception e) {
            message = "Error: " + e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Vinculación con referencia de Quiter de un viático"
            ,notes = "Se vincula el registro con la referencia correspodiente en Quiter")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('ANALISTACONTABLE')")
    @PatchMapping(value = "/viaticos/{id}/ref/{referencia}")
    public ResponseEntity<Msg> loadViatico(@PathVariable int id, @PathVariable String referencia) {

        LocalDateTime date = LocalDateTime.now();
        String message = "";

        StatesCompras stateUpdated = statesComprasService.getOne(3).get();

        try {

            Viaticos viaticoUpdated = viaticosService.getOne(id).get();
            viaticoUpdated.setState(stateUpdated);
            viaticoUpdated.setRef(referencia);
            viaticoUpdated.setDateLoaded(date);

            viaticosService.save(viaticoUpdated);

            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Guardado", HttpStatus.OK.value()));


        } catch (Exception e) {
            message = "Error: " + e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Vinculación con op de Quiter de un viático"
            ,notes = "Se vincula el registro con la op correspodiente en Quiter")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('TESORERO')")
    @PatchMapping(value = "/viaticos/{id}/op/{op}")
    public ResponseEntity<Msg> loadOP(@PathVariable int id, @PathVariable String op) {

        LocalDateTime date = LocalDateTime.now();
        String message = "";
        long totalTimeResolution = 0;
        String timeResolution = "";

        StatesCompras stateUpdated = statesComprasService.getOne(4).get();

        try {

            Viaticos viaticoUpdated = viaticosService.getOne(id).get();
            viaticoUpdated.setState(stateUpdated);
            viaticoUpdated.setOp(op);
            viaticoUpdated.setDateEnd(date);

            Duration duration = Duration.between(viaticoUpdated.getDateInit(), date);

            totalTimeResolution = totalTimeResolution + duration.getSeconds();

            long days = totalTimeResolution / DAYS;
            long hours = ((totalTimeResolution % DAYS) / SECONDS_PER_HOUR);
            long minutes = ((totalTimeResolution % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
            long secs = (totalTimeResolution % SECONDS_PER_MINUTE);

            String day = "";
            if(days == 0){
                if (hours != 0){
                    timeResolution = hours + "hs " + minutes + "min " + secs + "segs";
                }else {
                    if (minutes != 0) {
                        timeResolution = minutes + "min " + secs + "segs";
                    } else {
                        timeResolution = secs + "segs";
                    }
                }
            }else if(days == 1){
                day = "día ";
                timeResolution = days + day + hours + "hs " + minutes + "min " + secs + "segs";
            }else if(days > 1){
                day = "días ";
                timeResolution = days + day + hours + "hs " + minutes + "min " + secs + "segs";
            }

            viaticoUpdated.setResolution(timeResolution);

            viaticosService.save(viaticoUpdated);

            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Guardado", HttpStatus.OK.value()));


        } catch (Exception e) {
            message = "Error: " + e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Obtener reporte de viáticos de colaboradores"
            ,notes = "Se obtiene el reporte de viáticos de todos los colaboradores, ordenado de forma descendiente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('SUPERVISORCONTABLE') or hasRole('SUPERVISORTESORERIAS') or hasRole('SUPERVISORVIATICOS')")
    @GetMapping("/viaticos/colaborators/all/report")
    public List<ReportFinallyColaborators> getReportColaborators(){
        List<ReportFinallyColaborators> list = viaticosService.getReportColaborators();
        return list;
    }

    @ApiOperation(value = "Obtener desempeño de analistas contables"
            ,notes = "Se obtiene el detalle del desempeño de los analistas contables")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('SUPERVISORCONTABLE')")
    @GetMapping("/viaticos/analysts/performance")
    public List<CardAnalysts> getPerformanceTechnicians(){
        List<CardAnalysts> list = viaticosService.getPerformanceAnalysts();
        return list;
    }

    @ApiOperation(value = "Obtener desempeño de autorizantes"
            ,notes = "Se obtiene el detalle del desempeño de los autorizantes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('SUPERVISORVIATICOS')")
    @GetMapping("/viaticos/authorizers/performance")
    public List<CardAuthorizers> getPerformanceAuthorizers(){
        List<CardAuthorizers> list = viaticosService.getPerformanceAuthorizers();
        return list;
    }

    @ApiOperation(value = "Obtener desempeño de tesoreros"
            ,notes = "Se obtiene el detalle del desempeño de los tesoreros")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('SUPERVISORTESORERIAS')")
    @GetMapping("/viaticos/treasurers/performance")
    public List<CardTreasurers> getPerformanceTreasurers(){
        List<CardTreasurers> list = viaticosService.getPerformanceTreasurers();
        return list;
    }

    @ApiOperation(value = "Lista de Viaticos por Colaborador y Autorizante"
            ,notes = "Se obtiene una lista de viaticos por coincidencia en el nombre del colaborador y autorizante")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viaticos/colaborator/{name}/authorizer/{authorizer}")
    public List<Viaticos> getByColaboratorAndAuthorizer(@PathVariable("name") String name, @PathVariable("authorizer") User authorizer){

        List<Viaticos> list = viaticosService.getByNameColaboratorAndAuthorizer(name, authorizer);

        return list;
    }

    @ApiOperation(value = "Lista de Viaticos por Colaborador y Analista Contable"
            ,notes = "Se obtiene una lista de viaticos por coincidencia en el nombre del colaborador y Analista Contable")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viaticos/colaborator/{name}/analyst/{analyst}")
    public List<Viaticos> getByColaboratorAndAnalyst(@PathVariable("name") String name, @PathVariable("analyst") User analyst){

        List<Viaticos> list = viaticosService.getByNameColaboratorAndAnalyst(name, analyst);

        return list;
    }

    @ApiOperation(value = "Lista de Viaticos por Colaborador y Tesorero"
            ,notes = "Se obtiene una lista de viaticos por coincidencia en el nombre del colaborador y Tesorero")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viaticos/colaborator/{name}/treasurer/{treasurer}")
    public List<Viaticos> getByColaboratorAndTreasurer(@PathVariable("name") String name, @PathVariable("treasurer") User treasurer){

        List<Viaticos> list = viaticosService.getByNameColaboratorAndTreasurer(name, treasurer);

        return list;
    }

    @ApiOperation(value = "Viatico por referencia"
            ,notes = "Se obtiene una lista de viaticos por coincidencia en la referencia")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viaticos/ref/{ref}")
    public List<Viaticos> getByRef(@PathVariable("ref") String ref){

        List<Viaticos> list = viaticosService.getByRef(ref);

        return list;
    }

    @ApiOperation(value = "Viatico por Sucursal"
            ,notes = "Se obtiene una lista de viaticos por coincidencia en la sucursal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viaticos/branch/{branch}")
    public List<Viaticos> getByBranch(@PathVariable("branch") Branch branch){

        List<Viaticos> list = viaticosService.getByBranch(branch);

        return list;
    }

    @ApiOperation(value = "Eliminación de Viáticos por ID"
            ,notes = "Se elimina una viático a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/viaticos/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            viaticosService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Viático Eliminado", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Obtener reporte de estados"
            ,notes = "Se obtiene un reporte con la cantidad de viáticos en los distintos estados.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viaticos/reports/origin/pendings")
    public ReportGraphic getReportOriginPendings(){
        ReportGraphic reportGraphic = viaticosService.getReportTotalTimePendings();
        return reportGraphic;
    }

    @ApiOperation(value = "Obtener reporte de estados de sucursales"
            ,notes = "Se obtiene un reporte con la cantidad de viáticos en los distintos estados en cada sucursal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viaticos/reports/branchs/pendings")
    public ReportGraphic getReportBranchsPendings(){
        ReportGraphic reportGraphic = viaticosService.getReportForBranchs();
        return reportGraphic;
    }

    @ApiOperation(value = "Obtener reporte de montos por sucursales"
            ,notes = "Se obtiene un reporte con los monton de viáticos pagados por cada sucursal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viaticos/reports/branchs/amounts/paids")
    public LineReportLong getReportBranchsAmounts(){
        LineReportLong reportGraphic = viaticosService.getReportBranchsAmounts();
        return reportGraphic;
    }

    @ApiOperation(value = "Obtener reporte de monto por conceptos"
            ,notes = "Se obtiene un reporte con el detalle de montos por conceptos de viáticos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viaticos/reports/amounts/concepts")
    public ReportGraphicLong getReportConceptsAmounts(){
        ReportGraphicLong reportGraphic = viaticosService.getReportForConcepts();
        return reportGraphic;
    }

    @ApiOperation(value = "Obtener reporte por sucursal, según parámetros"
            ,notes = "Se obtiene un reporte de viáticos de una sucursal, según los parámetros enviados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viaticos/reports/branch/{branch}/month/{month}/year/{year}")
    public ReportMultipleLong getReportForBranchParametrized(@PathVariable("branch") Branch branch, @PathVariable("month") int month, @PathVariable("year") int year){
        ReportMultipleLong reportGraphic = viaticosService.getReportForBranchParametrized(branch, month, year);
        return reportGraphic;
    }

    @ApiOperation(value = "Obtener reporte por compañía, según parámetros"
            ,notes = "Se obtiene un reporte de viáticos de una compañía, según los parámetros enviados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viaticos/reports/company/{company}/month/{month}/year/{year}")
    public ReportMultipleLong getReportForCompanyParametrized(@PathVariable("company") Company company, @PathVariable("month") int month, @PathVariable("year") int year){
        ReportMultipleLong reportGraphic = viaticosService.getReportForCompanyParametrized(company, month, year);
        return reportGraphic;
    }

    @ApiOperation(value = "Obtener reporte por colaborador, según parámetros"
            ,notes = "Se obtiene un reporte de viáticos de un colaborador, según los parámetros enviados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/viaticos/reports/colaborator/{colaborator}/month/{month}/year/{year}")
    public ReportMultipleLong getReportForCompanyParametrized(@PathVariable("colaborator") User colaborator, @PathVariable("month") int month, @PathVariable("year") int year){
        ReportMultipleLong reportGraphic = viaticosService.getReportForColaboratorParametrized(colaborator, month, year);
        return reportGraphic;
    }

}
