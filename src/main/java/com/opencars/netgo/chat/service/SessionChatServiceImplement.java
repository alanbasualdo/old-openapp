package com.opencars.netgo.chat.service;

import com.opencars.netgo.chat.entity.SessionChat;
import com.opencars.netgo.chat.repository.SessionChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionChatServiceImplement implements SessionChatService{

    @Autowired
    SessionChatRepository sessionChatRepository;

    @Override
    public SessionChat save(SessionChat sessionChat) {
        return sessionChatRepository.save(sessionChat);
    }
    @Override
    public Optional<SessionChat> getOne(String id){
        return sessionChatRepository.findById(id);
    }
    @Override
    public void deleteByClientId(String clientId){
        sessionChatRepository.deleteByClientId(clientId);
    }
}
