package com.opencars.netgo.test.controller;

import com.opencars.netgo.news.letters.entity.Letter;
import com.opencars.netgo.news.letters.service.LettersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Testing")
public class TestController {

    @Autowired
    LettersService lettersService;

    @ApiOperation(value = "Enpoint Libre de Test"
            ,notes = "Se realiza una operación cualquiera, a fin de verificar el funcionamiento de la base de datos y la respuesta del servidor")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @GetMapping("/test")
    public ResponseEntity<Letter> getCountLetters(){
        List<Letter> list = lettersService.getCurrents();
        int count = list.size();
        return new ResponseEntity(count, HttpStatus.OK);
    }
}
