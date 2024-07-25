package com.opencars.netgo.users.entity;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.locations.entity.SubSector;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.io.Serializable;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name = "positions")
@ApiModel(value = "Entidad Modelo de Posición dentro de la empresa", description = "Información completa que se almacena del puesto de un usuario")
public class Position implements Serializable, Comparable<Position> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "charge_id")
    private Charge position;
    @ManyToOne
    @JoinColumn(name = "sub_sector_id")
    private SubSector subSector;
    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @SortNatural
    @JoinTable(name = "position_branch", joinColumns = @JoinColumn(name = "position_id"),
            inverseJoinColumns = @JoinColumn(name = "branch_id"))
    private SortedSet<Branch> branchs = new TreeSet<>();
    @NotNull
    private int linea;

    public Position() {
    }

    public Position(int id) {
        this.id = id;
    }

    public Position(Charge position, SubSector subSector, int linea, SortedSet<Branch> branchs) {
        this.position = position;
        this.subSector = subSector;
        this.linea = linea;
        this.branchs = branchs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Charge getPosition() {
        return position;
    }

    public void setPosition(Charge position) {
        this.position = position;
    }

    public SubSector getSubSector() {
        return subSector;
    }

    public void setSubSector(SubSector subSector) {
        this.subSector = subSector;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public SortedSet<Branch> getBranchs() {
        return branchs;
    }

    public void setBranchs(SortedSet<Branch> branchs) {
        this.branchs = branchs;
    }

    @Override
    public int compareTo(Position positions) {
        return String.valueOf(id).compareTo(String.valueOf(positions.getId()));
    }

}
