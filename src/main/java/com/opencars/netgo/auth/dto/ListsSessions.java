package com.opencars.netgo.auth.dto;

import com.opencars.netgo.users.dto.UsersConnections;

import java.util.List;

public class ListsSessions {

    List<UsersConnections> listActives;
    List<UsersConnections> listInactives;

    public ListsSessions() {
    }

    public ListsSessions(List<UsersConnections> listActives, List<UsersConnections> listInactives) {
        this.listActives = listActives;
        this.listInactives = listInactives;
    }

    public List<UsersConnections> getListActives() {
        return listActives;
    }

    public void setListActives(List<UsersConnections> listActives) {
        this.listActives = listActives;
    }

    public List<UsersConnections> getListInactives() {
        return listInactives;
    }

    public void setListInactives(List<UsersConnections> listInactives) {
        this.listInactives = listInactives;
    }
}
