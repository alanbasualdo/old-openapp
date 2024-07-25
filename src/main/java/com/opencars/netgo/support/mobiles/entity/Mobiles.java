package com.opencars.netgo.support.mobiles.entity;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@ApiModel(value = "Entidad Modelo de Celulares", description = "Información completa que se almacena de un celular")
public class Mobiles implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    private String sn;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "model_id")
    private ModelsMobiles model;
    private String invoice;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @NotEmpty
    private String assigned;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
    private String observation;
    @ManyToOne
    @JoinColumn(name = "line_id")
    private LinesMobiles line;
    @NotNull
    private LocalDate buyDate;

    public Mobiles() {
    }

    public Mobiles(String sn, ModelsMobiles model, String invoice, User user, String assigned, Branch branch, String observation, LinesMobiles line, LocalDate buyDate) {
        this.sn = sn;
        this.model = model;
        this.invoice = invoice;
        this.user = user;
        this.assigned = assigned;
        this.branch = branch;
        this.observation = observation;
        this.line = line;
        this.buyDate = buyDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public ModelsMobiles getModel() {
        return model;
    }

    public void setModel(ModelsMobiles model) {
        this.model = model;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAssigned() {
        return assigned;
    }

    public void setAssigned(String assigned) {
        this.assigned = assigned;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public LinesMobiles getLine() {
        return line;
    }

    public void setLine(LinesMobiles line) {
        this.line = line;
    }

    public LocalDate getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(LocalDate buyDate) {
        this.buyDate = buyDate;
    }
}
