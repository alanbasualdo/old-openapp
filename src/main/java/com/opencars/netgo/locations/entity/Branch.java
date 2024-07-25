package com.opencars.netgo.locations.entity;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "branches")
@ApiModel(value = "Entidad Modelo de Sucursal", description = "Información completa que se almacena de la sucursal")
public class Branch implements Serializable, Comparable<Branch> {

    /* Attributes */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String city;
    private String province;
    private String street;
    private String phone;
    @ManyToOne
    private BrandsCompany brandsCompany;

    public Branch() {
    }

    public Branch(int id) {
        this.id = id;
    }

    public Branch(String city, String province, String street, String phone, BrandsCompany brandsCompany) {
        this.city = city;
        this.province = province;
        this.street = street;
        this.phone = phone;
        this.brandsCompany = brandsCompany;
    }

    public Branch(int id, String city, String province, String street, String phone, BrandsCompany brandsCompany) {
        this.id = id;
        this.city = city;
        this.province = province;
        this.street = street;
        this.phone = phone;
        this.brandsCompany = brandsCompany;
    }

    /* Getter and setters */

    public int getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BrandsCompany getBrandsCompany() {
        return brandsCompany;
    }

    public void setBrandsCompany(BrandsCompany brandsCompany) {
        this.brandsCompany = brandsCompany;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public int compareTo(Branch branch) {
        return street.compareTo(branch.getStreet());
    }
}
