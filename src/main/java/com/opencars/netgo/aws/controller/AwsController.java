package com.opencars.netgo.aws.controller;

import com.opencars.netgo.aws.controller.dto.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
@CrossOrigin()
@Api(tags = "Controlador de API de InfoAuto")
public class AwsController {

    private String apiAws = "https://api-gateway.opencars.com.ar/prod";

    @ApiOperation(value = "Obtener estado del Servidor"
            ,notes = "Se obtiene el estado del servidor.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/estado/conciliaciones")
    public Response getServerState(){
        String uri = apiAws + "/estado/conciliaciones";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        Response response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), Response.class).getBody();
        return response;
    }

    @ApiOperation(value = "Iniciar Servidor"
            ,notes = "Se iniciar el servidor.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/iniciar/conciliaciones")
    public Response initServer(){
        String uri = apiAws + "/iniciar/conciliaciones";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        Response response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), Response.class).getBody();
        return response;
    }

    @ApiOperation(value = "Detener el Servidor"
            ,notes = "Se detiene el servidor.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/parar/conciliaciones")
    public Response stopServer(){
        String uri = apiAws + "/parar/conciliaciones";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        Response response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), Response.class).getBody();
        return response;
    }
}
