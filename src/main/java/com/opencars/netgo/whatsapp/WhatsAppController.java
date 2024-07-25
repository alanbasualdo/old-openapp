package com.opencars.netgo.whatsapp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Controlador de Envío de Mensajes por WhatsApp")
public class WhatsAppController {

    @ApiOperation(value = "Envío de Mensaje por WhatsApp"
            ,notes = "Se envía un mensaje por WhatsApp")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK. El recurso se obtiene correctamente"),
            @ApiResponse(code = 201, message = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello."),
            @ApiResponse(code = 401, message = "Usuario no autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado por prohibición"),
            @ApiResponse(code = 404, message = "El endpoint solicitado no existe") })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/message")
    public void sendMessage() throws IOException {

        String token = "EAACZAhGA4WkcBAJDKR6r0NUxxnI13kayVCSOBVReMPpRUqG3ZAQaNrSRECEF4aiwDn7mHZB82shs6e901pM2Njcl1q1GYYDvdG5JvI1LV9RnQdvBePYnuN6zRbvqbBzarM9j88Gz94DjmglgWEbml4iEGfLGM9iNBJFgWTRI0wReYjEiVdZBT31MNYPhsRDqIHaVeP9DiwZDZD";
        String phone = "54236154304307";
        String idNumber = "116873908037750";
        URL url = new URL("https://graph.facebook.com/v16.0/" + idNumber + "/messages");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");
        httpConn.setRequestProperty("Authorization", "Bearer " + token);
        httpConn.setRequestProperty("Content-Type", "application/json; application/x-www-form-urlencoded; charset=UTF-8");
        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
        writer.write("{ "
                + "\"messaging_product\": \"whatsapp\", "
                + "\"recipient_type\": \"individual\", "
                + "\"to\": \"" + phone + "\", "
                + "\"type\": \"text\", "
                + "\"text\": "
                + "   { \"preview_url\": \"true\", "
                + "     \"body\": { \"test\" } "
                + "   } "
                + "}");

        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();
        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String rsp = s.hasNext() ? s.next() : "";
        System.out.println(rsp);

    }

}
