package com.opencars.netgo.users.entity;

import com.opencars.netgo.auth.entity.Rol;
import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.locations.entity.Company;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name = "users")
@ApiModel(value = "Entidad Modelo de Usuario", description = "Información completa que se almacena del usuario")
public class User implements Serializable, Comparable<User> {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private LocalDate birthDate;
    @NotNull
    @ManyToOne
    private Branch branch;
    @NotNull
    @ManyToOne
    private Company payroll;
    @NotNull
    @Column(unique = true)
    private String cuil;
    private LocalDate egressDate;
    @NotNull
    private int enable;
    @NotNull
    private LocalDate entryDate;
    @NotNull
    private String imgProfile;
    @NotNull
    @Column(unique = true)
    private String mail;
    @NotNull
    private String name;
    @NotNull
    private String password;
    @NotNull
    @Column(unique = true)
    private String username;
    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @SortNatural
    @JoinTable(name = "usuario_position", joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "position_id"))
    private SortedSet<Position> positions = new TreeSet<>();
    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @SortNatural
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private SortedSet<Rol> roles = new TreeSet<>();
    @NotNull
    private String gender;
    @NotNull
    private int passwordState;

    private String cbu;

    public User() {
    }

    public User(User user) {
    }

    public User(int id) {
        this.id = id;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(String name, SortedSet<Rol> roles) {
        this.name = name;
        this.roles = roles;
    }

    public User(String name, String mail, String username, String password) {
        this.name = name;
        this.mail = mail;
        this.username = username;
        this.password = password;
    }

    public User(int id, LocalDate birthDate, Branch branch, Company payroll, String cuil, LocalDate egressDate, int enable, LocalDate entryDate, String imgProfile, String mail, String name, SortedSet<Position> positions, SortedSet<Rol> roles) {
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
        this.positions = positions;
        this.roles = roles;
    }

    public User(LocalDate birthDate, Branch branch, Company payroll, String cuil, LocalDate egressDate, int enable, LocalDate entryDate, String imgProfile, String mail, String name, String password, String username, SortedSet<Position> positions, SortedSet<Rol> roles, String gender, int passwordState) {
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
        this.password = password;
        this.username = username;
        this.positions = positions;
        this.roles = roles;
        this.gender = gender;
        this.passwordState = passwordState;
    }

    public User(int id, Branch branch, SortedSet<Position> positions) {
        this.id = id;
        this.branch = branch;
        this.positions = positions;
    }

    public User(Branch branch, String cuil, String mail, String name, SortedSet<Position> positions, String imgProfile) {
        this.branch = branch;
        this.cuil = cuil;
        this.mail = mail;
        this.name = name;
        this.positions = positions;
        this.imgProfile = imgProfile;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public SortedSet<Rol> getRoles() {
        return roles;
    }

    public void setRoles(SortedSet<Rol> roles) {
        this.roles = roles;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getPasswordState() {
        return passwordState;
    }

    public void setPasswordState(int passwordState) {
        this.passwordState = passwordState;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public SortedSet<Position> getPositions() {
        return positions;
    }

    public void setPositions(SortedSet<Position> positions) {
        this.positions = positions;
    }

    @Override
    public int compareTo(User user) {
        return username.compareTo(user.getUsername());
    }
}
