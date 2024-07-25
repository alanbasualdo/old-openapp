package com.opencars.netgo.support.mobiles.entity;

import com.opencars.netgo.dms.providers.entity.ProvidersSales;
import com.opencars.netgo.locations.entity.Company;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Líneas Móviles", description = "Información completa que se almacena de una línea móvil")
public class LinesMobiles implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    private String line;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private ProvidersSales provider;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public LinesMobiles() {
    }

    public LinesMobiles(int id) {
        this.id = id;
    }

    public LinesMobiles(String line, Plan plan, ProvidersSales provider, Company company) {
        this.line = line;
        this.plan = plan;
        this.provider = provider;
        this.company = company;
    }

    public LinesMobiles(int id, String line, Plan plan, ProvidersSales provider, Company company) {
        this.id = id;
        this.line = line;
        this.plan = plan;
        this.provider = provider;
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public ProvidersSales getProvider() {
        return provider;
    }

    public void setProvider(ProvidersSales provider) {
        this.provider = provider;
    }
}
