package com.opencars.netgo.auth.entity;

import com.opencars.netgo.locations.entity.Branch;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@ApiModel(value = "Entidad Registro de Uso por Sucursal", description = "Registro para indicadores de uso de la plataforma por sucursal")
public class LogDailyUse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    private int actives;

    private int total;

    @NotNull
    @ManyToOne
    private Branch branch;

    @NotNull
    private LocalDate day;

    public LogDailyUse() {
    }

    public LogDailyUse(int actives, int total, Branch branch, LocalDate day) {
        this.actives = actives;
        this.total = total;
        this.branch = branch;
        this.day = day;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getActives() {
        return actives;
    }

    public void setActives(int actives) {
        this.actives = actives;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }
}
