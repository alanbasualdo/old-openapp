package com.opencars.netgo.pushnotificacions.service;

import com.opencars.netgo.pushnotificacions.dto.PushNotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PushNotificacionService {

    private Logger logger = LoggerFactory.getLogger(PushNotificacionService.class);

    private FCMService fcmService;

    public PushNotificacionService(FCMService fcmService){
        this.fcmService = fcmService;
    }

    public void sendPushNotificationToToken(PushNotificationRequest request){
        try{
            fcmService.sendMessageToken(request);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }
}
