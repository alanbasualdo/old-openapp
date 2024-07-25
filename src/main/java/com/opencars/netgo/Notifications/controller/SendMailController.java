package com.opencars.netgo.Notifications.controller;

import com.opencars.netgo.Notifications.dto.MailResponse;
import com.opencars.netgo.Notifications.service.SendMailService;
import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.msgs.Msg;
import com.opencars.netgo.users.controller.UsersController;
import com.opencars.netgo.users.dto.UserId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.passay.CharacterOccurrencesRule.ERROR_CODE;

@Controller
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Envío de Correos")
public class SendMailController {
    @Autowired
    SendMailService sendMailService;
    @Autowired
    UsersController usersController;

    @ApiOperation(value = "Recuperación de clave"
            ,notes = "Se envía una solicitud para recuperar la clave de la plataforma")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PostMapping("/sendMail")
    public ResponseEntity<MailResponse> sendNewPassword(@RequestParam("username") String username){
        String message = "";
        String subject = "";
        String body = "";
        try{

            UserId user = this.usersController.getUserIdByUsername(username).getBody();

            PasswordGenerator gen = new PasswordGenerator();
            CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
            CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
            lowerCaseRule.setNumberOfCharacters(2);

            CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
            CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
            upperCaseRule.setNumberOfCharacters(2);

            CharacterData digitChars = EnglishCharacterData.Digit;
            CharacterRule digitRule = new CharacterRule(digitChars);
            digitRule.setNumberOfCharacters(2);

            CharacterData specialChars = new CharacterData() {
                public String getErrorCode() {
                    return ERROR_CODE;
                }

                public String getCharacters() {
                    return "!@#$%^&*()_+";
                }
            };

            CharacterRule splCharRule = new CharacterRule(specialChars);
            splCharRule.setNumberOfCharacters(2);

            String password = gen.generatePassword(10, splCharRule, lowerCaseRule,
                    upperCaseRule, digitRule);

            this.usersController.updatePasswordByAdmin(user.getId(), password);

            subject = "NUEVA CLAVE DE ACCESO - INTRANET OPENCARS";

            body = "Su nueva clave de acceso temporal es: " + password;

            message = "La solicitud se ha enviado con éxito.";

            if (!Objects.equals(user.getMail(), "")) {

                sendMailService.sendMail("notificaciones@opencars.com.ar", user.getMail(), subject, body);

            }else {
                sendMailService.sendMail("notificaciones@opencars.com.ar", "notificaciones@opencars.com.ar", subject, body);
            }
            return ResponseEntity.status(HttpStatus.OK).body(new MailResponse(message, HttpStatus.CREATED.value()));

        }catch (Exception e){
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MailResponse(message, HttpStatus.EXPECTATION_FAILED.value()));
        }
    }

    @ApiOperation(value = "Envío de correo con peritaje adjunto"
            ,notes = "Se envía un correo con un peritaje adjunto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PostMapping("/sendMail/attach")
    public ResponseEntity<?> sendMailWithAttach(@RequestParam("client") String client, @RequestParam("files") MultipartFile file){
        String message;
        String subject;
        try{

            subject = "Peritaje de su vehículo - Grupo Opencars";
            message = "La solicitud se ha enviado con éxito.";
            sendMailService.sendMailWithFile("notificaciones@opencars.com.ar", client, subject, file);

            return ResponseEntity.status(HttpStatus.OK).body(new Msg(message, HttpStatus.OK.value()));

        }catch (Exception e){
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @ApiOperation(value = "Envío de correo con archivos adjuntos"
            ,notes = "Se envía un correo con archivos adjuntos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PostMapping("/sendMail/attachs")
    public ResponseEntity<?> sendMailWithAttachs(@RequestParam("id") long id, @RequestParam("op") String op, @RequestParam("branch") Branch branch, @RequestParam("provider") String provider, @RequestParam("files") MultipartFile[] files){

        List<MultipartFile> filesList = Arrays.asList(files);

        String message;
        String subject;
        try{

            subject = "Comprobantes de pagos | " + branch.getBrandsCompany().getCompany().getName() + " S. A";
            message = "La solicitud se ha enviado con éxito.";
            sendMailService.sendMailWithFiles(id, op, branch, "notificaciones@opencars.com.ar", provider, subject, filesList);

            return ResponseEntity.status(HttpStatus.OK).body(new Msg(message, HttpStatus.OK.value()));

        }catch (Exception e){
            message = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Msg(message, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
