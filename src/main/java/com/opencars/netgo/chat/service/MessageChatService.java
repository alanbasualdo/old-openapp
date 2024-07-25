package com.opencars.netgo.chat.service;

import com.opencars.netgo.chat.entity.MessageChat;

import java.util.List;

public interface MessageChatService {

    public List<MessageChat> obtainLast20Messages();
    public MessageChat save(MessageChat messageChat);
    public long getTotalMessages();
    public List<MessageChat> obtainMessages(int startIndex, long endIndex);
}
