package com.opencars.netgo.sales.proveedores.entity;

import com.opencars.netgo.dms.providers.entity.ProvidersSales;
import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.news.roundtrips.dto.Attacheds;
import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@ApiModel(value = "Entidad Modelo de Compras-Proveedores", description = "Información completa que se almacena de una compra-proveedor")
public class SalesProviders implements Serializable {

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
    private StatesProveedores state;
    @NotNull
    @ManyToOne
    private SubcateorizedConcepts concept;
    @NotNull
    @ManyToOne
    private User colaborator;
    @NotNull
    @ManyToOne
    private User authorizerPaidMethod;
    @NotNull
    @ManyToOne
    private User authorizerSector;
    @NotNull
    @ManyToOne
    private User authorizerAccount;
    @NotNull
    @ManyToOne
    private User authorizerMaxAmount;
    @NotNull
    @ManyToOne
    private User analyst;
    @Column(columnDefinition = "TEXT")
    private String observation;
    private String ref;
    private LocalDateTime dateInit;
    private LocalDateTime dateAuthorizedPaidMethod;
    private LocalDateTime dateAuthorizedSector;
    private LocalDateTime dateAuthorizedAccount;
    private LocalDateTime dateAuthorizedMaxAmount;
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
    @NotEmpty
    private String paidType;
    @NotNull
    private LocalDate dateEmision;
    @NotNull
    private LocalDate dateExpiration;
    @NotNull
    private LocalDate dateAgreed;
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private ProvidersSales provider;
    private int rejectionPaidMethod;
    private String changeAuthorization;
    private LocalDateTime dateChangeAuthorization;

    public SalesProviders() {
    }

    public SalesProviders(double amount, Branch branch, Attacheds[] attacheds, SubcateorizedConcepts concept, User colaborator, String observation, String paidType, LocalDate dateEmision, LocalDate dateExpiration, ProvidersSales provider) {
        this.amount = amount;
        this.branch = branch;
        this.attacheds = attacheds;
        this.concept = concept;
        this.colaborator = colaborator;
        this.observation = observation;
        this.paidType = paidType;
        this.dateEmision = dateEmision;
        this.dateExpiration = dateExpiration;
        this.provider = provider;
    }

    public SalesProviders(double amount, Branch branch, Attacheds[] attacheds, SubcateorizedConcepts concept, User colaborator, String observation, String paidType, LocalDate dateEmision, LocalDate dateExpiration, ProvidersSales provider, LocalDate dateAgreed) {
        this.amount = amount;
        this.branch = branch;
        this.attacheds = attacheds;
        this.concept = concept;
        this.colaborator = colaborator;
        this.observation = observation;
        this.paidType = paidType;
        this.dateEmision = dateEmision;
        this.dateExpiration = dateExpiration;
        this.provider = provider;
        this.dateAgreed = dateAgreed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public StatesProveedores getState() {
        return state;
    }

    public void setState(StatesProveedores state) {
        this.state = state;
    }

    public SubcateorizedConcepts getConcept() {
        return concept;
    }

    public void setConcept(SubcateorizedConcepts concept) {
        this.concept = concept;
    }

    public User getColaborator() {
        return colaborator;
    }

    public void setColaborator(User colaborator) {
        this.colaborator = colaborator;
    }

    public User getAuthorizerPaidMethod() {
        return authorizerPaidMethod;
    }

    public void setAuthorizerPaidMethod(User authorizerPaidMethod) {
        this.authorizerPaidMethod = authorizerPaidMethod;
    }

    public User getAuthorizerSector() {
        return authorizerSector;
    }

    public void setAuthorizerSector(User authorizerSector) {
        this.authorizerSector = authorizerSector;
    }

    public User getAuthorizerAccount() {
        return authorizerAccount;
    }

    public void setAuthorizerAccount(User authorizerAccount) {
        this.authorizerAccount = authorizerAccount;
    }

    public User getAuthorizerMaxAmount() {
        return authorizerMaxAmount;
    }

    public void setAuthorizerMaxAmount(User authorizerMaxAmount) {
        this.authorizerMaxAmount = authorizerMaxAmount;
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

    public LocalDateTime getDateAuthorizedPaidMethod() {
        return dateAuthorizedPaidMethod;
    }

    public void setDateAuthorizedPaidMethod(LocalDateTime dateAuthorizedPaidMethod) {
        this.dateAuthorizedPaidMethod = dateAuthorizedPaidMethod;
    }

    public LocalDateTime getDateAuthorizedSector() {
        return dateAuthorizedSector;
    }

    public void setDateAuthorizedSector(LocalDateTime dateAuthorizedSector) {
        this.dateAuthorizedSector = dateAuthorizedSector;
    }

    public LocalDateTime getDateAuthorizedAccount() {
        return dateAuthorizedAccount;
    }

    public void setDateAuthorizedAccount(LocalDateTime dateAuthorizedAccount) {
        this.dateAuthorizedAccount = dateAuthorizedAccount;
    }

    public LocalDateTime getDateAuthorizedMaxAmount() {
        return dateAuthorizedMaxAmount;
    }

    public void setDateAuthorizedMaxAmount(LocalDateTime dateAuthorizedMaxAmount) {
        this.dateAuthorizedMaxAmount = dateAuthorizedMaxAmount;
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

    public String getPaidType() {
        return paidType;
    }

    public void setPaidType(String paidType) {
        this.paidType = paidType;
    }

    public LocalDate getDateEmision() {
        return dateEmision;
    }

    public void setDateEmision(LocalDate dateEmision) {
        this.dateEmision = dateEmision;
    }

    public LocalDate getDateAgreed() {
        return dateAgreed;
    }

    public void setDateAgreed(LocalDate dateAgreed) {
        this.dateAgreed = dateAgreed;
    }

    public ProvidersSales getProvider() {
        return provider;
    }

    public void setProvider(ProvidersSales provider) {
        this.provider = provider;
    }

    public int getRejectionPaidMethod() {
        return rejectionPaidMethod;
    }

    public void setRejectionPaidMethod(int rejectionPaidMethod) {
        this.rejectionPaidMethod = rejectionPaidMethod;
    }

    public String getChangeAuthorization() {
        return changeAuthorization;
    }

    public void setChangeAuthorization(String changeAuthorization) {
        this.changeAuthorization = changeAuthorization;
    }

    public LocalDateTime getDateChangeAuthorization() {
        return dateChangeAuthorization;
    }

    public void setDateChangeAuthorization(LocalDateTime dateChangeAuthorization) {
        this.dateChangeAuthorization = dateChangeAuthorization;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }
}
