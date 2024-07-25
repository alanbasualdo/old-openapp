package com.opencars.netgo.support.tickets.entity;

import com.opencars.netgo.support.tickets.dto.Members;
import com.opencars.netgo.support.tickets.dto.Tools;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ticketslowaccounts")
@ApiModel(value = "Entidad Modelo de Tickets de Baja de Cuentas", description = "Información completa que se almacena del ticket de baja de cuenta")
public class TicketsLowAccounts implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String colaborator;
    @NotNull
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private Tools[] accounts;
    private String redirect;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private Members[] members;
    @Column(columnDefinition = "TEXT")
    private String description;

    public TicketsLowAccounts() {
    }

    public TicketsLowAccounts(int id) {
        this.id = id;
    }

    public TicketsLowAccounts(String colaborator, @NotNull Tools[] accounts, String redirect, Members[] members, String description) {
        this.colaborator = colaborator;
        this.accounts = accounts;
        this.redirect = redirect;
        this.members = members;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColaborator() {
        return colaborator;
    }

    public void setColaborator(String colaborator) {
        this.colaborator = colaborator;
    }

    public Tools[] getAccounts() {
        return accounts;
    }

    public void setAccounts(Tools[] accounts) {
        this.accounts = accounts;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public Members[] getMembers() {
        return members;
    }

    public void setMembers(Members[] members) {
        this.members = members;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
