package com.opencars.netgo.news.roundtrips.entity;

import com.opencars.netgo.auth.entity.Rol;
import com.opencars.netgo.locations.entity.Company;
import com.opencars.netgo.locations.entity.Sector;
import com.opencars.netgo.locations.entity.SubSector;
import com.opencars.netgo.news.roundtrips.dto.Attacheds;
import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name = "roundtrips")
@ApiModel(value = "Entidad Modelo de Circular", description = "Información completa que se almacena de la circular")
public class Roundtrips {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String title;
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String description;
    @NotNull
    @OneToOne
    private Sector type;
    @NotNull
    @OneToOne
    private SubSector subSector;
    @NotNull
    private LocalDateTime date;
    @NotNull
    private String banner;
    @NotNull
    private String footer;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private Attacheds[] attacheds;
    @NotNull
    private String stringDate;
    @NotNull
    private int published;

    @NotNull
    private LocalDate shortDate;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private Rol[] audience;

    @NotNull
    @OneToOne
    private Company company;

    @ManyToMany(fetch = FetchType.EAGER)
    @SortNatural
    @JoinTable(name = "roundtrips_likesusers", joinColumns = @JoinColumn(name = "roundtrip_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private SortedSet<User> likes = new TreeSet<>();

    private int views;
    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    public Roundtrips() {
    }

    public Roundtrips(Roundtrips roundtrips) {
    }

    public Roundtrips(int id, String title, LocalDateTime date) {
        this.id = id;
        this.title = title;
        this.date = date;
    }

    public Roundtrips(String title, String description, Sector type, SubSector subSector, String banner, String footer, Attacheds[] attacheds, int published, Rol[] audience, Company company, User createdBy) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.subSector = subSector;
        this.banner = banner;
        this.footer = footer;
        this.attacheds = attacheds;
        this.published = published;
        this.audience = audience;
        this.company = company;
        this.createdBy = createdBy;
    }

    public Roundtrips(int id, String title, String description, Sector type, SubSector subSector, LocalDateTime date, String banner, String footer, Attacheds[] attacheds, String stringDate, int published, LocalDate shortDate, Rol[] audience, Company company) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.subSector = subSector;
        this.date = date;
        this.banner = banner;
        this.footer = footer;
        this.attacheds = attacheds;
        this.stringDate = stringDate;
        this.published = published;
        this.shortDate = shortDate;
        this.audience = audience;
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Sector getType() {
        return type;
    }

    public void setType(Sector type) {
        this.type = type;
    }

    public SubSector getSubSector() {
        return subSector;
    }

    public void setSubSector(SubSector subSector) {
        this.subSector = subSector;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public Attacheds[] getAttacheds() {
        return attacheds;
    }

    public void setAttacheds(Attacheds[] attacheds) {
        this.attacheds = attacheds;
    }

    public String getStringDate() {
        return stringDate;
    }

    public void setStringDate(String stringDate) {
        this.stringDate = stringDate;
    }

    public int getPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    public LocalDate getShortDate() {
        return shortDate;
    }

    public void setShortDate(LocalDate shortDate) {
        this.shortDate = shortDate;
    }

    public Rol[] getAudience() {
        return audience;
    }

    public void setAudience(Rol[] audience) {
        this.audience = audience;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public SortedSet<User> getLikes() {
        return likes;
    }

    public void setLikes(SortedSet<User> likes) {
        this.likes = likes;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}


