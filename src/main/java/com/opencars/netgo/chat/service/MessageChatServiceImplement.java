package com.opencars.netgo.chat.service;

import com.opencars.netgo.chat.entity.MessageChat;
import com.opencars.netgo.chat.repository.MessageChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class MessageChatServiceImplement implements MessageChatService {

    @Autowired
    MessageChatRepository messageChatRepository;

    @Override
    public List<MessageChat> obtainLast20Messages() {
        return messageChatRepository.findFirst20ByOrderByDateDesc();
    }

    @Override
    public MessageChat save(MessageChat messageChat) {
        return messageChatRepository.save(messageChat);
    }

    @Override
    public long getTotalMessages() {
        return messageChatRepository.count();
    }

    @Override
    public List<MessageChat> obtainMessages(int startIndex, long endIndex) {
        int pageSize = 20;
        int pageNumber = (int) Math.floor((startIndex + pageSize) / pageSize) - 1;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("date").descending());
        List<MessageChat> messages = messageChatRepository.findAll(pageable).getContent();

        // Creamos una nueva lista mutable y copiamos los elementos de la lista inmodificable
        List<MessageChat> mutableList = new ArrayList<>(messages);

        // Ordenamos la lista mutable
        Collections.sort(mutableList, new Comparator<MessageChat>() {
            public int compare(MessageChat message1, MessageChat message2) {
                return message1.getDate().compareTo(message2.getDate());
            }
        });

        // Devolvemos la lista mutable ordenada
        return mutableList;

    }
}
