package com.opencars.netgo.ipbox.controller;

import com.opencars.netgo.ipbox.model.UrlEncoded;
import com.opencars.netgo.users.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;

@RestController
@RequestMapping("/api")
@CrossOrigin()
@Api(tags = "Controlador de API de Encriptación para IPBOX")
public class IpboxController {

    @Autowired
    UserService userService;

    private String uriLogin = "https://drive.alpha2000.com.ar/?Login=";
    private String apiEncryption = "https://service-encryption.opencars.com.ar/api/";

    HttpHeaders headers = new HttpHeaders();

    @ApiOperation(value = "Obtener datos de sesión encriptados"
            ,notes = "Se obtiene una encriptación de los datos de sesión del usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/ipbox/encryption/{code}")
    public ResponseEntity<UrlEncoded> getUriLogin(@PathVariable Long code){
        LocalDate date = LocalDate.now();

        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        String uri = apiEncryption + "/encryption/" + date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() + "/" + code;
        RestTemplate restTemplate = new RestTemplate();
        UrlEncoded dataEncryption = restTemplate.exchange(uri, HttpMethod.GET, entity, UrlEncoded.class).getBody();
        UrlEncoded objFinal = new UrlEncoded(uriLogin + dataEncryption.getUrl());
        return ResponseEntity.ok(objFinal);
    }

    @ApiOperation(value = "Obtener datos de sesión encriptados para el usuario administrador de IPBOX"
            ,notes = "Se obtiene una encriptación de los datos de sesión del usuario administrador de IPBOX")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('ADMINRRLL') or hasRole('ADMINCCHH')")
    @GetMapping("/ipbox/encryption/admin")
    public ResponseEntity<UrlEncoded> getUriLoginAdmin(){
        LocalDate date = LocalDate.now();

        String adminUser = "OpenCars";

        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        String uri = apiEncryption + "/encryption/" + date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() + "/" + adminUser;
        RestTemplate restTemplate = new RestTemplate();
        UrlEncoded dataEncryption = restTemplate.exchange(uri, HttpMethod.GET, entity, UrlEncoded.class).getBody();
        UrlEncoded objFinal = new UrlEncoded(uriLogin + dataEncryption.getUrl());
        return ResponseEntity.ok(objFinal);
    }
}
