package com.opencars.netgo.support.general.controller;

import com.opencars.netgo.auth.controller.AuthController;
import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.news.letters.controller.LettersController;
import com.opencars.netgo.news.recognitions.controller.RecognitionsController;
import com.opencars.netgo.news.welcomes.controller.WelcomeController;
import com.opencars.netgo.support.general.dto.*;
import com.opencars.netgo.support.tickets.controller.TicketsController;
import com.opencars.netgo.support.tickets.entity.TicketsCategories;
import com.opencars.netgo.support.tickets.entity.TicketsStates;
import com.opencars.netgo.support.tickets.entity.TicketsSubcategories;
import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador General de Soporte")
public class GeneralControllerSupport {

    @Autowired
    TicketsController ticketsController;

    @Autowired
    WelcomeController welcomeController;

    @Autowired
    LettersController lettersController;

    @Autowired
    RecognitionsController recognitionsController;

    @Autowired
    AuthController authController;

    @ApiOperation(value = "Cantidad de Notificaciones de CCHH por tipo"
            ,notes = "Se obtiene la cantidad de notificaciones de CCHH, por tipo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cchh/general/count")
    public ResponseEntity<SupportAdminCCHHCounts> getCountCCHH(){
        SupportAdminCCHHCounts supportAdminCCHHCounts = new SupportAdminCCHHCounts(
                welcomeController.countWelcomesNotPublished(),
                lettersController.getCountLettersNotPublished(),
                recognitionsController.countRecognitionsNotPublisheds()
        );
        return new ResponseEntity(supportAdminCCHHCounts, HttpStatus.OK);
    }

