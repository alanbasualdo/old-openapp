package com.opencars.netgo.sales.proveedores.dto;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.support.tickets.dto.Data;
import com.opencars.netgo.users.entity.User;

import java.util.List;

public class CardAnalystsProviders {

    private User analyst;
    private int totalForAnalyst;
    private int salesNotLoaded;
    private int salesLoadeds;
    private int salesRejecteds;
    private String timeAverageResolution;
    private Data[] arrayTotalResolvedVsTotal;
    private String[] labelsTotal;
    private List<Branch> branchs;

    public CardAnalystsProviders() {
    }

    public CardAnalystsProviders(User analyst, int totalForAnalyst, int salesNotLoaded, int salesLoadeds, int salesRejecteds, String timeAverageResolution, Data[] arrayTotalResolvedVsTotal, String[] labelsTotal, List<Branch> branchs) {
        this.analyst = analyst;
        this.totalForAnalyst = totalForAnalyst;
        this.salesNotLoaded = salesNotLoaded;
        this.salesLoadeds = salesLoadeds;
        this.salesRejecteds = salesRejecteds;
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

    public int getSalesNotLoaded() {
        return salesNotLoaded;
    }

    public void setSalesNotLoaded(int salesNotLoaded) {
        this.salesNotLoaded = salesNotLoaded;
    }

    public int getSalesLoadeds() {
        return salesLoadeds;
    }

    public void setSalesLoadeds(int salesLoadeds) {
        this.salesLoadeds = salesLoadeds;
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

    public List<Branch> getBranchs() {
        return branchs;
    }

    public void setBranchs(List<Branch> branchs) {
        this.branchs = branchs;
    }
}
