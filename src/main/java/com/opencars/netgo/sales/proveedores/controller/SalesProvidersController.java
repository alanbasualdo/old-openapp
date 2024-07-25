package com.opencars.netgo.sales.proveedores.controller;

import com.opencars.netgo.dms.providers.entity.ProvidersSales;
import com.opencars.netgo.dms.quoter.entity.TypeOfSale;
import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.msgs.MsgInt;
import com.opencars.netgo.sales.proveedores.dto.*;
import com.opencars.netgo.sales.proveedores.entity.ConceptsProviders;
import com.opencars.netgo.sales.proveedores.entity.SalesProviders;
import com.opencars.netgo.sales.proveedores.entity.StatesProveedores;
import com.opencars.netgo.sales.proveedores.entity.SubcateorizedConcepts;
import com.opencars.netgo.sales.proveedores.service.*;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Expression;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Compras para módulo Compras-Proveedores")
public class SalesProvidersController {

    @Autowired
    SalesProvidersService salesProvidersService;

    @Autowired
    TableContableProvidersService tableContableProvidersService;

    @Autowired
    TableTreasurersProvidersService tableTreasurersProvidersService;

    @Autowired
    AuthorizerPaidTypeService authorizerPaidTypeService;

    @Autowired
    TableAuthorizerService tableAuthorizerService;

    @Autowired
    AuthorizersAmountsService authorizersAmountsService;

    @Autowired
    AccountsService accountsService;

    @Autowired
    StatesProveedoresService statesProveedoresService;

    @Autowired
    ExceptedsConceptsService exceptedsConceptsService;

    @Autowired
    ExceptedsConceptsTreasurersService exceptedsConceptsTreasurersService;

    @Autowired
    TableConceptsTypesPaidsService tableConceptsTypesPaidsService;

    @Autowired
    GarajeChiefsService garajeChiefsService;

    @Autowired
    BranchChiefsService branchChiefsService;

