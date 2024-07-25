package com.opencars.netgo.dms.providers.entity;

import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "providers")
@ApiModel(value = "Entidad Modelo de proveedores de compras", description = "Información completa que se almacena de un proveedor")
public class ProvidersSales implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String nombre;
    private String telefono;
    private String correo;
    private String form;
    @NotNull
    @Column(unique = true)
    private Long cuit;
    @ManyToOne
    @JoinColumn(name = "fiscal_type_id")
    private FiscalType fiscalType;
    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;
    @ManyToOne
    @JoinColumn(name = "edit_by_id")
    private User editBy;
    private LocalDate fechaAlta;
    @PrePersist
    private void persistAlta(){
        this.fechaAlta = LocalDate.now();
    }

    public ProvidersSales() {
    }
    public ProvidersSales(Long id) {
        this.id = id;
    }

    public ProvidersSales(String nombre, String telefono, String correo, String form, Long cuit, FiscalType fiscalType, User createdBy) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.form = form;
        this.cuit = cuit;
        this.fiscalType = fiscalType;
        this.createdBy = createdBy;
    }

    public ProvidersSales(String nombre, String telefono, String correo, String form, Long cuit, FiscalType fiscalType, User createdBy, User editBy) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.form = form;
        this.cuit = cuit;
        this.fiscalType = fiscalType;
        this.createdBy = createdBy;
        this.editBy = editBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public Long getCuit() {
        return cuit;
    }

    public void setCuit(Long cuit) {
        this.cuit = cuit;
    }

    public FiscalType getFiscalType() {
        return fiscalType;
    }

    public void setFiscalType(FiscalType fiscalType) {
        this.fiscalType = fiscalType;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getEditBy() {
        return editBy;
    }

    public void setEditBy(User editBy) {
        this.editBy = editBy;
    }

}
