package com.opencars.netgo.support.tickets.entity;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.locations.entity.Company;
import com.opencars.netgo.support.tickets.dto.Tools;
import com.opencars.netgo.users.entity.Position;
import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "ticketsnewentry")
@ApiModel(value = "Entidad Modelo de Ticket de Nuevo Ingreso", description = "Información completa que se almacena del ticket de nuevo ingreso")
public class TicketsNewEntry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private LocalDate entrydate;
    @NotNull
    @OneToOne
    private User responsable;
    @NotNull
    private String name;
    @NotNull
    private String cuil;
    @NotNull
    private LocalDate birthdate;
    @NotNull
    @OneToOne
    private Branch branch;
    @NotNull
    @OneToOne
    private Company payroll;
    @NotNull
    @OneToOne
    private Position position;
    @NotNull
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private Tools[] tools;
    @Column(columnDefinition = "TEXT")
    private String description;

    public TicketsNewEntry() {
    }

    public TicketsNewEntry(int id) {
        this.id = id;
    }

    public TicketsNewEntry(LocalDate entrydate, User responsable, String name, String cuil, LocalDate birthdate, Branch branch, Company payroll, Position position, @NotNull Tools[] tools, String description) {
        this.entrydate = entrydate;
        this.responsable = responsable;
        this.name = name;
        this.cuil = cuil;
        this.birthdate = birthdate;
        this.branch = branch;
        this.payroll = payroll;
        this.position = position;
        this.tools = tools;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(LocalDate entrydate) {
        this.entrydate = entrydate;
    }

    public User getResponsable() {
        return responsable;
    }

    public void setResponsable(User responsable) {
        this.responsable = responsable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Company getPayroll() {
        return payroll;
    }

    public void setPayroll(Company payroll) {
        this.payroll = payroll;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Tools[] getTools() {
        return tools;
    }

    public void setTools(Tools[] tools) {
        this.tools = tools;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
