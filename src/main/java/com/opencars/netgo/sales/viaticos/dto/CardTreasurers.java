package com.opencars.netgo.sales.viaticos.dto;

import com.opencars.netgo.locations.entity.Branch;
import com.opencars.netgo.support.tickets.dto.Data;
import com.opencars.netgo.support.tickets.dto.DataLong;
import com.opencars.netgo.users.entity.User;

import java.util.List;

public class CardTreasurers {

    private User treasurer;
    private int totalForTreasurer;
    private int viaticosNotPaids;
    private int viaticosPaids;
    private String timeAverageResolution;
    private Data[] arrayTotalResolvedVsTotal;
    private String[] labelsTotal;
    private DataLong[] arrayTotalAmount;
    private String[] arrayLabelsAmount;

    private String amountNotPaid;

    private String amountPaid;

    private String totalAmountForTreasurer;

    private List<Branch> branchs;

    public CardTreasurers() {
    }

    public CardTreasurers(User treasurer, int totalForTreasurer, int viaticosNotPaids, int viaticosPaids, String timeAverageResolution, Data[] arrayTotalResolvedVsTotal, String[] labelsTotal, DataLong[] arrayTotalAmount, String[] arrayLabelsAmount, String amountNotPaid, String amountPaid, String totalAmountForTreasurer, List<Branch> branchs) {
        this.treasurer = treasurer;
        this.totalForTreasurer = totalForTreasurer;
        this.viaticosNotPaids = viaticosNotPaids;
        this.viaticosPaids = viaticosPaids;
        this.timeAverageResolution = timeAverageResolution;
        this.arrayTotalResolvedVsTotal = arrayTotalResolvedVsTotal;
        this.labelsTotal = labelsTotal;
        this.arrayTotalAmount = arrayTotalAmount;
        this.arrayLabelsAmount = arrayLabelsAmount;
        this.amountNotPaid = amountNotPaid;
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

    public int getViaticosNotPaids() {
        return viaticosNotPaids;
    }

    public void setViaticosNotPaids(int viaticosNotPaids) {
        this.viaticosNotPaids = viaticosNotPaids;
    }

    public int getViaticosPaids() {
        return viaticosPaids;
    }

    public void setViaticosPaids(int viaticosPaids) {
        this.viaticosPaids = viaticosPaids;
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

    public String getAmountNotPaid() {
        return amountNotPaid;
    }

    public void setAmountNotPaid(String amountNotPaid) {
        this.amountNotPaid = amountNotPaid;
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
