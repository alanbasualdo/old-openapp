package com.opencars.netgo.sales.proveedores.dto;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.support.tickets.dto.Data;
import com.opencars.netgo.support.tickets.dto.DataLong;
import com.opencars.netgo.users.entity.User;

import java.util.List;

public class CardTreasurersProviders {

    private User treasurer;
    private int totalForTreasurer;
    private int salesNotPaidsNotDefeated;
    private int salesNotPaidsDefeated;
    private int salesPaids;
    private String timeAverageResolution;
    private Data[] arrayTotalResolvedVsTotal;
    private String[] labelsTotal;
    private DataLong[] arrayTotalAmount;
    private String[] arrayLabelsAmount;
    private String amountNotPaidNotDefeated;
    private String amountNotPaidDefeated;
    private String amountPaid;
    private String totalAmountForTreasurer;

    private List<Branch> branchs;


    public CardTreasurersProviders() {
    }

    public CardTreasurersProviders(User treasurer, int totalForTreasurer, int salesNotPaidsNotDefeated, int salesNotPaidsDefeated, int salesPaids, String timeAverageResolution, Data[] arrayTotalResolvedVsTotal, String[] labelsTotal, DataLong[] arrayTotalAmount, String[] arrayLabelsAmount, String amountNotPaidNotDefeatedStr, String amountNotPaidDefeatedStr, String amountPaid, String totalAmountForTreasurer, List<Branch> branchs) {
        this.treasurer = treasurer;
        this.totalForTreasurer = totalForTreasurer;
        this.salesNotPaidsNotDefeated = salesNotPaidsNotDefeated;
        this.salesNotPaidsDefeated = salesNotPaidsDefeated;
        this.salesPaids = salesPaids;
        this.timeAverageResolution = timeAverageResolution;
        this.arrayTotalResolvedVsTotal = arrayTotalResolvedVsTotal;
        this.labelsTotal = labelsTotal;
        this.arrayTotalAmount = arrayTotalAmount;
        this.arrayLabelsAmount = arrayLabelsAmount;
        this.amountNotPaidNotDefeated = amountNotPaidNotDefeatedStr;
        this.amountNotPaidDefeated = amountNotPaidDefeatedStr;
        this.amountPaid = amountPaid;
        this.totalAmountForTreasurer = totalAmountForTreasurer;
        this.branchs = branchs;
    }

    public User getTreasurer() {
        return treasurer;
    }

    public void setTreasurer(User treasurer) {
        this.treasurer = treasurer;
    }

    public int getTotalForTreasurer() {
        return totalForTreasurer;
    }

    public void setTotalForTreasurer(int totalForTreasurer) {
        this.totalForTreasurer = totalForTreasurer;
    }


    public int getSalesNotPaidsNotDefeated() {
        return salesNotPaidsNotDefeated;
    }

    public void setSalesNotPaidsNotDefeated(int salesNotPaidsNotDefeated) {
        this.salesNotPaidsNotDefeated = salesNotPaidsNotDefeated;
    }

    public int getSalesNotPaidsDefeated() {
        return salesNotPaidsDefeated;
    }

    public void setSalesNotPaidsDefeated(int salesNotPaidsDefeated) {
        this.salesNotPaidsDefeated = salesNotPaidsDefeated;
    }

    public int getSalesPaids() {
        return salesPaids;
    }

    public void setSalesPaids(int salesPaids) {
        this.salesPaids = salesPaids;
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

    public DataLong[] getArrayTotalAmount() {
        return arrayTotalAmount;
    }

    public void setArrayTotalAmount(DataLong[] arrayTotalAmount) {
        this.arrayTotalAmount = arrayTotalAmount;
    }

    public String[] getArrayLabelsAmount() {
        return arrayLabelsAmount;
    }

    public void setArrayLabelsAmount(String[] arrayLabelsAmount) {
        this.arrayLabelsAmount = arrayLabelsAmount;
    }

    public String getAmountNotPaidNotDefeated() {
        return amountNotPaidNotDefeated;
    }

    public void setAmountNotPaidNotDefeated(String amountNotPaidNotDefeated) {
        this.amountNotPaidNotDefeated = amountNotPaidNotDefeated;
    }

    public String getAmountNotPaidDefeated() {
        return amountNotPaidDefeated;
    }

    public void setAmountNotPaidDefeated(String amountNotPaidDefeated) {
        this.amountNotPaidDefeated = amountNotPaidDefeated;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getTotalAmountForTreasurer() {
        return totalAmountForTreasurer;
    }

    public void setTotalAmountForTreasurer(String totalAmountForTreasurer) {
        this.totalAmountForTreasurer = totalAmountForTreasurer;
    }

    public List<Branch> getBranchs() {
        return branchs;
    }

    public void setBranchs(List<Branch> branchs) {
        this.branchs = branchs;
    }
}
