package com.opencars.netgo.sales.viaticos.entity;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.news.roundtrips.dto.Attacheds;
import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@ApiModel(value = "Entidad Modelo de Viático", description = "Información completa que se almacena de un viático")
public class Viaticos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private double amount;

    @NotNull
    @OneToOne
    private Branch branch;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private Attacheds[] attacheds;

    @NotNull
    @ManyToOne
    private StatesCompras state;

    @NotNull
    @ManyToOne
    private ConceptsCompras concept;

    @NotNull
    @ManyToOne
    private User colaborator;

    @NotNull
    @ManyToOne
    private User authorizer;

    @ManyToOne
    private User analyst;

    @Column(columnDefinition = "TEXT")
    private String observation;

    private String ref;

    @NotNull
    private LocalDateTime dateInit;

    private LocalDateTime dateAuthorized;

    private LocalDateTime dateLoaded;

    private LocalDateTime dateEnd;

    private int rejection;

    private String reasonRejection;

    @ManyToOne
    private User rejectedBy;

    @ManyToOne
    private User treasurer;

    private String op;

    private String resolution;

    private String advance;

    private double amountAdvance;

    private String legend;

    private double amountDif;

    private String paidTo;

    public Viaticos() {
    }

    public Viaticos(double amount, Branch branch, Attacheds[] attacheds, ConceptsCompras concept, User colaborator, String observation, String advance, double amountAdvance, String paidTo) {
        this.amount = amount;
        this.branch = branch;
        this.attacheds = attacheds;
        this.concept = concept;
        this.colaborator = colaborator;
        this.observation = observation;
        this.advance = advance;
        this.amountAdvance = amountAdvance;
        this.paidTo = paidTo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Attacheds[] getAttacheds() {
        return attacheds;
    }

    public void setAttacheds(Attacheds[] attacheds) {
        this.attacheds = attacheds;
    }

    public StatesCompras getState() {
        return state;
    }

    public void setState(StatesCompras state) {
        this.state = state;
    }

    public ConceptsCompras getConcept() {
        return concept;
    }

    public void setConcept(ConceptsCompras concept) {
        this.concept = concept;
    }

    public User getColaborator() {
        return colaborator;
    }

    public void setColaborator(User colaborator) {
        this.colaborator = colaborator;
    }

    public User getAuthorizer() {
        return authorizer;
    }

    public void setAuthorizer(User authorizer) {
        this.authorizer = authorizer;
    }

    public User getAnalyst() {
        return analyst;
    }

    public void setAnalyst(User analyst) {
        this.analyst = analyst;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public LocalDateTime getDateInit() {
        return dateInit;
    }

    public void setDateInit(LocalDateTime dateInit) {
        this.dateInit = dateInit;
    }

    public LocalDateTime getDateAuthorized() {
        return dateAuthorized;
    }

    public void setDateAuthorized(LocalDateTime dateAuthorized) {
        this.dateAuthorized = dateAuthorized;
    }

    public LocalDateTime getDateLoaded() {
        return dateLoaded;
    }

    public void setDateLoaded(LocalDateTime dateLoaded) {
        this.dateLoaded = dateLoaded;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getRejection() {
        return rejection;
    }

    public void setRejection(int rejection) {
        this.rejection = rejection;
    }

    public String getReasonRejection() {
        return reasonRejection;
    }

    public void setReasonRejection(String reasonRejection) {
        this.reasonRejection = reasonRejection;
    }

    public User getRejectedBy() {
        return rejectedBy;
    }

    public void setRejectedBy(User rejectedBy) {
        this.rejectedBy = rejectedBy;
    }

    public User getTreasurer() {
        return treasurer;
    }

    public void setTreasurer(User treasurer) {
        this.treasurer = treasurer;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getAdvance() {
        return advance;
    }

    public void setAdvance(String advance) {
        this.advance = advance;
    }

    public String getLegend() {
        return legend;
    }

    public void setLegend(String legend) {
        this.legend = legend;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmountAdvance() {
        return amountAdvance;
    }

    public void setAmountAdvance(double amountAdvance) {
        this.amountAdvance = amountAdvance;
    }

    public double getAmountDif() {
        return amountDif;
    }

    public void setAmountDif(double amountDif) {
        this.amountDif = amountDif;
    }

    public String getPaidTo() {
        return paidTo;
    }

    public void setPaidTo(String paidTo) {
        this.paidTo = paidTo;
    }
}
