package com.opencars.netgo.chat.service;

import com.opencars.netgo.chat.entity.SessionChat;

import java.util.Optional;

public interface SessionChatService {
    SessionChat save(SessionChat sessionChat);

    Optional<SessionChat> getOne(String id);

    void deleteByClientId(String clientId);
}
