package com.opencars.netgo.users.entity;

import com.opencars.netgo.locations.entity.Branch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Employee implements Serializable {
    private String name;
    private String cssClass;
    private String image;
    private String title;
    private String mail;
    private Branch branch;
    private List<Employee> childs;
    private String mobile;

    public Employee() {
    }

    public Employee(String name, String cssClass, String image, String title, String mail, Branch branch, String mobile) {
        this.name = name;
        this.cssClass = cssClass;
        this.image = image;
        this.title = title;
        this.mail = mail;
        this.branch = branch;
        this.childs = new ArrayList<>();
        this.mobile = mobile;
    }

    public void addChilds(Employee e) {
        childs.add(e);
    }

    public List<Employee> getChilds() {
        return childs;
    }

    public void setChilds(List<Employee> childs) {
        this.childs = childs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
