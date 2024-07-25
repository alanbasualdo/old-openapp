package com.opencars.netgo.sales.viaticos.dto;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.support.tickets.dto.Data;
import com.opencars.netgo.users.entity.User;

import java.util.List;

public class CardAnalysts {

    private User analyst;
    private int totalForAnalyst;
    private int viaticosNotLoaded;
    private int viaticosLoadeds;
    private int viaticosRejecteds;
    private String timeAverageResolution;
    private Data[] arrayTotalResolvedVsTotal;
    private String[] labelsTotal;
    private List<Branch> branchs;

    public CardAnalysts() {
    }

    public CardAnalysts(User analyst, int totalForAnalyst, int viaticosNotLoaded, int viaticosLoadeds, int viaticosRejecteds, String timeAverageResolution, Data[] arrayTotalResolvedVsTotal, String[] labelsTotal, List<Branch> branchs) {
        this.analyst = analyst;
        this.totalForAnalyst = totalForAnalyst;
        this.viaticosNotLoaded = viaticosNotLoaded;
        this.viaticosLoadeds = viaticosLoadeds;
        this.viaticosRejecteds = viaticosRejecteds;
        this.timeAverageResolution = timeAverageResolution;
        this.arrayTotalResolvedVsTotal = arrayTotalResolvedVsTotal;
        this.labelsTotal = labelsTotal;
        this.branchs = branchs;
    }

    public User getAnalyst() {
        return analyst;
    }

    public void setAnalyst(User analyst) {
        this.analyst = analyst;
    }

    public int getTotalForAnalyst() {
        return totalForAnalyst;
    }

    public void setTotalForAnalyst(int totalForAnalyst) {
        this.totalForAnalyst = totalForAnalyst;
    }

    public int getViaticosNotLoaded() {
        return viaticosNotLoaded;
    }

    public void setViaticosNotLoaded(int viaticosNotLoaded) {
        this.viaticosNotLoaded = viaticosNotLoaded;
    }

    public int getViaticosLoadeds() {
        return viaticosLoadeds;
    }

    public void setViaticosLoadeds(int viaticosLoadeds) {
        this.viaticosLoadeds = viaticosLoadeds;
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

    public List<Branch> getBranchs() {
        return branchs;
    }

    public void setBranchs(List<Branch> branchs) {
        this.branchs = branchs;
    }
}
