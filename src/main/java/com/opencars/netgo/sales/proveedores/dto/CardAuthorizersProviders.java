package com.opencars.netgo.sales.proveedores.dto;

import com.opencars.netgo.sales.viaticos.dto.TypeAuthorizer;
import com.opencars.netgo.support.tickets.dto.Data;
import com.opencars.netgo.users.entity.User;

import java.util.List;

public class CardAuthorizersProviders {

    private User authorizer;
    private int totalForAuthorizer;
    private int salesNotAuthorizeds;
    private int salesAuthorizeds;
    private int salesRejecteds;
    private String timeAverageResolution;
    private Data[] arrayTotalResolvedVsTotal;
    private String[] labelsTotal;
    private List<TypeAuthorizer> typesAuthorizations;

    public CardAuthorizersProviders() {
    }

    public CardAuthorizersProviders(User authorizer, int totalForAuthorizer, int salesNotAuthorizeds, int salesAuthorizeds, int salesRejecteds, String timeAverageResolution, Data[] arrayTotalResolvedVsTotal, String[] labelsTotal, List<TypeAuthorizer> typesAuthorizations) {
        this.authorizer = authorizer;
        this.totalForAuthorizer = totalForAuthorizer;
        this.salesNotAuthorizeds = salesNotAuthorizeds;
        this.salesAuthorizeds = salesAuthorizeds;
        this.salesRejecteds = salesRejecteds;
        this.timeAverageResolution = timeAverageResolution;
        this.arrayTotalResolvedVsTotal = arrayTotalResolvedVsTotal;
        this.labelsTotal = labelsTotal;
        this.typesAuthorizations = typesAuthorizations;
    }

    public User getAuthorizer() {
        return authorizer;
    }

    public void setAuthorizer(User authorizer) {
        this.authorizer = authorizer;
    }

    public int getTotalForAuthorizer() {
        return totalForAuthorizer;
    }

    public void setTotalForAuthorizer(int totalForAuthorizer) {
        this.totalForAuthorizer = totalForAuthorizer;
    }

    public int getSalesNotAuthorizeds() {
        return salesNotAuthorizeds;
    }

    public void setSalesNotAuthorizeds(int salesNotAuthorizeds) {
        this.salesNotAuthorizeds = salesNotAuthorizeds;
    }

    public int getSalesAuthorizeds() {
        return salesAuthorizeds;
    }

    public void setSalesAuthorizeds(int salesAuthorizeds) {
        this.salesAuthorizeds = salesAuthorizeds;
    }

    public int getSalesRejecteds() {
        return salesRejecteds;
    }

    public void setSalesRejecteds(int salesRejecteds) {
        this.salesRejecteds = salesRejecteds;
    }

    public String getTimeAverageResolution() {
        return timeAverageResolution;
    }

    public void setTimeAverageResolution(String timeAverageResolution) {
        this.timeAverageResolution = timeAverageResolution;
    }

    public Data[] getArrayTotalResolvedVsTotal() {
        return arrayTotalResolvedVsTotal;
    }

    public void setArrayTotalResolvedVsTotal(Data[] arrayTotalResolvedVsTotal) {
        this.arrayTotalResolvedVsTotal = arrayTotalResolvedVsTotal;
    }

    public String[] getLabelsTotal() {
        return labelsTotal;
    }

    public void setLabelsTotal(String[] labelsTotal) {
        this.labelsTotal = labelsTotal;
    }

    public List<TypeAuthorizer> getTypesAuthorizations() {
        return typesAuthorizations;
    }

    public void setTypesAuthorizations(List<TypeAuthorizer> typesAuthorizations) {
        this.typesAuthorizations = typesAuthorizations;
    }
}
