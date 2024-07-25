package com.opencars.netgo.dms.clients.entity;

import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "clients")
@ApiModel(value = "Entidad Modelo de clientes", description = "Información completa que se almacena de un cliente")
public class Clients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String nombre;
    @NotEmpty
    @Column(unique = true)
    private String telefono;
    @NotEmpty
    @Column(unique = true)
    private String correo;
    @NotNull
    @Column(unique = true)
    private Long documento;
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

    public Clients() {
    }

    public Clients(Long id) {
        this.id = id;
    }

    public Clients(String nombre, String telefono, String correo, Long documento, User createdBy) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.documento = documento;
        this.createdBy = createdBy;
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

    public Long getDocumento() {
        return documento;
    }

    public void setDocumento(Long documento) {
        this.documento = documento;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
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
