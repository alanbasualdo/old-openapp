package com.opencars.netgo.support.tickets.dto;

import com.opencars.netgo.support.tickets.entity.TicketsCategories;
import com.opencars.netgo.users.entity.User;

public class CardTechniciansResume {

    private User colaborator;
    private TicketsCategories category;

    public CardTechniciansResume() {
    }

    public CardTechniciansResume(User colaborator, TicketsCategories category) {
        this.colaborator = colaborator;
        this.category = category;
    }

    public User getColaborator() {
        return colaborator;
    }

    public void setColaborator(User colaborator) {
        this.colaborator = colaborator;
    }

    public TicketsCategories getCategory() {
        return category;
    }

    public void setCategory(TicketsCategories category) {
        this.category = category;
    }
}
