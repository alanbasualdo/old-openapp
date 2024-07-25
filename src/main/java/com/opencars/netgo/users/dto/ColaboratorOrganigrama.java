package com.opencars.netgo.users.dto;

import com.opencars.netgo.users.entity.User;

import java.util.List;

public class ColaboratorOrganigrama {

    private User colaborator;
    private List<ColaboratorOrganigrama> listPeopleInCharge;

    public ColaboratorOrganigrama() {
    }

    public ColaboratorOrganigrama(User colaborator, List<ColaboratorOrganigrama> listPeopleInCharge) {
        this.colaborator = colaborator;
        this.listPeopleInCharge = listPeopleInCharge;
    }

    public ColaboratorOrganigrama(User colaborator) {
        this.colaborator = colaborator;
    }

    public User getColaborator() {
        return colaborator;
    }

    public void setColaborator(User colaborator) {
        this.colaborator = colaborator;
    }

    public List<ColaboratorOrganigrama> getListPeopleInCharge() {
        return listPeopleInCharge;
    }

    public void setListPeopleInCharge(List<ColaboratorOrganigrama> listPeopleInCharge) {
        this.listPeopleInCharge = listPeopleInCharge;
    }
}
