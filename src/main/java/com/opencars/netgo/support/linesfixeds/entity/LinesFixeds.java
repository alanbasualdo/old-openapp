package com.opencars.netgo.support.linesfixeds.entity;

import com.opencars.netgo.dms.providers.entity.ProvidersSales;
import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.locations.entity.Company;
import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ApiModel(value = "Entidad Modelo de Líneas Fijas", description = "Información completa que se almacena de una línea fija")
public class LinesFixeds implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    private String line;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "type_id")
    private LineType type;
    @NotEmpty
    private String useLine;
    private String receiptNumber;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private ProvidersSales provider;
    @ManyToOne
    @JoinColumn(name = "invoiced_id")
    private Company invoiced;
    private String observation;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    public LinesFixeds() {
    }

    public LinesFixeds(String line, LineType type, String useLine, String receiptNumber, ProvidersSales provider, Company invoiced, String observation, Branch branch) {
        this.line = line;
        this.type = type;
        this.useLine = useLine;
        this.receiptNumber = receiptNumber;
        this.provider = provider;
        this.invoiced = invoiced;
        this.observation = observation;
        this.branch = branch;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public LineType getType() {
        return type;
    }

    public void setType(LineType type) {
        this.type = type;
    }

    public String getUseLine() {
        return useLine;
    }

    public void setUseLine(String useLine) {
        this.useLine = useLine;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public ProvidersSales getProvider() {
        return provider;
    }

    public void setProvider(ProvidersSales provider) {
        this.provider = provider;
    }

    public Company getInvoiced() {
        return invoiced;
    }

    public void setInvoiced(Company invoiced) {
        this.invoiced = invoiced;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
