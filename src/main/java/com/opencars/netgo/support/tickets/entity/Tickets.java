package com.opencars.netgo.support.tickets.entity;

import com.opencars.netgo.news.roundtrips.dto.Attacheds;
import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name = "tickets")
@ApiModel(value = "Entidad Modelo de Ticket", description = "Información completa que se almacena del ticket")
public class Tickets implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @NotNull
    @ManyToOne
    private TicketsCategories category;
    @NotNull
    @ManyToOne
    private TicketsSubcategories subCategory;

    @ManyToOne
    private TicketsSubSubCategories subSubCategory;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private Attacheds[] attacheds;
    @NotNull
    @ManyToOne
    private TicketsStates state;
    private LocalDateTime openingDate;
    private LocalDateTime editDate;
    private LocalDateTime closingDate;
    @ManyToOne
    private User resolved;
    @NotNull
    @ManyToOne
    private User applicant;
    @ManyToMany(fetch = FetchType.EAGER)
    @SortNatural
    @JoinTable(name = "ticket_observers", joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private SortedSet<User> observers = new TreeSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @SortNatural
    @JoinTable(name = "ticket_comments", joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private SortedSet<TicketsComents> coments = new TreeSet<>();
    @OneToOne
    private TicketsNewEntry newEntry;
    @OneToOne
    private TicketsLowAccounts lowAccount;

    public Tickets() {
    }

    public Tickets(String title, String description, TicketsCategories category, TicketsSubcategories subCategory, TicketsSubSubCategories subSubCategory, Attacheds[] attacheds, TicketsStates state, User applicant, SortedSet<User> observers, TicketsNewEntry newEntry, TicketsLowAccounts lowAccount) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.subCategory = subCategory;
        this.subSubCategory = subSubCategory;
        this.attacheds = attacheds;
        this.state = state;
        this.applicant = applicant;
        this.observers = observers;
        this.newEntry = newEntry;
        this.lowAccount = lowAccount;
    }

    public Tickets(Long id, String title, TicketsCategories category, TicketsSubcategories subCategory, TicketsSubSubCategories subSubCategory, TicketsStates state, LocalDateTime openingDate, LocalDateTime editDate, LocalDateTime closingDate, User resolved) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.subCategory = subCategory;
        this.subSubCategory = subSubCategory;
        this.state = state;
        this.openingDate = openingDate;
        this.editDate = editDate;
        this.closingDate = closingDate;
        this.resolved = resolved;
    }

    public Tickets(Long id, String title, String description, TicketsCategories category, TicketsSubcategories subCategory, TicketsSubSubCategories subSubCategory, Attacheds[] attacheds, TicketsStates state, LocalDateTime openingDate, LocalDateTime editDate, LocalDateTime closingDate, User resolved, User applicant, SortedSet<User> observers, SortedSet<TicketsComents> coments, TicketsNewEntry newEntry, TicketsLowAccounts lowAccount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.subCategory = subCategory;
        this.subSubCategory = subSubCategory;
        this.attacheds = attacheds;
        this.state = state;
        this.openingDate = openingDate;
        this.editDate = editDate;
        this.closingDate = closingDate;
        this.resolved = resolved;
        this.applicant = applicant;
        this.observers = observers;
        this.coments = coments;
        this.newEntry = newEntry;
        this.lowAccount = lowAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Attacheds[] getAttacheds() {
        return attacheds;
    }

    public void setAttacheds(Attacheds[] attacheds) {
        this.attacheds = attacheds;
    }

    public TicketsStates getState() {
        return state;
    }

    public void setState(TicketsStates state) {
        this.state = state;
    }

    public LocalDateTime getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDateTime openingDate) {
        this.openingDate = openingDate;

    }

    public LocalDateTime getEditDate() {
        return editDate;
    }

    public void setEditDate(LocalDateTime editDate) {
        this.editDate = editDate;
    }

    public LocalDateTime getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDateTime closingDate) {
        this.closingDate = closingDate;
    }

    public User getResolved() {
        return resolved;
    }

    public void setResolved(User resolved) {
        this.resolved = resolved;
    }

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    public TicketsCategories getCategory() {
        return category;
    }

    public void setCategory(TicketsCategories category) {
        this.category = category;
    }

    public TicketsSubcategories getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(TicketsSubcategories subCategory) {
        this.subCategory = subCategory;
    }

    public SortedSet<User> getObservers() {
        return observers;
    }

    public void setObservers(SortedSet<User> observers) {
        this.observers = observers;
    }

    public SortedSet<TicketsComents> getComents() {
        return coments;
    }

    public void setComents(SortedSet<TicketsComents> coments) {
        this.coments = coments;
    }

    public TicketsNewEntry getNewEntry() {
        return newEntry;
    }

    public void setNewEntry(TicketsNewEntry newEntry) {
        this.newEntry = newEntry;
    }

    public TicketsLowAccounts getLowAccount() {
        return lowAccount;
    }

    public void setLowAccount(TicketsLowAccounts lowAccount) {
        this.lowAccount = lowAccount;
    }

    public TicketsSubSubCategories getSubSubCategory() {
        return subSubCategory;
    }

    public void setSubSubCategory(TicketsSubSubCategories subSubCategory) {
        this.subSubCategory = subSubCategory;
    }


}
