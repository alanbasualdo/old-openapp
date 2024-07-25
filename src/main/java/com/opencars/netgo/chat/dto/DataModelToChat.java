package com.opencars.netgo.chat.dto;

import com.opencars.netgo.support.tickets.entity.TicketsComents;

import java.io.Serializable;

public class DataModelToChat implements Serializable {

    private Long ticketId;
    private TicketsComents comentModel;

    public DataModelToChat() {
    }

    public DataModelToChat(Long ticketId, TicketsComents comentModel) {
        this.ticketId = ticketId;
        this.comentModel = comentModel;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public TicketsComents getComentModel() {
        return comentModel;
    }

    public void setComentModel(TicketsComents comentModel) {
        this.comentModel = comentModel;
    }

}
