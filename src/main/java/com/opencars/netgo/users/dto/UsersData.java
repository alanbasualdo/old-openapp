package com.opencars.netgo.users.dto;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.locations.entity.Company;
import com.opencars.netgo.locations.entity.Sector;

import java.io.Serializable;

public class UsersData implements Serializable {

    private int id;
    private Branch branch;
    private Company payroll;
    private String cuil;
    private int enable;
    private String imgProfile;
    private String mail;
    private String name;
    private Sector sector;
    private String position;
    private String gender;

    public UsersData() {
    }

    public UsersData(int id, Branch branch, Company payroll, String cuil, int enable, String imgProfile, String mail, String name, Sector sector, String position, String gender) {
        this.id = id;
        this.branch = branch;
        this.payroll = payroll;
        this.cuil = cuil;
        this.enable = enable;
        this.imgProfile = imgProfile;
        this.mail = mail;
        this.name = name;
        this.sector = sector;
        this.position = position;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
