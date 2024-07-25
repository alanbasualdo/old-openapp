package com.opencars.netgo.chat.dto;

import java.io.Serializable;

public class HistoryRequest implements Serializable {

    int startIndex;
    String clientId;

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
