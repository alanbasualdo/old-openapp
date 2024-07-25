package com.opencars.netgo.benefitcars.entity;

import com.opencars.netgo.users.entity.User;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@ApiModel(value = "Entidad Modelo de Solicitud de Beneficio en Compra de Vehículos", description = "Información completa de una solicitud de beneficio en compra de vehículos para colaboradores")
public class BenefitCars implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @OneToOne
    private User colaborator;
    @NotNull
    private LocalDateTime dateCreated;
    @NotNull
    private String domain;
    @NotNull
    private String brand;
    @NotNull
    private String model;
    @NotNull
    private String financing;
    private String authorizeBranchManager;
    private LocalDateTime dateAuthorizeBranchManager;
    private boolean checkAuthorizeBranchManager;
    private String authorizeAreaManager;
    private LocalDateTime dateAuthorizeAreaManager;
    private boolean checkAuthorizeAreaManager;
    private String authorizeVOManager;
    private LocalDateTime dateAuthorizeVOManager;
    private boolean checkAuthorizeVOManager;
    private long amountToAuthorize;
    private long transfToAuthorize;
    @Column(columnDefinition = "TEXT")
    private String obsVOManager;
    private String authorizeGeneralManager;
    private LocalDateTime dateAuthorizeGeneralManager;
    private boolean checkAuthorizeGeneralManager;
    @Column(columnDefinition = "TEXT")
    private String obsGeneralManager;
    private long amountModifiedByGeneralManager;
    private long transfModifiedByGeneralManager;
    private String authorizeCCHH;
    private LocalDateTime dateAuthorizeCCHH;
    private boolean checkAuthorizeCCHHManager;
    @Column(columnDefinition = "TEXT")
    private String obsCCHH;
    private String authorizePresident;
    private LocalDateTime dateAuthorizePresident;
    private boolean checkAuthorizePresidentManager;
    @Column(columnDefinition = "TEXT")
    private String obsPresident;
    private long amountModifiedByPresident;
    private long transfModifiedByPresident;
    @Column(columnDefinition = "TEXT")
    private String finalFinancing;
    private String rrll;
    private LocalDateTime dateValidateRRLL;
    private LocalDate dateGranting;
    @NotNull
    @OneToOne
    private StateInternal stateInternal;
    @NotNull
    @OneToOne
    private StateToColaborator stateToColaborator;
    private long amountFinal;
    private long transfFinal;
    private String notificateToColaborator;
    private String endProposal;
    private String agreementColaborator;
    private boolean checkAuthorizeRRLL;

    public BenefitCars() {
    }

    public BenefitCars(BenefitCars benefitCars) {
    }

    public BenefitCars(User colaborator, LocalDateTime dateCreated, String domain, String brand, String model, String financing, String authorizeBranchManager, LocalDateTime dateAuthorizeBranchManager, boolean checkAuthorizeBranchManager, String authorizeAreaManager, LocalDateTime dateAuthorizeAreaManager, boolean checkAuthorizeAreaManager, String authorizeVOManager, LocalDateTime dateAuthorizeVOManager, boolean checkAuthorizeVOManager, long amountToAuthorize, long transfToAuthorize, String obsVOManager, String authorizeGeneralManager, LocalDateTime dateAuthorizeGeneralManager, boolean checkAuthorizeGeneralManager, String obsGeneralManager, long amountModifiedByGeneralManager, long transModifiedByGeneralManager, String authorizeCCHH, LocalDateTime dateAuthorizeCCHH, boolean checkAuthorizeCCHHManager, String obsCCHH, String authorizePresident, LocalDateTime dateAuthorizePresident, boolean checkAuthorizePresidentManager, String obsPresident, long amountModifiedByPresident, long transfModifiedByPresident, String finalFinancing, String rrll, LocalDateTime dateValidateRRLL, LocalDate dateGranting, StateInternal stateInternal, StateToColaborator stateToColaborator, long amountFinal, long transfFinal, String notificateToColaborator, String endProposal, String agreementColaborator, boolean checkAuthorizeRRLL) {
        this.colaborator = colaborator;
        this.dateCreated = dateCreated;
        this.domain = domain;
        this.brand = brand;
        this.model = model;
        this.financing = financing;
        this.authorizeBranchManager = authorizeBranchManager;
        this.dateAuthorizeBranchManager = dateAuthorizeBranchManager;
        this.checkAuthorizeBranchManager = checkAuthorizeBranchManager;
        this.authorizeAreaManager = authorizeAreaManager;
        this.dateAuthorizeAreaManager = dateAuthorizeAreaManager;
        this.checkAuthorizeAreaManager = checkAuthorizeAreaManager;
        this.authorizeVOManager = authorizeVOManager;
        this.dateAuthorizeVOManager = dateAuthorizeVOManager;
        this.checkAuthorizeVOManager = checkAuthorizeVOManager;
        this.amountToAuthorize = amountToAuthorize;
        this.transfToAuthorize = transfToAuthorize;
        this.obsVOManager = obsVOManager;
        this.authorizeGeneralManager = authorizeGeneralManager;
        this.dateAuthorizeGeneralManager = dateAuthorizeGeneralManager;
        this.checkAuthorizeGeneralManager = checkAuthorizeGeneralManager;
        this.obsGeneralManager = obsGeneralManager;
        this.amountModifiedByGeneralManager = amountModifiedByGeneralManager;
        this.transfModifiedByGeneralManager = transModifiedByGeneralManager;
        this.authorizeCCHH = authorizeCCHH;
        this.dateAuthorizeCCHH = dateAuthorizeCCHH;
        this.checkAuthorizeCCHHManager = checkAuthorizeCCHHManager;
        this.obsCCHH = obsCCHH;
        this.authorizePresident = authorizePresident;
        this.dateAuthorizePresident = dateAuthorizePresident;
        this.checkAuthorizePresidentManager = checkAuthorizePresidentManager;
        this.obsPresident = obsPresident;
        this.amountModifiedByPresident = amountModifiedByPresident;
        this.transfModifiedByPresident = transfModifiedByPresident;
        this.finalFinancing = finalFinancing;
        this.rrll = rrll;
        this.dateValidateRRLL = dateValidateRRLL;
        this.dateGranting = dateGranting;
        this.stateInternal = stateInternal;
        this.stateToColaborator = stateToColaborator;
        this.amountFinal = amountFinal;
        this.transfFinal = transfFinal;
        this.notificateToColaborator = notificateToColaborator;
        this.endProposal = endProposal;
        this.agreementColaborator = agreementColaborator;
        this.checkAuthorizeRRLL = checkAuthorizeRRLL;
    }

    public BenefitCars(User colaborator, String domain, String brand, String model, String financing) {
        this.colaborator = colaborator;
        this.domain = domain;
        this.brand = brand;
        this.model = model;
        this.financing = financing;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getColaborator() {
        return colaborator;
    }

    public void setColaborator(User colaborator) {
        this.colaborator = colaborator;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFinancing() {
        return financing;
    }

    public void setFinancing(String financing) {
        this.financing = financing;
    }

    public String getAuthorizeBranchManager() {
        return authorizeBranchManager;
    }

    public void setAuthorizeBranchManager(String authorizeBranchManager) {
        this.authorizeBranchManager = authorizeBranchManager;
    }

    public String getAuthorizeAreaManager() {
        return authorizeAreaManager;
    }

    public void setAuthorizeAreaManager(String authorizeAreaManager) {
        this.authorizeAreaManager = authorizeAreaManager;
    }

    public String getAuthorizeVOManager() {
        return authorizeVOManager;
    }

    public void setAuthorizeVOManager(String authorizeVOManager) {
        this.authorizeVOManager = authorizeVOManager;
    }

    public long getAmountToAuthorize() {
        return amountToAuthorize;
    }

    public void setAmountToAuthorize(long amountToAuthorize) {
        this.amountToAuthorize = amountToAuthorize;
    }

    public String getObsVOManager() {
        return obsVOManager;
    }

    public void setObsVOManager(String obsVOManager) {
        this.obsVOManager = obsVOManager;
    }

    public String getAuthorizeGeneralManager() {
        return authorizeGeneralManager;
    }

    public void setAuthorizeGeneralManager(String authorizeGeneralManager) {
        this.authorizeGeneralManager = authorizeGeneralManager;
    }

    public String getObsGeneralManager() {
        return obsGeneralManager;
    }

    public void setObsGeneralManager(String obsGeneralManager) {
        this.obsGeneralManager = obsGeneralManager;
    }

    public long getAmountModifiedByGeneralManager() {
        return amountModifiedByGeneralManager;
    }

    public void setAmountModifiedByGeneralManager(long amountModifiedByGeneralManager) {
        this.amountModifiedByGeneralManager = amountModifiedByGeneralManager;
    }

    public String getAuthorizeCCHH() {
        return authorizeCCHH;
    }

    public void setAuthorizeCCHH(String authorizeCCHH) {
        this.authorizeCCHH = authorizeCCHH;
    }

    public String getObsCCHH() {
        return obsCCHH;
    }

    public void setObsCCHH(String obsCCHH) {
        this.obsCCHH = obsCCHH;
    }

    public String getAuthorizePresident() {
        return authorizePresident;
    }

    public void setAuthorizePresident(String authorizePresident) {
        this.authorizePresident = authorizePresident;
    }

    public String getObsPresident() {
        return obsPresident;
    }

    public void setObsPresident(String obsPresident) {
        this.obsPresident = obsPresident;
    }

    public long getAmountModifiedByPresident() {
        return amountModifiedByPresident;
    }

    public void setAmountModifiedByPresident(long amountModifiedByPresident) {
        this.amountModifiedByPresident = amountModifiedByPresident;
    }

    public String getFinalFinancing() {
        return finalFinancing;
    }

    public void setFinalFinancing(String finalFinancing) {
        this.finalFinancing = finalFinancing;
    }

    public LocalDate getDateGranting() {
        return dateGranting;
    }

    public void setDateGranting(LocalDate dateGranting) {
        this.dateGranting = dateGranting;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateAuthorizeBranchManager() {
        return dateAuthorizeBranchManager;
    }

    public void setDateAuthorizeBranchManager(LocalDateTime dateAuthorizeBranchManager) {
        this.dateAuthorizeBranchManager = dateAuthorizeBranchManager;
    }

    public LocalDateTime getDateAuthorizeAreaManager() {
        return dateAuthorizeAreaManager;
    }

    public void setDateAuthorizeAreaManager(LocalDateTime dateAuthorizeAreaManager) {
        this.dateAuthorizeAreaManager = dateAuthorizeAreaManager;
    }

    public LocalDateTime getDateAuthorizeVOManager() {
        return dateAuthorizeVOManager;
    }

    public void setDateAuthorizeVOManager(LocalDateTime dateAuthorizeVOManager) {
        this.dateAuthorizeVOManager = dateAuthorizeVOManager;
    }

    public LocalDateTime getDateAuthorizeGeneralManager() {
        return dateAuthorizeGeneralManager;
    }

    public void setDateAuthorizeGeneralManager(LocalDateTime dateAuthorizeGeneralManager) {
        this.dateAuthorizeGeneralManager = dateAuthorizeGeneralManager;
    }

    public LocalDateTime getDateAuthorizeCCHH() {
        return dateAuthorizeCCHH;
    }

    public void setDateAuthorizeCCHH(LocalDateTime dateAuthorizeCCHH) {
        this.dateAuthorizeCCHH = dateAuthorizeCCHH;
    }

    public LocalDateTime getDateAuthorizePresident() {
        return dateAuthorizePresident;
    }

    public void setDateAuthorizePresident(LocalDateTime dateAuthorizePresident) {
        this.dateAuthorizePresident = dateAuthorizePresident;
    }

    public StateInternal getStateInternal() {
        return stateInternal;
    }

    public void setStateInternal(StateInternal stateInternal) {
        this.stateInternal = stateInternal;
    }

    public StateToColaborator getStateToColaborator() {
        return stateToColaborator;
    }

    public void setStateToColaborator(StateToColaborator stateToColaborator) {
        this.stateToColaborator = stateToColaborator;
    }

    public boolean isCheckAuthorizeBranchManager() {
        return checkAuthorizeBranchManager;
    }

    public void setCheckAuthorizeBranchManager(boolean checkAuthorizeBranchManager) {
        this.checkAuthorizeBranchManager = checkAuthorizeBranchManager;
    }

    public boolean isCheckAuthorizeAreaManager() {
        return checkAuthorizeAreaManager;
    }

    public void setCheckAuthorizeAreaManager(boolean checkAuthorizeAreaManager) {
        this.checkAuthorizeAreaManager = checkAuthorizeAreaManager;
    }

    public boolean isCheckAuthorizeVOManager() {
        return checkAuthorizeVOManager;
    }

    public void setCheckAuthorizeVOManager(boolean checkAuthorizeVOManager) {
        this.checkAuthorizeVOManager = checkAuthorizeVOManager;
    }

    public boolean isCheckAuthorizeGeneralManager() {
        return checkAuthorizeGeneralManager;
    }

    public void setCheckAuthorizeGeneralManager(boolean checkAuthorizeGeneralManager) {
        this.checkAuthorizeGeneralManager = checkAuthorizeGeneralManager;
    }

    public boolean isCheckAuthorizeCCHHManager() {
        return checkAuthorizeCCHHManager;
    }

    public void setCheckAuthorizeCCHHManager(boolean checkAuthorizeCCHHManager) {
        this.checkAuthorizeCCHHManager = checkAuthorizeCCHHManager;
    }

    public boolean isCheckAuthorizePresidentManager() {
        return checkAuthorizePresidentManager;
    }

    public void setCheckAuthorizePresidentManager(boolean checkAuthorizePresidentManager) {
        this.checkAuthorizePresidentManager = checkAuthorizePresidentManager;
    }

    public String getRrll() {
        return rrll;
    }

    public void setRrll(String rrll) {
        this.rrll = rrll;
    }

    public LocalDateTime getDateValidateRRLL() {
        return dateValidateRRLL;
    }

    public void setDateValidateRRLL(LocalDateTime dateValidateRRLL) {
        this.dateValidateRRLL = dateValidateRRLL;
    }

    public long getAmountFinal() {
        return amountFinal;
    }

    public void setAmountFinal(long amountFinal) {
        this.amountFinal = amountFinal;
    }

    public String getNotificateToColaborator() {
        return notificateToColaborator;
    }

    public void setNotificateToColaborator(String notificateToColaborator) {
        this.notificateToColaborator = notificateToColaborator;
    }

    public String getEndProposal() {
        return endProposal;
    }

    public void setEndProposal(String endProposal) {
        this.endProposal = endProposal;
    }

    public long getTransfToAuthorize() {
        return transfToAuthorize;
    }

    public void setTransfToAuthorize(long transfToAuthorize) {
        this.transfToAuthorize = transfToAuthorize;
    }

    public long getTransfModifiedByGeneralManager() {
        return transfModifiedByGeneralManager;
    }

    public void setTransfModifiedByGeneralManager(long transfModifiedByGeneralManager) {
        this.transfModifiedByGeneralManager = transfModifiedByGeneralManager;
    }

    public long getTransfModifiedByPresident() {
        return transfModifiedByPresident;
    }

    public void setTransfModifiedByPresident(long transfModifiedByPresident) {
        this.transfModifiedByPresident = transfModifiedByPresident;
    }

    public long getTransfFinal() {
        return transfFinal;
    }

    public void setTransfFinal(long transfFinal) {
        this.transfFinal = transfFinal;
    }

    public String getAgreementColaborator() {
        return agreementColaborator;
    }

    public void setAgreementColaborator(String agreementColaborator) {
        this.agreementColaborator = agreementColaborator;
    }

    public boolean isCheckAuthorizeRRLL() {
        return checkAuthorizeRRLL;
    }

    public void setCheckAuthorizeRRLL(boolean checkAuthorizeRRLL) {
        this.checkAuthorizeRRLL = checkAuthorizeRRLL;
    }
}
