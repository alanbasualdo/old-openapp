package com.opencars.netgo.dms.expertises.entity;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "accesories")
@ApiModel(value = "Entidad Modelo de accesorios de autos", description = "Información completa que se almacena de un accesorio")
public class Accesories implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    private String accesorie;

    private Boolean checked;

    public Accesories() {
    }

    public Accesories(String accesorie) {
        this.accesorie = accesorie;
    }

    public Accesories(int id, String accesorie, Boolean checked) {
        this.id = id;
        this.accesorie = accesorie;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccesorie() {
        return accesorie;
    }

    public void setAccesorie(String accesorie) {
        this.accesorie = accesorie;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
