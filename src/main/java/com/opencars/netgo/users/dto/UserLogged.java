package com.opencars.netgo.users.dto;

import com.opencars.netgo.auth.entity.Rol;
import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.locations.entity.Company;
import com.opencars.netgo.locations.entity.Sector;

import java.time.LocalDate;
import java.util.SortedSet;
import java.util.TreeSet;

public class UserLogged {

    private int id;
    private LocalDate birthDate;
    private Branch branch;
    private Company payroll;
    private String cuil;
    private LocalDate egressDate;
    private int enable;
    private LocalDate entryDate;
    private String imgProfile;
    private String mail;
    private String name;
    private Sector sector;
    private String position;
    private SortedSet<Rol> roles = new TreeSet<>();

    public UserLogged() {
    }

    public UserLogged(int id, LocalDate birthDate, Branch branch, Company payroll, String cuil, LocalDate egressDate, int enable, LocalDate entryDate, String imgProfile, String mail, String name, Sector sector, String position, SortedSet<Rol> roles) {
        this.id = id;
        this.birthDate = birthDate;
        this.branch = branch;
        this.payroll = payroll;
        this.cuil = cuil;
        this.egressDate = egressDate;
        this.enable = enable;
        this.entryDate = entryDate;
        this.imgProfile = imgProfile;
        this.mail = mail;
        this.name = name;
        this.sector = sector;
        this.position = position;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Company getPayroll() {
        return payroll;
    }

    public void setPayroll(Company payroll) {
        this.payroll = payroll;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public LocalDate getEgressDate() {
        return egressDate;
    }

    public void setEgressDate(LocalDate egressDate) {
        this.egressDate = egressDate;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public String getImgProfile() {
        return imgProfile;
    }

    public void setImgProfile(String imgProfile) {
        this.imgProfile = imgProfile;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public SortedSet<Rol> getRoles() {
        return roles;
    }

    public void setRoles(SortedSet<Rol> roles) {
        this.roles = roles;
    }
}
