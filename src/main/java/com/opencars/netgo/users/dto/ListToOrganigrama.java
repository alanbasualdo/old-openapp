package com.opencars.netgo.users.dto;

import com.opencars.netgo.users.entity.User;

import java.util.List;

public class ListToOrganigrama {

    private int linea;
    private List<ColaboratorOrganigrama> list;

    public ListToOrganigrama() {
    }

    public ListToOrganigrama(int linea, List<ColaboratorOrganigrama> list) {
        this.linea = linea;
        this.list = list;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public List<ColaboratorOrganigrama> getList() {
        return list;
    }

    public void setList(List<ColaboratorOrganigrama> list) {
        this.list = list;
    }
}
