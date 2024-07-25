package com.opencars.netgo.cv.entity;

import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@ApiModel(value = "Entidad Modelo de Curriculum Vitae", description = "Información completa que se almacena de un cv")
public class Cv implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne
    private User colaborator;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cv_education", joinColumns = @JoinColumn(name = "cv_id"),
            inverseJoinColumns = @JoinColumn(name = "education_id"))
    private Set<Education> education = new HashSet<>();

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cv_certifications", joinColumns = @JoinColumn(name = "cv_id"),
            inverseJoinColumns = @JoinColumn(name = "certification_id"))
    private Set<Certifications> certifications = new HashSet<>();

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cv_experience", joinColumns = @JoinColumn(name = "cv_id"),
            inverseJoinColumns = @JoinColumn(name = "experience_id"))
    private Set<Experience> experience = new HashSet<>();

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cv_hobbies", joinColumns = @JoinColumn(name = "cv_id"),
            inverseJoinColumns = @JoinColumn(name = "hobbies_id"))
    private Set<Hobbies> hobbies = new HashSet<>();

    public Cv() {
    }

    public Cv(int id, User colaborator) {
        this.id = id;
        this.colaborator = colaborator;
    }

    public Cv(User colaborator, Set<Education> education, Set<Certifications> certifications, Set<Experience> experience, Set<Hobbies> hobbies) {
        this.colaborator = colaborator;
        this.education = education;
        this.certifications = certifications;
        this.experience = experience;
        this.hobbies = hobbies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getColaborator() {
        return colaborator;
    }

    public void setColaborator(User colaborator) {
        this.colaborator = colaborator;
    }

    public Set<Education> getEducation() {
        return education;
    }

    public void setEducation(Set<Education> education) {
        this.education = education;
    }

    public Set<Certifications> getCertifications() {
        return certifications;
    }

    public void setCertifications(Set<Certifications> certifications) {
        this.certifications = certifications;
    }

    public Set<Experience> getExperience() {
        return experience;
    }

    public void setExperience(Set<Experience> experience) {
        this.experience = experience;
    }

    public Set<Hobbies> getHobbies() {
        return hobbies;
    }

    public void setHobbies(Set<Hobbies> hobbies) {
        this.hobbies = hobbies;
    }
}