    @Autowired
    SubcateorizedConceptsService subcateorizedConceptsService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "Creación de una Compra"
            ,notes = "Se envía objeto de tipo SalesProviders a través de un payload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/salesproviders/create")
    public ResponseEntity<?> create(@Valid @RequestBody SalesProviders sale, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MsgInt("Alguno de los datos no coinciden con el formato esperado", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        String message = "";

        LocalDateTime date = LocalDateTime.now();

        try{

            SalesProviders newSale = new SalesProviders(
                    sale.getAmount(),
                    sale.getBranch(),
                    sale.getAttacheds(),
                    sale.getConcept(),
                    sale.getColaborator(),
                    sale.getObservation(),
                    sale.getPaidType(),
                    sale.getDateEmision(),
                    sale.getDateExpiration(),
                    sale.getProvider()
            );

            //Asigno el estado
            newSale.setState(new StatesProveedores(1));

            //Asigno le fecha de creación
            newSale.setDateInit(date);

            //Asigno analista contable
            newSale.setAnalyst(assignAnalyst(sale.getConcept(), sale.getBranch()));

            //Asigno tesorero
            newSale.setTreasurer(assignTreasurer(sale.getConcept(), sale.getBranch()));

            //Asigno autorizante de metodo de pago
            User authorizerPaidMethod = authorizerPaidTypeService.listAuthorizers().get(0);
            newSale.setAuthorizerPaidMethod(authorizerPaidMethod);

            //Asigno fecha de pago
            newSale.setDateAgreed(assignDateAgreed(sale.getPaidType(), sale.getConcept(), sale.getDateEmision(), sale.getDateAgreed(), sale.getDateExpiration()));

            SubcateorizedConcepts subConcept = subcateorizedConceptsService.getOne(sale.getConcept().getId()).get();

            if (subConcept.getType().getType().equals("Por sucursal")){
                newSale.setAuthorizerSector(garajeChiefsService.getChiefForGaraje(sale.getBranch()).get().getChief());
            }else if (subConcept.getType().getType().equals("Por taller")){
                newSale.setAuthorizerSector(branchChiefsService.getChiefForBranch(sale.getBranch()).get().getChief());
            }else{
                //Asigno autorizante designado o autorizante de cuenta. El que corresponda según el monto
                if (authorizersAmountsService.getAuthorizerForSubConceptAndAmount(sale.getAmount(), sale.getConcept()).isPresent()){
                    newSale.setAuthorizerSector(authorizersAmountsService.getAuthorizerForSubConceptAndAmount(sale.getAmount(), sale.getConcept()).get().getAuthorizer());
                }else{
                    newSale.setAuthorizerAccount(accountsService.getAuthorizerForSubConcept(sale.getConcept()).get().getOwner());
                }
            }

            newSale.setAuthorizerMaxAmount(assignMaxAmountAuthorizers(sale.getAmount()));

            //Si es un autorizante
            if (authorizersAmountsService.existsByAuthorizer(newSale.getColaborator())
                    || accountsService.existsByOwner(newSale.getColaborator())
                    || tableAuthorizerService.existsByAuthorizer(newSale.getColaborator())
                    || garajeChiefsService.existsByChief(newSale.getColaborator())
                    || branchChiefsService.existsByChief(newSale.getColaborator())){

                //Si es autorizante designado, hago la autorización automática
                if (newSale.getAuthorizerSector() != null){
                    if (newSale.getAuthorizerSector().getId() == newSale.getColaborator().getId()){
                        newSale.setState(statesProveedoresService.getOne(2).get());
                        newSale.setDateAuthorizedSector(date);
                    }
                }

                //Si es autorizante del subconcepto, hago la autorización automática
                if (newSale.getAuthorizerAccount() != null) {
                    if (newSale.getAuthorizerAccount().getId() == newSale.getColaborator().getId()){
                        newSale.setState(statesProveedoresService.getOne(2).get());
                        newSale.setDateAuthorizedAccount(date);
                    }
                }

                //Si es autorizante de monto mayor y autorizante designado o de subconcepto, hago la autorización automática de monto mayor
                //Si no, no se hace porque quedaría pendiente la autorización del designado o autorizante de subconcepto
                if (newSale.getAuthorizerMaxAmount() != null) {
                    if (newSale.getAuthorizerMaxAmount().getId() == newSale.getColaborator().getId()
                            && (newSale.getAuthorizerSector().getId() == newSale.getColaborator().getId()
                                || newSale.getAuthorizerAccount().getId() == newSale.getColaborator().getId())){
                        newSale.setState(statesProveedoresService.getOne(3).get());
                        newSale.setDateAuthorizedMaxAmount(date);
                    }
                }
            }

            salesProvidersService.save(newSale);

            return ResponseEntity.status(HttpStatus.CREATED).body(new Msg("Registro Guardado", HttpStatus.CREATED.value(), newSale.getId()));
        }catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MsgInt(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    private User assignAnalyst(SubcateorizedConcepts concept, Branch branch){
        User analyst = null;
        if (exceptedsConceptsService.existsByConceptId(concept.getId())){
            analyst = exceptedsConceptsService.getAnalystForConcept(concept);
        }else{
            if (tableContableProvidersService.existByBranch(branch)){
                analyst = tableContableProvidersService.getByBranch(branch).get().getAnalyst();
            }
        }
        return analyst;
    }

    private User assignTreasurer(SubcateorizedConcepts concept, Branch branch){
        User treasurer = null;
        if (exceptedsConceptsTreasurersService.existsByConceptId(concept.getId())){
            treasurer = exceptedsConceptsTreasurersService.getTreasurerForConcept(concept);
        }else{
            if (tableTreasurersProvidersService.existByBranch(branch)){
                treasurer = tableTreasurersProvidersService.getByBranch(branch).get().getTreasurer();
            }
        }
        return treasurer;
    }

    private LocalDate assignDateAgreed(String paidType, SubcateorizedConcepts concept, LocalDate dateEmision, LocalDate dateAgreed, LocalDate dateExpiration){
        LocalDate nDateAgreed = null;
        //Si es pago pactado asigno fecha de pago recibida
        if (paidType.equals("Pactado")){
            nDateAgreed = dateAgreed;
            //Si es pago habitual y no existe en tabla de excepciones, asigno fecha de pago: fecha de emision + 30 dias
        }else if(paidType.equals("Habitual") && !this.tableConceptsTypesPaidsService.existsByConcept(concept)){
            nDateAgreed = dateEmision.plusDays(30);
        }

        //Si está en la lista de excepciones por tipos de pago
        if(this.tableConceptsTypesPaidsService.existsByConcept(concept) && paidType.equals("Habitual")){
            int typePaidId = this.tableConceptsTypesPaidsService.getByConcept(concept).get().getTypePaid().getId();
            //Si la excepción es pago del 1 al 10
            if (typePaidId == 1){
                //Si la excepción es pago el ultimo jueves antes del vencimiento
                nDateAgreed = this.salesProvidersService.lastThursdayBeforeDay10(dateEmision);
            //Si la excepción es pago el ultimo jueves antes del vencimiento
            }else if(typePaidId == 2){
                nDateAgreed = this.salesProvidersService.calculateLastThursday(dateExpiration);
            }
        }
        return nDateAgreed;
    }

    private User assignMaxAmountAuthorizers(double amount){
        //Asigno autorizante de montos mayores si corresponde
        User authorizerAmount;
        if (amount > tableAuthorizerService.findAmountMin()){
            authorizerAmount = tableAuthorizerService.getAuthorizerForAmount(amount).get().getAuthorizer();
        }else{
            authorizerAmount = null;
        }
        return authorizerAmount;
    }

    @ApiOperation(value = "Actualización de una compra"
            ,notes = "Se actualiza un registro de una compra a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/salesproviders/update/{id}")
    public ResponseEntity<Msg>  update(@PathVariable(value = "id") int id, @Valid @RequestBody SalesProviders sale) {
        SalesProviders saleUpdated = salesProvidersService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found for this id :: " + id));

        String message = "";
        LocalDateTime date = LocalDateTime.now();

        saleUpdated.setAmount(sale.getAmount());
        saleUpdated.setBranch(sale.getBranch());
        saleUpdated.setAttacheds(sale.getAttacheds());
        saleUpdated.setConcept(sale.getConcept());
        saleUpdated.setColaborator(sale.getColaborator());
        saleUpdated.setObservation(sale.getObservation());
        saleUpdated.setPaidType(sale.getPaidType());
        saleUpdated.setDateEmision(sale.getDateEmision());
        saleUpdated.setDateExpiration(sale.getDateExpiration());
        saleUpdated.setProvider(sale.getProvider());

        //Asigno analista contable
        saleUpdated.setAnalyst(assignAnalyst(sale.getConcept(), sale.getBranch()));

        //Asigno tesorero
        saleUpdated.setTreasurer(assignTreasurer(sale.getConcept(), sale.getBranch()));

        //Asigno autorizante de metodo de pago
        User authorizerPaidMethod = authorizerPaidTypeService.listAuthorizers().get(0);
        saleUpdated.setAuthorizerPaidMethod(authorizerPaidMethod);

        //Asigno fecha de pago
        saleUpdated.setDateAgreed(assignDateAgreed(sale.getPaidType(), sale.getConcept(), sale.getDateEmision(), sale.getDateAgreed(), sale.getDateExpiration()));

        SubcateorizedConcepts subConcept = subcateorizedConceptsService.getOne(sale.getConcept().getId()).get();

        if (subConcept.getType().getType().equals("Por sucursal")){
            saleUpdated.setAuthorizerSector(garajeChiefsService.getChiefForGaraje(sale.getBranch()).get().getChief());
        }else if (subConcept.getType().getType().equals("Por taller")){
            saleUpdated.setAuthorizerSector(branchChiefsService.getChiefForBranch(sale.getBranch()).get().getChief());
        }else{
            //Asigno autorizante designado o autorizante de cuenta. El que corresponda según el monto
            if (authorizersAmountsService.getAuthorizerForSubConceptAndAmount(sale.getAmount(), sale.getConcept()).isPresent()){
                saleUpdated.setAuthorizerSector(authorizersAmountsService.getAuthorizerForSubConceptAndAmount(sale.getAmount(), sale.getConcept()).get().getAuthorizer());
            }else{
                saleUpdated.setAuthorizerAccount(accountsService.getAuthorizerForSubConcept(sale.getConcept()).get().getOwner());
            }
        }

        saleUpdated.setAuthorizerMaxAmount(assignMaxAmountAuthorizers(sale.getAmount()));

        //Si es un autorizante
        if (authorizersAmountsService.existsByAuthorizer(saleUpdated.getColaborator())
                || accountsService.existsByOwner(saleUpdated.getColaborator())
                || tableAuthorizerService.existsByAuthorizer(saleUpdated.getColaborator())
                || garajeChiefsService.existsByChief(saleUpdated.getColaborator())
                || branchChiefsService.existsByChief(saleUpdated.getColaborator())){

            //Si es autorizante designado, hago la autorización automática
            if (saleUpdated.getAuthorizerSector() != null){
                if (saleUpdated.getAuthorizerSector().getId() == saleUpdated.getColaborator().getId()){
                    saleUpdated.setState(statesProveedoresService.getOne(2).get());
                    saleUpdated.setDateAuthorizedSector(date);
                }
            }

            //Si es autorizante del subconcepto, hago la autorización automática
            if (saleUpdated.getAuthorizerAccount() != null) {
                if (saleUpdated.getAuthorizerAccount().getId() == saleUpdated.getColaborator().getId()){
                    saleUpdated.setState(statesProveedoresService.getOne(2).get());
                    saleUpdated.setDateAuthorizedAccount(date);
                }
            }

            //Si es autorizante de monto mayor y autorizante designado o de subconcepto, hago la autorización automática de monto mayor
            //Si no, no se hace porque quedaría pendiente la autorización del designado o autorizante de subconcepto
            if (saleUpdated.getAuthorizerMaxAmount() != null) {
                if (saleUpdated.getAuthorizerMaxAmount().getId() == saleUpdated.getColaborator().getId()
                        && (saleUpdated.getAuthorizerSector().getId() == saleUpdated.getColaborator().getId()
                        || saleUpdated.getAuthorizerAccount().getId() == saleUpdated.getColaborator().getId())){
                    saleUpdated.setState(statesProveedoresService.getOne(3).get());
                    saleUpdated.setDateAuthorizedMaxAmount(date);
                }
            }
        }

        try {
            salesProvidersService.save(saleUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("El registro se ha actualizado con éxito.", HttpStatus.OK.value(), saleUpdated.getId()));

        } catch (Exception e) {
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Msg(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Lista de compras cargadas por colaborador"
            ,notes = "Se obtiene una lista de compras cargadas por un colaborador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/user/{user}")
    public Page<SalesProviders> getAllFromUser(@PathVariable("user") User user, @PageableDefault(size = 20, page = 0, sort = "dateInit", direction = Sort.Direction.DESC) Pageable pageable){
        Page<SalesProviders> list = salesProvidersService.getByColaborator(user, pageable);
        return list;
    }

    @ApiOperation(value = "Compra por ID"
            ,notes = "Se obtiene una compra a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/id/{id}")
    public ResponseEntity<SalesProviders> getById(@PathVariable("id") long id){
        SalesProviders sale = salesProvidersService.getOne(id).get();
        return new ResponseEntity(sale, HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de compras por proveedor y colaborador que cargó"
            ,notes = "Se obtiene una lista de compras por proveedor, cargadas por un colaborador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/provider/{provider}/user/{user}")
    public Page<SalesProviders> getByProviderAndColaborator(@PathVariable("provider") ProvidersSales provider, @PathVariable("user") User user, @PageableDefault(size = 20, page = 0, sort = "dateInit", direction = Sort.Direction.DESC) Pageable pageable){
        Page<SalesProviders> list = salesProvidersService.getByProviderAndColaborator(provider.getId(), user.getId(), pageable);
        return list;
    }

    @ApiOperation(value = "Lista de compras por sucursal y colaborador que cargó"
            ,notes = "Se obtiene una lista de compras por sucursal, cargadas por un colaborador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/branch/{branch}/user/{user}")
    public Page<SalesProviders> getByBranchAndColaborator(@PathVariable("branch") Branch branch, @PathVariable("user") User user, @PageableDefault(size = 20, page = 0, sort = "dateInit", direction = Sort.Direction.DESC) Pageable pageable){
        Page<SalesProviders> list = salesProvidersService.getByBranchAndColaborator(branch.getId(), user.getId(), pageable);
        return list;
    }

    @ApiOperation(value = "Lista de compras por proveedor"
            ,notes = "Se obtiene una lista de compras por proveedor")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/provider/{provider}")
    public Page<SalesProviders> getByProvider(@PathVariable("provider") ProvidersSales provider, @PageableDefault(size = 20, page = 0, sort = "dateInit", direction = Sort.Direction.DESC) Pageable pageable){
        Page<SalesProviders> list = salesProvidersService.getByProvider(provider.getId(), pageable);
        return list;
    }

    @ApiOperation(value = "Lista de compras por sucursal"
            ,notes = "Se obtiene una lista de compras por sucursal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/branch/{branch}")
    public Page<SalesProviders> getByBranch(@PathVariable("branch") Branch branch, @PageableDefault(size = 20, page = 0, sort = "dateInit", direction = Sort.Direction.DESC) Pageable pageable){
        Page<SalesProviders> list = salesProvidersService.getByBranch(branch.getId(), pageable);
        return list;
    }

    @ApiOperation(value = "Lista de compras por proveedor y autorizante"
            ,notes = "Se obtiene una lista de compras por proveedor y autorizante")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/provider/{provider}/authorizer/{authorizer}")
    public Page<SalesProviders> getByProviderAndAuthorizer(@PathVariable("provider") ProvidersSales provider, @PathVariable("authorizer") User authorizer, @PageableDefault(size = 20, page = 0, sort = "dateInit", direction = Sort.Direction.DESC) Pageable pageable){
        Page<SalesProviders> list = salesProvidersService.getByProviderAndAuthorizer(provider.getId(), authorizer.getId(), pageable);
        return list;
    }

    @ApiOperation(value = "Lista de compras por sucursal y autorizante"
            ,notes = "Se obtiene una lista de compras por sucursal y autorizante")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/branch/{branch}/authorizer/{authorizer}")
    public Page<SalesProviders> getByBranchAndAuthorizer(@PathVariable("branch") Branch branch, @PathVariable("authorizer") User authorizer, @PageableDefault(size = 20, page = 0, sort = "dateInit", direction = Sort.Direction.DESC) Pageable pageable){
        Page<SalesProviders> list = salesProvidersService.getByBranchAndAuthorizer(branch.getId(), authorizer.getId(), pageable);
        return list;
    }

    @ApiOperation(value = "Lista de compras por proveedor y analista contable"
            ,notes = "Se obtiene una lista de compras por proveedor y analista contable")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/provider/{provider}/analyst/{analyst}")
    public Page<SalesProviders> getByProviderAndAnalyst(@PathVariable("provider") ProvidersSales provider, @PathVariable("analyst") User analyst, @PageableDefault(size = 20, page = 0, sort = "dateInit", direction = Sort.Direction.DESC) Pageable pageable){
        Page<SalesProviders> list = salesProvidersService.getByProviderAndAnalyst(provider.getId(), analyst.getId(), pageable);
        return list;
    }

    @ApiOperation(value = "Lista de compras por proveedor y tesorero"
            ,notes = "Se obtiene una lista de compras por proveedor y tesorero")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/provider/{provider}/treasurer/{treasurer}")
    public Page<SalesProviders> getByProviderAndTreasurer(@PathVariable("provider") ProvidersSales provider, @PathVariable("treasurer") User treasurer, @PageableDefault(size = 20, page = 0, sort = "dateInit", direction = Sort.Direction.DESC) Pageable pageable){
        Page<SalesProviders> list = salesProvidersService.getByProviderAndTreasurer(provider.getId(), treasurer.getId(), pageable);
        return list;
    }

    @ApiOperation(value = "Lista de compras por sucursal y tesorero"
            ,notes = "Se obtiene una lista de compras por sucursal y tesorero")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/branch/{branch}/treasurer/{treasurer}")
    public Page<SalesProviders> getByBranchAndTreasurer(@PathVariable("branch") Branch branch, @PathVariable("treasurer") User treasurer, @PageableDefault(size = 20, page = 0, sort = "dateInit", direction = Sort.Direction.DESC) Pageable pageable){
        Page<SalesProviders> list = salesProvidersService.getByBranchAndTreasurer(branch.getId(), treasurer.getId(), pageable);
        return list;
    }

    @ApiOperation(value = "Lista de compras por sucursal y analista contable"
            ,notes = "Se obtiene una lista de compras por sucursal y analista contable")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/branch/{branch}/analyst/{analyst}")
    public Page<SalesProviders> getByBranchAndAnalyst(@PathVariable("branch") Branch branch, @PathVariable("analyst") User analyst, @PageableDefault(size = 20, page = 0, sort = "dateInit", direction = Sort.Direction.DESC) Pageable pageable){
        Page<SalesProviders> list = salesProvidersService.getByBranchAndAnalyst(branch.getId(), analyst.getId(), pageable);
        return list;
    }

    @ApiOperation(value = "Confirmación de envío de pago a proveedores por WhatsApp"
            ,notes = "Se cierra el ciclo de un pago a proveedores, confirmando que se enviaron los comprobantes por WhatsApp")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('TESORERO')")
    @PatchMapping(value = "/salesproviders/confirm/{id}/op/{op}")
    public ResponseEntity<Msg> endCompra(@PathVariable("id") long id, @PathVariable("op") String op) {

        LocalDateTime date = LocalDateTime.now();
        String message = "";

        StatesProveedores stateUpdated = statesProveedoresService.getOne(6).get();

        try {

            SalesProviders compraUpdated = salesProvidersService.getOne(id).get();
            compraUpdated.setState(stateUpdated);
            compraUpdated.setOp(op);
            compraUpdated.setDateEnd(date);
            long diferenciaEnDias = ChronoUnit.DAYS.between(compraUpdated.getDateEmision(), compraUpdated.getDateEnd());
            compraUpdated.setResolution(String.valueOf(diferenciaEnDias));

            salesProvidersService.save(compraUpdated);

            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Guardado", HttpStatus.OK.value()));

        } catch (Exception e) {
            message = "Error: " + e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Reasignación de autorizante"
            ,notes = "Se reasigna un nuevo autorizante a un pago")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('AUTORIZANTECOMPRAS')")
    @PatchMapping(value = "/salesproviders/id/{id}/change/authorizer/{authorizer}/to/{newauthorizer}")
    public ResponseEntity<Msg> changeAuthorizer(@PathVariable("id") long id, @PathVariable("authorizer") User authorizer, @PathVariable("newauthorizer") User newauthorizer) {

        LocalDateTime date = LocalDateTime.now();
        String message = "";
        String nameAuthorizer = this.userService.getOne(authorizer.getId()).get().getName();
        String nameNewAuthorizer = this.userService.getOne(newauthorizer.getId()).get().getName();

        try {

            SalesProviders compraUpdated = salesProvidersService.getOne(id).get();
            if (compraUpdated.getAuthorizerAccount() != null){
                if (compraUpdated.getAuthorizerAccount().getId() == authorizer.getId()){
                    compraUpdated.setAuthorizerAccount(newauthorizer);
                }
            }
            if (compraUpdated.getAuthorizerSector() != null){
                if (compraUpdated.getAuthorizerSector().getId() == authorizer.getId()){
                    compraUpdated.setAuthorizerSector(newauthorizer);
                }
            }
            if (compraUpdated.getAuthorizerMaxAmount() != null){
                if (compraUpdated.getAuthorizerMaxAmount().getId() == authorizer.getId()){
                    compraUpdated.setAuthorizerMaxAmount(newauthorizer);
                }
            }
            compraUpdated.setDateChangeAuthorization(date);
            compraUpdated.setChangeAuthorization("El autorizante fue cambiado por " + nameAuthorizer + " a: " + nameNewAuthorizer + ".");

            salesProvidersService.save(compraUpdated);

            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Guardado", HttpStatus.OK.value()));

        } catch (Exception e) {
            message = "Error: " + e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Eliminación de una Compra por ID"
            ,notes = "Se elimina una compra a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "El servidor procesó con éxito la solicitud, pero no devuelve ningún contenido."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe"),
            @ApiResponse(code = 500, message = "Error Interno del servidor."),})
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/salesproviders/delete/{id}")
    public ResponseEntity<Msg> deleteById(@PathVariable int id) {
        try{
            salesProvidersService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Compra Eliminada", HttpStatus.OK.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg("Ha ocurrido un error al intentar eliminar", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Lista de compras sin autorizar, por autorizante"
            ,notes = "Se obtiene una lista de compras sin autorizar, por autorizante")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/unauthorizeds/authorizer/{authorizer}")
    public Page<SalesProviders> getUnauthorizedFromAutorizer(@PathVariable("authorizer") User authorizer, @PageableDefault(size = 20, page = 0, sort = "dateInit", direction = Sort.Direction.ASC) Pageable pageable){
        Page<SalesProviders> list = salesProvidersService.getByAuthorizerPendings(authorizer, pageable);
        return list;
    }

    @ApiOperation(value = "Lista de compras autorizadas, por autorizante"
            ,notes = "Se obtiene una lista de compras autorizadas, por autorizante")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/authorizeds/authorizer/{authorizer}")
    public Page<SalesProviders> getAuthorizedFromAutorizer(@PathVariable("authorizer") User authorizer, @PageableDefault(size = 20, page = 0) Pageable pageable){
        Specification<SalesProviders> spec = Specification.where(null);
        spec = spec.and((root, query, cb) -> cb.or(
                cb.equal(root.get("authorizerSector"), authorizer),
                cb.equal(root.get("authorizerAccount"), authorizer),
                cb.equal(root.get("authorizerMaxAmount"), authorizer)
        ));
        spec = spec.and((root, query, cb) -> cb.or(
                cb.isNotNull(root.get("dateAuthorizedSector")),
                cb.isNotNull(root.get("dateAuthorizedAccount")),
                cb.isNotNull(root.get("dateAuthorizedMaxAmount"))
        ));
        // Order by dateAuthorizedSector or dateAuthorizedAccount or dateAuthorizedMaxAmount
        spec = spec.and((root, query, cb) -> {
            Expression<Date> dateExpr = cb.coalesce(
                    root.get("dateAuthorizedSector"),
                    cb.coalesce(
                            root.get("dateAuthorizedAccount"),
                            root.get("dateAuthorizedMaxAmount")
                    )
            );
            query.orderBy(cb.desc(dateExpr));
            return null;
        });

        return salesProvidersService.getAll(spec, pageable);
    }

    @ApiOperation(value = "Lista de compras rechazadas, por autorizante"
            ,notes = "Se obtiene una lista de compras rechazadas, por autorizante")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/rejecteds/authorizer/{authorizer}")
    public Page<SalesProviders> getByRejectedBy(@PathVariable("authorizer") User authorizer, @PageableDefault(size = 20, page = 0, sort = "dateEnd", direction = Sort.Direction.DESC) Pageable pageable){
        Page<SalesProviders> list = salesProvidersService.getByRejectedBy(authorizer, pageable);
        return list;
    }

    @ApiOperation(value = "Lista de compras rechazadas, por analista contable"
            ,notes = "Se obtiene una lista de compras rechazadas, por analista contable")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/rejecteds/analyst/{analyst}")
    public Page<SalesProviders> getRejectedsByAnalyst(@PathVariable("analyst") User analyst, @PageableDefault(size = 20, page = 0, sort = "dateEnd", direction = Sort.Direction.DESC) Pageable pageable){
        Page<SalesProviders> list = salesProvidersService.getByRejectedBy(analyst, pageable);
        return list;
    }

    @ApiOperation(value = "Vinculación con referencia de Quiter de una compra"
            ,notes = "Se vincula el registro con la referencia correspodiente en Quiter")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('ANALISTACONTABLE')")
    @PatchMapping(value = "/salesproviders/{id}/ref/{referencia}")
    public ResponseEntity<Msg> loadCompra(@PathVariable int id, @PathVariable String referencia) {

        LocalDateTime date = LocalDateTime.now();
        String message = "";

        StatesProveedores stateUpdated = statesProveedoresService.getOne(4).get();

        try {

            SalesProviders compraUpdated = salesProvidersService.getOne(id).get();
            compraUpdated.setState(stateUpdated);
            compraUpdated.setRef(referencia);
            compraUpdated.setDateLoaded(date);

            //Si es pago habitual, autorizacion automatica del autorizante de metodo de pago
            if (compraUpdated.getPaidType().equals("Habitual")){
                compraUpdated.setDateAuthorizedPaidMethod(date);
                compraUpdated.setState(statesProveedoresService.getOne(5).get());
            }

            salesProvidersService.save(compraUpdated);

            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Guardado", HttpStatus.OK.value()));


        } catch (Exception e) {
            message = "Error: " + e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Edición de referencia de Quiter de una compra"
            ,notes = "Se edita la referencia de Quiter vinculada a un registro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('ANALISTACONTABLE')")
    @PatchMapping(value = "/salesproviders/{id}/edit/ref/{referencia}")
    public ResponseEntity<Msg> editRef(@PathVariable int id, @PathVariable String referencia) {

        String message = "";

        try {

            SalesProviders compraUpdated = salesProvidersService.getOne(id).get();
            compraUpdated.setRef(referencia);
            salesProvidersService.save(compraUpdated);

            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Guardado", HttpStatus.OK.value()));


        } catch (Exception e) {
            message = "Error: " + e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Lista de compras sin cargar, por analista contable"
            ,notes = "Se obtiene una lista de compras sin cargar en quiter, por analista contable")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/unloads/analyst/{analyst}")
    public Page<SalesProviders> getUnloadFromAnalyst(@PathVariable("analyst") User analyst, @PageableDefault(size = 20, page = 0, sort = "dateInit", direction = Sort.Direction.ASC) Pageable pageable){
        Page<SalesProviders> list = salesProvidersService.getUnloadsAnalyst(analyst, pageable);
        return list;
    }

    @ApiOperation(value = "Lista de compras cargadas en Quiter, por analista contable"
            ,notes = "Se obtiene una lista de compras cargadas en Quiter, por analista contable")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/loads/analyst/{analyst}")
    public Page<SalesProviders> getLoadsFromAnalyst(@PathVariable("analyst") User analyst, @PageableDefault(size = 20, page = 0, sort = "dateLoaded", direction = Sort.Direction.DESC) Pageable pageable){
        Page<SalesProviders> list = salesProvidersService.getAllNotRejectedAndLoaded(analyst, pageable);
        return list;
    }

    @ApiOperation(value = "Lista de compras con fecha de pago pendiente de autorizar"
            ,notes = "Se obtiene una lista de compras con fecha de pago pendiente de autorizar")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('AUTORIZANTEFECHADEPAGO')")
    @GetMapping("/salesproviders/datapaid/unauthorized")
    public Page<SalesProviders> getDataPaidUnauthorized(@PageableDefault(size = 20, page = 0, sort = "dateInit", direction = Sort.Direction.ASC) Pageable pageable){
        Page<SalesProviders> list = salesProvidersService.getAllDataPaidUnauthorizeds(pageable);
        return list;
    }

    @ApiOperation(value = "Lista de compras con fecha de pago autorizadas"
            ,notes = "Se obtiene una lista de compras con fecha de pago autorizadas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('AUTORIZANTEFECHADEPAGO')")
    @GetMapping("/salesproviders/datapaid/authorized")
    public Page<SalesProviders> getDataPaidAuthorized(@PageableDefault(size = 20, page = 0, sort = "dateAuthorizedPaidMethod", direction = Sort.Direction.DESC) Pageable pageable){
        Page<SalesProviders> list = salesProvidersService.getAllDataPaidAuthorizeds(pageable);
        return list;
    }

    @ApiOperation(value = "Lista de compras con fecha de pago reprogramada"
            ,notes = "Se obtiene una lista de compras con fecha de pago reprogramada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('AUTORIZANTEFECHADEPAGO')")
    @GetMapping("/salesproviders/datapaid/reprogramed")
    public Page<SalesProviders> getDataPaidReprogramed(@PageableDefault(size = 20, page = 0, sort = "dateAuthorizedPaidMethod", direction = Sort.Direction.DESC) Pageable pageable){
        Page<SalesProviders> list = salesProvidersService.getAllDataPaidReprogramed(pageable);
        return list;
    }

    @ApiOperation(value = "Autorización de una compra"
            ,notes = "Se autoriza una compra")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('AUTORIZANTECOMPRAS')")
    @PatchMapping(value = "/salesproviders/{id}/authorize/{authorizer}")
    public ResponseEntity<Msg> authorizeSale(@PathVariable int id, @PathVariable User authorizer) {

        LocalDateTime date = LocalDateTime.now();
        String message = "";

        try {

            SalesProviders compraUpdated = salesProvidersService.getOne(id).get();

            if (compraUpdated.getAuthorizerSector() != null){
                if (compraUpdated.getAuthorizerSector().getId() == authorizer.getId()){
                    compraUpdated.setState(statesProveedoresService.getOne(2).get());
                    compraUpdated.setDateAuthorizedSector(date);
                }
            }

            if (compraUpdated.getAuthorizerAccount() != null) {
                if (compraUpdated.getAuthorizerAccount().getId() == authorizer.getId()){
                    compraUpdated.setState(statesProveedoresService.getOne(2).get());
                    compraUpdated.setDateAuthorizedAccount(date);
                }
            }

            if (compraUpdated.getAuthorizerMaxAmount() != null) {
                if (compraUpdated.getAuthorizerMaxAmount().getId() == authorizer.getId()){
                    compraUpdated.setState(statesProveedoresService.getOne(3).get());
                    compraUpdated.setDateAuthorizedMaxAmount(date);
                }
            }

            salesProvidersService.save(compraUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Guardado", HttpStatus.OK.value()));

        } catch (Exception e) {
            message = "Error: " + e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Rechazo de una compra"
            ,notes = "Rechazo de una compra")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('AUTORIZANTECOMPRAS') or hasRole('ANALISTACONTABLE')")
    @PatchMapping(value = "/salesproviders/{id}/rejection/reason/{reason}/by/{user}")
    public ResponseEntity<Msg> rejectionViatico(@PathVariable int id, @PathVariable String reason, @PathVariable User user) {

        LocalDateTime date = LocalDateTime.now();
        String message = "";

        StatesProveedores stateUpdated = statesProveedoresService.getOne(7).get();

        try {

            SalesProviders compraUpdated = salesProvidersService.getOne(id).get();
            compraUpdated.setState(stateUpdated);
            compraUpdated.setReasonRejection(reason);
            compraUpdated.setDateEnd(date);
            compraUpdated.setRejection(1);
            compraUpdated.setRejectedBy(user);

            salesProvidersService.save(compraUpdated);

            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Guardado", HttpStatus.OK.value()));


        } catch (Exception e) {
            message = "Error: " + e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Reprogramación de Fecha de Pago"
            ,notes = "Se reprograma la fecha de pago de una compra")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('AUTORIZANTEFECHADEPAGO')")
    @PatchMapping(value = "/salesproviders/{id}/reprogramed/dateagreed/{dateAgreed}/user/{user}")
    public ResponseEntity<Msg> dataPaidReprogramed(@PathVariable int id, @PathVariable String dateAgreed, @PathVariable User user) {

        LocalDateTime dateCurrent = LocalDateTime.now();

        //Convierto la fecha que recibo en String a LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateAgreed, formatter);

        String message = "";

        StatesProveedores stateUpdated = statesProveedoresService.getOne(5).get();

        try {

            SalesProviders compraUpdated = salesProvidersService.getOne(id).get();
            compraUpdated.setState(stateUpdated);
            compraUpdated.setRejectionPaidMethod(1);
            compraUpdated.setDateAgreed(date);
            compraUpdated.setDateAuthorizedPaidMethod(dateCurrent);
            if(user.getId() != compraUpdated.getAuthorizerPaidMethod().getId()){
                compraUpdated.setAuthorizerPaidMethod(user);
            }

            salesProvidersService.save(compraUpdated);

            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Guardado", HttpStatus.OK.value()));

        } catch (Exception e) {
            message = "Error: " + e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Autorización de fecha de pago"
            ,notes = "Se actualiza el estado de una compra a fecha de pago autorizada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('AUTORIZANTEFECHADEPAGO')")
    @PatchMapping(value = "/salesproviders/{id}/datepaid/authorize/{authorizer}")
    public ResponseEntity<Msg> authorizeSaleDatePaid(@PathVariable int id, @PathVariable User authorizer) {

        LocalDateTime date = LocalDateTime.now();
        String message = "";

        try {

            StatesProveedores stateUpdated = statesProveedoresService.getOne(5).get();

            SalesProviders compraUpdated = salesProvidersService.getOne(id).get();
            compraUpdated.setState(stateUpdated);
            compraUpdated.setDateAuthorizedPaidMethod(date);

            if(authorizer.getId() != compraUpdated.getAuthorizerPaidMethod().getId()){
                compraUpdated.setAuthorizerPaidMethod(authorizer);
            }

            salesProvidersService.save(compraUpdated);

            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Guardado", HttpStatus.OK.value()));

        } catch (Exception e) {
            message = "Error: " + e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Lista de compras sin pagar, por tesorero"
            ,notes = "Se obtiene una lista de compras sin pagar, por tesorero")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('TESORERO') or hasRole('SUPERVISORTESORERIAS')")
    @GetMapping("/salesproviders/pendings/treasurers/{treasurer}")
    public Page<SalesProviders> getPendingsByTreasurer(@PathVariable("treasurer") User treasurer, @PageableDefault(size = 20, page = 0, sort = "dateAgreed", direction = Sort.Direction.ASC) Pageable pageable){
        Page<SalesProviders> list = salesProvidersService.getByTreasurerAndState(treasurer, statesProveedoresService.getOne(5).get(), pageable);
        return list;
    }

    @ApiOperation(value = "Lista de compras pagadas, por tesorero"
            ,notes = "Se obtiene una lista de compras pagadas, por tesorero")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('TESORERO') or hasRole('SUPERVISORTESORERIAS')")
    @GetMapping("/salesproviders/paids/treasurers/{treasurer}")
    public Page<SalesProviders> getPaidsByTreasurer(@PathVariable("treasurer") User treasurer, @PageableDefault(size = 20, page = 0, sort = "dateEnd", direction = Sort.Direction.DESC) Pageable pageable){
        Page<SalesProviders> list = salesProvidersService.getByTreasurerAndState(treasurer, statesProveedoresService.getOne(6).get(), pageable);
        return list;
    }

    @ApiOperation(value = "Lista de compras pendientes de carga por analistas"
            ,notes = "Se obtiene una lista de compras pendientes de carga por analistas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/analysts/pendings")
    public Page<SalesProviders> getAllPendingsForAnalysts(@PageableDefault(size = 20, page = 0) Pageable pageable){

        Specification<SalesProviders> spec = Specification.where(null);

        spec = spec.and((root, query, cb) -> cb.or(
                cb.and(
                        cb.equal(root.get("state"), statesProveedoresService.getOne(2).get()),
                        cb.isNull(root.get("authorizerMaxAmount"))
                ),
                cb.equal(root.get("state"), statesProveedoresService.getOne(3).get())
        ));

        spec = spec.and((root, query, cb) -> cb.or(
                cb.isNotNull(root.get("dateAuthorizedSector")),
                cb.isNotNull(root.get("dateAuthorizedAccount")),
                cb.isNotNull(root.get("dateAuthorizedMaxAmount"))
        ));
        // Order by dateAuthorizedSector or dateAuthorizedAccount or dateAuthorizedMaxAmount
        spec = spec.and((root, query, cb) -> {
            Expression<Date> dateExpr = cb.coalesce(
                    root.get("dateAuthorizedSector"),
                    cb.coalesce(
                            root.get("dateAuthorizedAccount"),
                            root.get("dateAuthorizedMaxAmount")
                    )
            );
            query.orderBy(cb.asc(dateExpr));
            return null;
        });

        return salesProvidersService.getAll(spec, pageable);
    }

    @ApiOperation(value = "Obtener lista de analistas contables para Compras"
            ,notes = "Se obtiene una lista de analistas contables para Compras")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/list/analysts")
    public List<User> getAnalystsList(){
        List<User> list = salesProvidersService.listAnalysts();
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
    @GetMapping("/salesproviders/list/treasurers")
    public List<User> getTreasurersList(){
        List<User> list = salesProvidersService.listTreasurers();
        return list;
    }

    @ApiOperation(value = "Obtener lista de todas las compras pendientes"
            ,notes = "Se obtiene una lista de todas las compras pendientes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/list/pendings/all")
    public List<SalesToExport> getAllPendingsForClose(){
        List<SalesToExport> list = salesProvidersService.getAllPendingsForClose();
        return list;
    }

    @ApiOperation(value = "Obtener lista de todas las compras cerradas"
            ,notes = "Se obtiene una lista de todas las compras cerradas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/list/closed/all")
    public List<SalesToExport> getAllClosedSales(){
        List<SalesToExport> list = salesProvidersService.getAllClosedSales();
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
    @GetMapping("/salesproviders/treasurers/pendings")
    public Page<SalesProviders> getAllPendingsForTreasurers(@PageableDefault(size = 20, page = 0, sort = "dateLoaded", direction = Sort.Direction.DESC) Pageable pageable){
        Page<SalesProviders> list = salesProvidersService.getByTreasurersPendings(pageable);
        return list;
    }

    @ApiOperation(value = "Obtener desempeño de tesoreros de Compras"
            ,notes = "Se obtiene el detalle del desempeño de los tesoreros de Compras")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('SUPERVISORTESORERIAS')")
    @GetMapping("/salesproviders/treasurers/performance")
    public List<CardTreasurersProviders> getPerformanceTreasurers(){
        List<CardTreasurersProviders> list = salesProvidersService.getPerformanceTreasurers();
        return list;
    }

    @ApiOperation(value = "Obtener desempeño de analistas contables en Compras"
            ,notes = "Se obtiene el detalle del desempeño de los analistas contables en Compras")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('SUPERVISORCONTABLE')")
    @GetMapping("/salesproviders/analysts/performance")
    public List<CardAnalystsProviders> getPerformanceTechnicians(){
        List<CardAnalystsProviders> list = salesProvidersService.getPerformanceAnalysts();
        return list;
    }

    @ApiOperation(value = "Obtener desempeño de autorizantes en Compras"
            ,notes = "Se obtiene el detalle del desempeño de los autorizantes en Compras")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('SUPERVISORVIATICOS')")
    @GetMapping("/salesproviders/authorizers/performance")
    public List<CardAuthorizersProviders> getPerformanceAuthorizers(){
        List<CardAuthorizersProviders> list = salesProvidersService.getPerformanceAuthorizers();
        return list;
    }

    @ApiOperation(value = "Traspaso de compras pendientes a otro autorizante"
            ,notes = "Se actualiza el autorizante de una compra")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 204, message = "No existe contenido para enviar"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición")})
    @PreAuthorize("hasRole('ADMINIT')")
    @PatchMapping(value = "/salesproviders/authorizefrom/{authorizerfrom}/authorizeto/{authorizerto}")
    public ResponseEntity<Msg> changeAuthorizerViatico(@PathVariable User authorizerfrom, @PathVariable User authorizerto) {

        String message = "";
        try {

            List<SalesProviders> listPendingsForAuthorizer = this.salesProvidersService.getListByAuthorizerPendings(authorizerfrom);
            for (SalesProviders sale : listPendingsForAuthorizer) {
                this.salesProvidersService.passSalesPendingsToOtherAuthorizer(sale.getId(), authorizerto);
            }

            return ResponseEntity.status(HttpStatus.OK).body(new Msg("Registro Guardado", HttpStatus.OK.value()));

        } catch (Exception e) {
            message = "Error: " + e;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Reporte de Facturas cargadas por colaborador"
            ,notes = "Se obtiene una lista de facturas cargadas por colaborador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('INDICADORESPROVEEDORES')")
    @GetMapping("/salesproviders/report/general")
    public List<ReportFinalProviders> getReportGeneral(){
        List<ReportFinalProviders> report = salesProvidersService.generateReport();
        return report;
    }

    @ApiOperation(value = "Reporte de Facturas cargadas por proveedor y colaborador"
            ,notes = "Se obtiene una lista de facturas cargadas por proveedor y colaborador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('INDICADORESPROVEEDORES')")
    @GetMapping("/salesproviders/report/provider/{provider}/general")
    public List<ReportFinalProviders> getReportGeneral(@PathVariable ProvidersSales provider){
        List<ReportFinalProviders> report = salesProvidersService.generateReportByProvider(provider.getId());
        return report;
    }

    @ApiOperation(value = "Reporte de Facturas cargadas por colaborador, filtrado"
            ,notes = "Se obtiene una lista de facturas cargadas por colaborador, filtrado por fecha de carga")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('INDICADORESPROVEEDORES')")
    @GetMapping("/salesproviders/report/general/filter/dateinit/{dateInit}/dateend/{dateEnd}")
    public List<ReportFinalProviders> getReportGeneralFilterByDate(@PathVariable String dateInit, @PathVariable String dateEnd){

        LocalDate dateInitLD = LocalDate.parse(dateInit);
        LocalDate dateEndLD = LocalDate.parse(dateEnd);

        int hour = 0;
        int minute = 0;

        LocalDateTime dateInitFormated = LocalDateTime.of(dateInitLD, LocalTime.of(hour, minute));
        LocalDateTime dateEndFormated = LocalDateTime.of(dateEndLD, LocalTime.of(hour, minute));

        List<ReportFinalProviders> report = salesProvidersService.generateReportFilterByDate(dateInitFormated, dateEndFormated);
        return report;
    }

    @ApiOperation(value = "Reporte de Facturas cargadas por colaborador y por proveedor, filtrado"
            ,notes = "Se obtiene una lista de facturas cargadas por colaborador y por proveedor, filtrado por fecha de carga")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('INDICADORESPROVEEDORES')")
    @GetMapping("/salesproviders/report/general/filter/dateinit/{dateInit}/dateend/{dateEnd}/provider/{provider}")
    public List<ReportFinalProviders> getReportGeneralFilterByDate(@PathVariable String dateInit, @PathVariable String dateEnd, @PathVariable long provider){

        LocalDate dateInitLD = LocalDate.parse(dateInit);
        LocalDate dateEndLD = LocalDate.parse(dateEnd);

        int hour = 0;
        int minute = 0;

        LocalDateTime dateInitFormated = LocalDateTime.of(dateInitLD, LocalTime.of(hour, minute));
        LocalDateTime dateEndFormated = LocalDateTime.of(dateEndLD, LocalTime.of(hour, minute));

        List<ReportFinalProviders> report = salesProvidersService.generateReportFilterByDateAndProvider(dateInitFormated, dateEndFormated, provider);
        return report;
    }

    @ApiOperation(value = "Lista por Colaborador que cargó la factura y Concepto"
            ,notes = "Se obtiene una lista por Colaborador que cargó la factura y Concepto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/list/colaborator/{colaborator}/concept/{concept}")
    public List<SalesFilter> getByColaboratorAndConcept(@PathVariable int colaborator, @PathVariable int concept){
        List<SalesFilter> list = salesProvidersService.getByColaboratorAndConcept(colaborator, concept);
        return list;
    }

    @ApiOperation(value = "Lista por Colaborador que cargó la factura, Concepto y Proveedor"
            ,notes = "Se obtiene una lista por Colaborador que cargó la factura, Concepto y Proveedor")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/list/colaborator/{colaborator}/concept/{concept}/provider/{provider}")
    public List<SalesFilter> getByColaboratorAndConceptAndProvider(@PathVariable int colaborator, @PathVariable int concept, @PathVariable long provider){
        List<SalesFilter> list = salesProvidersService.getByColaboratorAndConceptAndProvider(colaborator, concept, provider);
        return list;
    }

    @ApiOperation(value = "Lista por Colaborador que cargó la factura y Concepto"
            ,notes = "Se obtiene una lista por Colaborador que cargó la factura y Concepto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/list/colaborator/{colaborator}/concept/{concept}/dateinit/{dateInit}/dateend/{dateEnd}")
    public List<SalesFilter> getByColaboratorAndConceptByDateRange(@PathVariable int colaborator, @PathVariable int concept, @PathVariable String dateInit, @PathVariable String dateEnd){
        LocalDate dateInitLD = LocalDate.parse(dateInit);
        LocalDate dateEndLD = LocalDate.parse(dateEnd);

        int hour = 0;
        int minute = 0;

        LocalDateTime dateInitFormated = LocalDateTime.of(dateInitLD, LocalTime.of(hour, minute));
        LocalDateTime dateEndFormated = LocalDateTime.of(dateEndLD, LocalTime.of(hour, minute));

        List<SalesFilter> list = salesProvidersService.getByColaboratorAndConceptByDateRange(colaborator, concept, dateInitFormated, dateEndFormated);
        return list;
    }

    @ApiOperation(value = "Lista por coincidencia en el monto de la factura"
            ,notes = "Se obtiene una lista por coincidencia en el monto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/list/amount/{amount}")
    public List<ReportFinalProviders> getByCoincidenceInAmount(@PathVariable double amount){
        List<ReportFinalProviders> report = salesProvidersService.getByCoincidenceInAmount(amount);
        return report;
    }

    @ApiOperation(value = "Lista por coincidencia en el monto de la factura y proveedor"
            ,notes = "Se obtiene una lista por coincidencia en el monto de la factura y proveedor")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/list/amount/{amount}/provider/{provider}")
    public List<ReportFinalProviders> getByCoincidenceInAmount(@PathVariable double amount, @PathVariable long provider){
        List<ReportFinalProviders> report = salesProvidersService.getByCoincidenceInAmountAndProvider(amount, provider);
        return report;
    }

    @ApiOperation(value = "Lista por Colaborador que cargó la factura, Concepto y Proveedor"
            ,notes = "Se obtiene una lista por Colaborador que cargó la factura, Concepto y Proveedor")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/list/colaborator/{colaborator}/concept/{concept}/provider/{provider}/dateinit/{dateInit}/dateend/{dateEnd}")
    public List<SalesFilter> getByColaboratorAndConceptByDateRange(@PathVariable int colaborator, @PathVariable int concept, @PathVariable String dateInit, @PathVariable String dateEnd, @PathVariable long provider){
        LocalDate dateInitLD = LocalDate.parse(dateInit);
        LocalDate dateEndLD = LocalDate.parse(dateEnd);

        int hour = 0;
        int minute = 0;

        LocalDateTime dateInitFormated = LocalDateTime.of(dateInitLD, LocalTime.of(hour, minute));
        LocalDateTime dateEndFormated = LocalDateTime.of(dateEndLD, LocalTime.of(hour, minute));

        List<SalesFilter> list = salesProvidersService.getByColaboratorAndConceptAndProviderByDateRange(colaborator, concept, dateInitFormated, dateEndFormated, provider);
        return list;
    }

    @ApiOperation(value = "Lista por coincidencia en colaborador, concepto y monto"
            ,notes = "Se obtiene una lista por coincidencia en colaborador, concepto y monto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/list/colaborator/{colaborator}/concept/{concept}/amount/{amount}")
    public List<SalesFilter> getByColaboratorAndConceptAndAmount(@PathVariable int colaborator, @PathVariable int concept, @PathVariable double amount){

        List<SalesFilter> list = salesProvidersService.getByColaboratorAndConceptAndAmount(colaborator, concept, amount);
        return list;
    }

    @ApiOperation(value = "Lista por coincidencia en colaborador, concepto, monto y proveedor"
            ,notes = "Se obtiene una lista por coincidencia en colaborador, concepto, monto y proveedor")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/salesproviders/list/colaborator/{colaborator}/concept/{concept}/amount/{amount}/provider/{provider}")
    public List<SalesFilter> getByColaboratorAndConceptAndAmountAndProvider(@PathVariable int colaborator, @PathVariable int concept, @PathVariable double amount, @PathVariable long provider){

        List<SalesFilter> list = salesProvidersService.getByColaboratorAndConceptAndAmountAndProvider(colaborator, concept, amount, provider);
        return list;
    }

}
