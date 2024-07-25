package com.opencars.netgo.chat.repository;

import com.opencars.netgo.chat.entity.MessageChat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageChatRepository extends MongoRepository<MessageChat, String> {
    public List<MessageChat> findFirst20ByOrderByDateDesc();
    public long count();
    Page<MessageChat> findAll(Pageable pageable);

}
