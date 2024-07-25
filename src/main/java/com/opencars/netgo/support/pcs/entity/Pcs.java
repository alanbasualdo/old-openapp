package com.opencars.netgo.support.pcs.entity;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@ApiModel(value = "Entidad Modelo de Pcs", description = "Información completa que se almacena de la pc")
public class Pcs implements Serializable {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String sn;
    @NotNull
    @OneToOne
    private PcTypes type;
    @NotNull
    private LocalDate buyDate;
    private String invoice;
    @NotNull
    @OneToOne
    private Model model;
    @NotNull
    @OneToOne
    private Processor processor;
    @NotNull
    @OneToOne
    private Memory memory;
    @NotNull
    @OneToOne
    private Disk disk;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @NotEmpty
    private String assigned;
    @NotNull
    @OneToOne
    private Brands company;
    @NotNull
    @OneToOne
    private Branch branch;

    public Pcs() {
    }

    public Pcs(int id) {
        this.id = id;
    }

    public Pcs(String sn, PcTypes type, LocalDate buyDate, String invoice, Model model, Processor processor, Memory ram, Disk disk, User user, String assigned, Brands company, Branch branch) {
        this.sn = sn;
        this.type = type;
        this.buyDate = buyDate;
        this.invoice = invoice;
        this.model = model;
        this.processor = processor;
        this.memory = ram;
        this.disk = disk;
        this.user = user;
        this.assigned = assigned;
        this.company = company;
        this.branch = branch;

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

    public PcTypes getType() {
        return type;
    }

    public void setType(PcTypes type) {
        this.type = type;
    }

    public LocalDate getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(LocalDate buyDate) {
        this.buyDate = buyDate;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public Memory getMemory() {
        return memory;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    public Disk getDisk() {
        return disk;
    }

    public void setDisk(Disk disk) {
        this.disk = disk;
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

    public Brands getCompany() {
        return company;
    }

    public void setCompany(Brands company) {
        this.company = company;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    private static final long serialVersionUID = -6357055441137782881L;
}
