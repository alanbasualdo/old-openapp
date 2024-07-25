package com.opencars.netgo.sales.viaticos.dto;

import com.opencars.netgo.support.tickets.dto.Data;
import com.opencars.netgo.users.entity.User;

import java.util.List;

public class CardAuthorizers {

    private User authorizer;
    private int totalForAuthorizer;
    private int viaticosNotAuthorizeds;
    private int viaticosAuthorizeds;
    private int viaticosRejecteds;
    private String timeAverageResolution;
    private Data[] arrayTotalResolvedVsTotal;
    private String[] labelsTotal;

    private List<TypeAuthorizer> typesAuthorizations;

    public CardAuthorizers() {
    }

    public CardAuthorizers(User authorizer, int totalForAuthorizer, int viaticosNotAuthorizeds, int viaticosAuthorizeds, int viaticosRejecteds, String timeAverageResolution, Data[] arrayTotalResolvedVsTotal, String[] labelsTotal, List<TypeAuthorizer> typesAuthorizations) {
        this.authorizer = authorizer;
        this.totalForAuthorizer = totalForAuthorizer;
        this.viaticosNotAuthorizeds = viaticosNotAuthorizeds;
        this.viaticosAuthorizeds = viaticosAuthorizeds;
        this.viaticosRejecteds = viaticosRejecteds;
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

    public int getViaticosNotAuthorizeds() {
        return viaticosNotAuthorizeds;
    }

    public void setViaticosNotAuthorizeds(int viaticosNotAuthorizeds) {
        this.viaticosNotAuthorizeds = viaticosNotAuthorizeds;
    }

    public int getViaticosAuthorizeds() {
        return viaticosAuthorizeds;
    }

    public void setViaticosAuthorizeds(int viaticosAuthorizeds) {
        this.viaticosAuthorizeds = viaticosAuthorizeds;
    }

    public int getViaticosRejecteds() {
        return viaticosRejecteds;
    }

    public void setViaticosRejecteds(int viaticosRejecteds) {
        this.viaticosRejecteds = viaticosRejecteds;
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
