package com.opencars.netgo.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api")
public class ChatTicketsController {

    @Autowired
    private SimpMessagingTemplate webSocket;

    @MessageMapping("/escribiendo/{id}")
    @SendTo("/chat/escribiendo/{id}")
    public String isWriting(String user){
        return user.concat(" está escribiendo...");
    }

    /* @MessageMapping("/mensaje/{id}/coments")
    @SendTo("/chat/mensaje/{id}/coments")
    public String receiptMessage(@DestinationVariable String id, SimpleMessage message) {

        Arrays.stream(message.getParameters()).forEach(p -> {
            System.out.println(p.toString());
        });

        String receipt = "recibo " + id;
        return receipt;
    }

    @MessageMapping("/files/{id}/coments")
    @SendTo("/chat/files/{id}/coments")
    public String receiptFiles(@DestinationVariable String id, @RequestParam("files") MultipartFile[] files) {

        String receipt = "archivos para el id " + id + " cantidad: " + files.length;
        return receipt;
    }*/

}
