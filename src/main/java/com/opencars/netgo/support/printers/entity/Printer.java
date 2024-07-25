package com.opencars.netgo.support.printers.entity;

import com.opencars.netgo.dms.providers.entity.ProvidersSales;
import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.locations.entity.Company;
import com.opencars.netgo.locations.entity.Sector;
import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Impresora", description = "Información completa que se almacena de una impresora")
public class Printer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    private String model;
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
    @ManyToOne
    @JoinColumn(name = "sector_id")
    private Sector sector;
    private String ip;
    private String code;
    @NotEmpty
    private String assignedTo;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @NotNull
    private boolean onJson;
    private boolean isOwn;
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private ProvidersSales provider;
    private String hostname;
    @ManyToOne
    @JoinColumn(name = "invoiced_to_id")
    private Company invoicedTo;
    private String toner;
    @ManyToOne
    @JoinColumn(name = "provider_toner_id")
    private ProvidersSales providerToner;
    @NotEmpty
    private String state;
    @NotEmpty
    private String typeDevice;

    public Printer() {
    }

    public Printer(String model, Branch branch, Sector sector, String ip, String code, String assignedTo, User user, boolean onJson, boolean isOwn, ProvidersSales provider, String hostname, Company invoicedTo, String toner, ProvidersSales providerToner, String state, String typeDevice) {
        this.model = model;
        this.branch = branch;
        this.sector = sector;
        this.ip = ip;
        this.code = code;
        this.assignedTo = assignedTo;
        this.user = user;
        this.onJson = onJson;
        this.isOwn = isOwn;
        this.provider = provider;
        this.hostname = hostname;
        this.invoicedTo = invoicedTo;
        this.toner = toner;
        this.providerToner = providerToner;
        this.state = state;
        this.typeDevice = typeDevice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isOnJson() {
        return onJson;
    }

    public void setOnJson(boolean onJson) {
        this.onJson = onJson;
    }

    public boolean isOwn() {
        return isOwn;
    }

    public void setOwn(boolean own) {
        isOwn = own;
    }

    public ProvidersSales getProvider() {
        return provider;
    }

    public void setProvider(ProvidersSales provider) {
        this.provider = provider;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Company getInvoicedTo() {
        return invoicedTo;
    }

    public void setInvoicedTo(Company invoicedTo) {
        this.invoicedTo = invoicedTo;
    }

    public String getToner() {
        return toner;
    }

    public void setToner(String toner) {
        this.toner = toner;
    }

    public ProvidersSales getProviderToner() {
        return providerToner;
    }

    public void setProviderToner(ProvidersSales providerToner) {
        this.providerToner = providerToner;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTypeDevice() {
        return typeDevice;
    }

    public void setTypeDevice(String typeDevice) {
        this.typeDevice = typeDevice;
    }
}
