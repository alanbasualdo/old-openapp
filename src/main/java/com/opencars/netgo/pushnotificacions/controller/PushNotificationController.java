package com.opencars.netgo.pushnotificacions.controller;

import com.opencars.netgo.pushnotificacions.dto.PushNotificationRequest;
import com.opencars.netgo.pushnotificacions.dto.PushNotificationResponse;
import com.opencars.netgo.pushnotificacions.service.PushNotificacionService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@CrossOrigin
@Api(tags = "Api de Notificaciones Push")
public class PushNotificationController {

    private PushNotificacionService pushNotificacionService;

    public PushNotificationController(PushNotificacionService pushNotificacionService) {
        this.pushNotificacionService = pushNotificacionService;
    }

    @PostMapping("/notification/token")
    public ResponseEntity sendTokenNotification(@RequestBody PushNotificationRequest request) {
        pushNotificacionService.sendPushNotificationToToken(request);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notifications has been sent."), HttpStatus.OK);
    }
}
