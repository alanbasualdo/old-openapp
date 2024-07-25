package com.opencars.netgo.chat.controller;

import com.opencars.netgo.chat.dto.HistoryRequest;
import com.opencars.netgo.chat.entity.MessageChat;
import com.opencars.netgo.chat.entity.SessionChat;
import com.opencars.netgo.chat.service.MessageChatService;
import com.opencars.netgo.chat.service.SessionChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/api")
public class ChatController {

    @Autowired
    MessageChatService messageChatService;

    @Autowired
    SessionChatService sessionChatService;

    @Autowired
    private SimpMessagingTemplate webSocket;

    private String[] colors = {
            "#D98880", "#F1948A", "#C39BD3", "#BB8FCE",
            "#7FB3D5", "#85C1E9", "#76D7C4", "#73C6B6",
            "#7DCEA0", "#82E0AA", "#F7DC6F", "#F8C471",
            "#F0B27A", "#E59866", "#BFC9CA", "#B2BABB",
            "#85929E", "#808B96"
    };

    @MessageMapping("/mensaje")
    @SendTo("/chat/mensaje")
    public MessageChat receiptMessage(MessageChat messageChat){
        LocalDateTime date = LocalDateTime.now();
        messageChat.setDate(date);
        messageChat.setUsername(messageChat.getUsername());
        if(messageChat.getType().equals("NUEVO_USUARIO")){
            messageChat.setColor(colors[new Random().nextInt(colors.length)]);
            messageChat.setText(messageChat.getUsername() + " se ha conectado.");

            SessionChat session = new SessionChat();
            session.setUsername(messageChat.getUsername());
            session.setClientId(messageChat.getClientId());
            session.setOnline(true);
            sessionChatService.save(session);
        }else{
            messageChatService.save(messageChat);
        }
        return messageChat;
    }

    @MessageMapping("/salir")
    @SendTo("/chat/salir")
    public void logout(String clientId){
        sessionChatService.deleteByClientId(clientId);
    }

    @MessageMapping("/escribiendo")
    @SendTo("/chat/escribiendo")
    public String isWriting(String username){
        return username.concat(" está escribiendo...");
    }

    @MessageMapping("/historial")
    public void getHistory(String clientId){
        webSocket.convertAndSend("/chat/historial/" + clientId, messageChatService.obtainLast20Messages());
    }

    @MessageMapping("/historial-previo")
    public void getHistoryPreview(HistoryRequest request) {
        int messagesToObtain = 20;
        long totalMessages = messageChatService.getTotalMessages();

        if (request.getStartIndex() >= totalMessages) {
            // Si el índice del primer mensaje a obtener es mayor que el total de mensajes, no hay más mensajes que cargar.
            return;
        }

        long endIndex = Math.min(request.getStartIndex() + messagesToObtain, totalMessages);

        List<MessageChat> messages = messageChatService.obtainMessages(request.getStartIndex(), endIndex);

        //esto es lo que se recibe de la solicitud en el front, es el endpoint donde se publica esa info
        webSocket.convertAndSend("/chat/historial-previo/" + request.getClientId(), messages);
    }

}
