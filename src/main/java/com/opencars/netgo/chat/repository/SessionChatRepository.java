package com.opencars.netgo.chat.repository;

import com.opencars.netgo.chat.entity.SessionChat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionChatRepository extends MongoRepository<SessionChat, String> {

    void deleteByClientId(String clientId);
}
