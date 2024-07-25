package com.opencars.netgo.dms.quoter.dto;

import java.time.LocalDateTime;

public class CotizationVO {

    private LocalDateTime date;
    private long priceMagazine;
    private float priceFinal;
    private long kmAverage;
    private float percentAverageKm;
    private float priceWithDiscountForAverage;
    private float priceToDiscountForSaleType;
    private float percentSaleType;
    private float priceToDiscountForCarType;
    private float percentCarType;
    private float priceToDiscountForYear;
    private float percentYear;
    private float priceToDiscountForYearAndKM;
    private float percentYearAndKm;
    private float priceToDiscountForSerie;
    private float percentSerie;
    private float priceToDiscountForModel;
    private float percentModel;
    private int code;
    private String observation;

    public CotizationVO() {
    }

    public CotizationVO(LocalDateTime date, long priceMagazine, float priceFinal, long kmAverage, float percentAverageKm, float priceWithDiscountForAverage, float priceToDiscountForSaleType, float percentSaleType, float priceToDiscountForCarType, float percentCarType, float priceToDiscountForYear, float percentYear, float priceToDiscountForYearAndKM, float percentYearAndKm, float priceToDiscountForSerie, float percentSerie, float priceToDiscountForModel, float percentModel, int code, String observation) {
        this.date = date;
        this.priceMagazine = priceMagazine;
        this.priceFinal = priceFinal;
        this.kmAverage = kmAverage;
        this.percentAverageKm = percentAverageKm;
        this.priceWithDiscountForAverage = priceWithDiscountForAverage;
        this.priceToDiscountForSaleType = priceToDiscountForSaleType;
        this.percentSaleType = percentSaleType;
        this.priceToDiscountForCarType = priceToDiscountForCarType;
        this.percentCarType = percentCarType;
        this.priceToDiscountForYear = priceToDiscountForYear;
        this.percentYear = percentYear;
        this.priceToDiscountForYearAndKM = priceToDiscountForYearAndKM;
        this.percentYearAndKm = percentYearAndKm;
        this.priceToDiscountForSerie = priceToDiscountForSerie;
        this.percentSerie = percentSerie;
        this.priceToDiscountForModel = priceToDiscountForModel;
        this.percentModel = percentModel;
        this.code = code;
        this.observation = observation;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public long getPriceMagazine() {
        return priceMagazine;
    }

    public void setPriceMagazine(long priceMagazine) {
        this.priceMagazine = priceMagazine;
    }

    public float getPriceFinal() {
        return priceFinal;
    }

    public void setPriceFinal(float priceFinal) {
        this.priceFinal = priceFinal;
    }

    public long getKmAverage() {
        return kmAverage;
    }

    public void setKmAverage(long kmAverage) {
        this.kmAverage = kmAverage;
    }

    public float getPercentAverageKm() {
        return percentAverageKm;
    }

    public void setPercentAverageKm(float percentAverageKm) {
        this.percentAverageKm = percentAverageKm;
    }

    public float getPriceWithDiscountForAverage() {
        return priceWithDiscountForAverage;
    }

    public void setPriceWithDiscountForAverage(float priceWithDiscountForAverage) {
        this.priceWithDiscountForAverage = priceWithDiscountForAverage;
    }

    public float getPriceToDiscountForSaleType() {
        return priceToDiscountForSaleType;
    }

    public void setPriceToDiscountForSaleType(float priceToDiscountForSaleType) {
        this.priceToDiscountForSaleType = priceToDiscountForSaleType;
    }

    public float getPercentSaleType() {
        return percentSaleType;
    }

    public void setPercentSaleType(float percentSaleType) {
        this.percentSaleType = percentSaleType;
    }

    public float getPriceToDiscountForCarType() {
        return priceToDiscountForCarType;
    }

    public void setPriceToDiscountForCarType(float priceToDiscountForCarType) {
        this.priceToDiscountForCarType = priceToDiscountForCarType;
    }

    public float getPercentCarType() {
        return percentCarType;
    }

    public void setPercentCarType(float percentCarType) {
        this.percentCarType = percentCarType;
    }

    public float getPriceToDiscountForYear() {
        return priceToDiscountForYear;
    }

    public void setPriceToDiscountForYear(float priceToDiscountForYear) {
        this.priceToDiscountForYear = priceToDiscountForYear;
    }

    public float getPercentYear() {
        return percentYear;
    }

    public void setPercentYear(float percentYear) {
        this.percentYear = percentYear;
    }

    public float getPriceToDiscountForYearAndKM() {
        return priceToDiscountForYearAndKM;
    }

    public void setPriceToDiscountForYearAndKM(float priceToDiscountForYearAndKM) {
        this.priceToDiscountForYearAndKM = priceToDiscountForYearAndKM;
    }

    public float getPercentYearAndKm() {
        return percentYearAndKm;
    }

    public void setPercentYearAndKm(float percentYearAndKm) {
        this.percentYearAndKm = percentYearAndKm;
    }

    public float getPriceToDiscountForSerie() {
        return priceToDiscountForSerie;
    }

    public void setPriceToDiscountForSerie(float priceToDiscountForSerie) {
        this.priceToDiscountForSerie = priceToDiscountForSerie;
    }

    public float getPercentSerie() {
        return percentSerie;
    }

    public void setPercentSerie(float percentSerie) {
        this.percentSerie = percentSerie;
    }

    public float getPriceToDiscountForModel() {
        return priceToDiscountForModel;
    }

    public void setPriceToDiscountForModel(float priceToDiscountForModel) {
        this.priceToDiscountForModel = priceToDiscountForModel;
    }

    public float getPercentModel() {
        return percentModel;
    }

    public void setPercentModel(float percentModel) {
        this.percentModel = percentModel;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