    @ApiOperation(value = "Cantidad de Notificaciones de MKT por tipo"
            ,notes = "Se obtiene la cantidad de notificaciones de marketing, por tipo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mkt/general/count")
    public ResponseEntity<SupportAdminMKTCounts> getCountMKT(){
        SupportAdminMKTCounts supportAdminMKTCounts = new SupportAdminMKTCounts(
                welcomeController.countWelcomesNotPublished(),
                recognitionsController.countRecognitionsNotPublisheds(),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(6), new TicketsSubcategories(24), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(6), new TicketsSubcategories(24), new TicketsStates(2)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(6), new TicketsSubcategories(25), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(6), new TicketsSubcategories(25), new TicketsStates(2)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(6), new TicketsSubcategories(26), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(6), new TicketsSubcategories(26), new TicketsStates(2))
        );
        return new ResponseEntity(supportAdminMKTCounts, HttpStatus.OK);
    }

    @ApiOperation(value = "Cantidad de Notificaciones de Soporte de Usuario"
            ,notes = "Se obtiene la cantidad de notificaciones de soporte para un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/support/users/general/count/{applicant}")
    public ResponseEntity<SupportUserCounts> getCountUser(@PathVariable User applicant){
        SupportUserCounts supportUserCounts = new SupportUserCounts(
               ticketsController.countTicketsApplicantByState(applicant, new TicketsStates(1)),
               ticketsController.countTicketsApplicantByState(applicant, new TicketsStates(2))
        );
        return new ResponseEntity(supportUserCounts, HttpStatus.OK);
    }

    @ApiOperation(value = "Cantidad de Notificaciones de Soporte de un Admin IT"
            ,notes = "Se obtiene la cantidad de notificaciones de soporte para un administrador de IT")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/support/adminit/general/count")
    public ResponseEntity<SupportAdminITCounts> getCountIT(){
        SupportAdminITCounts supportAdminITCounts = new SupportAdminITCounts(
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(1), new TicketsSubcategories(2), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(1), new TicketsSubcategories(2), new TicketsStates(2)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(1), new TicketsSubcategories(3), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(1), new TicketsSubcategories(3), new TicketsStates(2)),
                ticketsController.countTicketsIT(new TicketsCategories(1), new TicketsStates(1)),
                ticketsController.countTicketsIT(new TicketsCategories(1), new TicketsStates(2))

        );
        return new ResponseEntity(supportAdminITCounts, HttpStatus.OK);
    }

    @ApiOperation(value = "Cantidad de Notificaciones de Soporte de un Admin PVTA"
            ,notes = "Se obtiene la cantidad de notificaciones de soporte para un administrador de Postventa")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/support/adminpvta/general/count")
    public ResponseEntity<SupportAdminPVTACounts> getCountPVTA(){
        SupportAdminPVTACounts supportAdminPVTACounts = new SupportAdminPVTACounts(

                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(2), new TicketsSubcategories(4), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(2), new TicketsSubcategories(4), new TicketsStates(2)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(2), new TicketsSubcategories(5), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(2), new TicketsSubcategories(5), new TicketsStates(2)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(2), new TicketsSubcategories(6), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(2), new TicketsSubcategories(6), new TicketsStates(2)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(2), new TicketsSubcategories(7), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(2), new TicketsSubcategories(7), new TicketsStates(2))


        );
        return new ResponseEntity(supportAdminPVTACounts, HttpStatus.OK);
    }

    @ApiOperation(value = "Cantidad de Notificaciones de Soporte de un Admin de Infraestructura"
            ,notes = "Se obtiene la cantidad de notificaciones de soporte para un administrador de Infraestructura")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/support/admininfra/general/count")
    public ResponseEntity<SupportAdminInfraCounts> getCountInfra(){
        SupportAdminInfraCounts supportAdminInfraCounts = new SupportAdminInfraCounts(

                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(3), new TicketsSubcategories(8), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(3), new TicketsSubcategories(8), new TicketsStates(2))


        );
        return new ResponseEntity(supportAdminInfraCounts, HttpStatus.OK);
    }

    @ApiOperation(value = "Cantidad de Notificaciones de Soporte de un Admin de Finanzas"
            ,notes = "Se obtiene la cantidad de notificaciones de soporte para un administrador de Finanzas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/support/adminfin/general/count")
    public ResponseEntity<SupportAdminFinanzasCounts> getCountFinanzas(){
        SupportAdminFinanzasCounts supportAdminFinanzasCounts = new SupportAdminFinanzasCounts(

                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(5), new TicketsSubcategories(23), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(5), new TicketsSubcategories(23), new TicketsStates(2))


        );
        return new ResponseEntity(supportAdminFinanzasCounts, HttpStatus.OK);
    }

    @ApiOperation(value = "Cantidad de Notificaciones de Soporte de un Admin de Administración"
            ,notes = "Se obtiene la cantidad de notificaciones de soporte para un administrador de Administración")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/support/adminadm/general/count")
    public ResponseEntity<SupportAdminADMCounts> getCountADM(){
        SupportAdminADMCounts supportAdminADMCounts = new SupportAdminADMCounts(

                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(4), new TicketsSubcategories(9), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(4), new TicketsSubcategories(9), new TicketsStates(2)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(4), new TicketsSubcategories(18), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(4), new TicketsSubcategories(18), new TicketsStates(2)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(4), new TicketsSubcategories(19), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(4), new TicketsSubcategories(19), new TicketsStates(2)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(4), new TicketsSubcategories(20), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(4), new TicketsSubcategories(20), new TicketsStates(2)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(4), new TicketsSubcategories(21), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(4), new TicketsSubcategories(21), new TicketsStates(2)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(4), new TicketsSubcategories(22), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(4), new TicketsSubcategories(22), new TicketsStates(2))

                );
        return new ResponseEntity(supportAdminADMCounts, HttpStatus.OK);
    }


    @ApiOperation(value = "Cantidad de Usuarios en línea por sucursal"
            ,notes = "Se obtiene la cantidad de usuarios en línea por sucursal física")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/onlines/branchs")
    public ResponseEntity<OnlinesBranchs> getOnlinesForBranchs(){
        OnlinesBranchs onlinesBranchs = new OnlinesBranchs(

                authController.countActiveUsersForBranch(new Branch(1)),
                authController.countActiveUsersForBranch(new Branch(2)),
                authController.countActiveUsersForBranch(new Branch(4)),
                authController.countActiveUsersForBranch(new Branch(5)),
                authController.countActiveUsersForBranch(new Branch(6)),
                authController.countActiveUsersForBranch(new Branch(7)),
                authController.countActiveUsersForBranch(new Branch(3)),
                authController.countActiveUsersForBranch(new Branch(9)),
                authController.countActiveUsersForBranch(new Branch(8)),
                authController.countActiveUsersForBranch(new Branch(10)),
                authController.countActiveUsersForBranch(new Branch(11)),
                authController.countActiveUsersForBranch(new Branch(12))

        );
        return new ResponseEntity(onlinesBranchs, HttpStatus.OK);
    }

    @ApiOperation(value = "Cantidad de Notificaciones de Soporte de un Admin de QA"
            ,notes = "Se obtiene la cantidad de notificaciones de soporte para un administrador de QA")
        @ApiResponses(value = {
                @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
                @ApiResponse(code = 401, message = "Usuario no autorizado"),
                @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
                @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
        @PreAuthorize("hasRole('USER')")
        @GetMapping("/support/adminqa/general/count")
        public ResponseEntity<SupportAdminQACounts> getCountQA(){
        SupportAdminQACounts supportAdminQACounts = new SupportAdminQACounts(

                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(7), new TicketsSubcategories(35), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(7), new TicketsSubcategories(35), new TicketsStates(2)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(7), new TicketsSubcategories(36), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(7), new TicketsSubcategories(36), new TicketsStates(2)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(7), new TicketsSubcategories(37), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(7), new TicketsSubcategories(37), new TicketsStates(2)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(7), new TicketsSubcategories(38), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(7), new TicketsSubcategories(38), new TicketsStates(2)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(7), new TicketsSubcategories(39), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(7), new TicketsSubcategories(39), new TicketsStates(2)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(7), new TicketsSubcategories(40), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(7), new TicketsSubcategories(40), new TicketsStates(2)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(7), new TicketsSubcategories(41), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(7), new TicketsSubcategories(41), new TicketsStates(2)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(7), new TicketsSubcategories(42), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(7), new TicketsSubcategories(42), new TicketsStates(2))
        );
        return new ResponseEntity(supportAdminQACounts, HttpStatus.OK);
        }

        @ApiOperation(value = "Cantidad de Notificaciones de Soporte de un Admin de Compras"
        ,notes = "Se obtiene la cantidad de notificaciones de soporte para un administrador de Compras")
        @ApiResponses(value = {
                @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
                @ApiResponse(code = 401, message = "Usuario no autorizado"),
                @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
                @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
        @PreAuthorize("hasRole('USER')")
        @GetMapping("/support/admincompras/general/count")
        public ResponseEntity<SupportAdminComprasCounts> getCountCompras(){
        SupportAdminComprasCounts supportAdminComprasCounts = new SupportAdminComprasCounts(

                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(9), new TicketsSubcategories(44), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(9), new TicketsSubcategories(44), new TicketsStates(2))
        );
        return new ResponseEntity<>(supportAdminComprasCounts, HttpStatus.OK);
        }

        @ApiOperation(value = "Cantidad de Notificaciones de Soporte de un Admin de Logística"
        ,notes = "Se obtiene la cantidad de notificaciones de soporte para un administrador de Logística")
        @ApiResponses(value = {
                @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
                @ApiResponse(code = 401, message = "Usuario no autorizado"),
                @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
                @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
        @PreAuthorize("hasRole('USER')")
        @GetMapping("/support/adminlogistica/general/count")
        public ResponseEntity<SupportAdminLogiCounts> getCountLogistica(){
        SupportAdminLogiCounts supportAdminLogisticaCounts = new SupportAdminLogiCounts(

                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(9), new TicketsSubcategories(44), new TicketsStates(1)),
                ticketsController.countTicketsByCategorySubcategoryAndState(new TicketsCategories(9), new TicketsSubcategories(44), new TicketsStates(2))
        );
        return new ResponseEntity<>(supportAdminLogisticaCounts, HttpStatus.OK);
        }

}
