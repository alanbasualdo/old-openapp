package com.opencars.netgo.dms.quoter.dto;

public class SeriesCarsInfoAuto {

    private int id;
    private boolean list_price;
    private String name;
    private boolean prices;
    private int prices_from;
    private int prices_to;
    private String summary;

    public SeriesCarsInfoAuto() {
    }

    public SeriesCarsInfoAuto(int id, boolean list_price, String name, boolean prices, int prices_from, int prices_to, String summary) {
        this.id = id;
        this.list_price = list_price;
        this.name = name;
        this.prices = prices;
        this.prices_from = prices_from;
        this.prices_to = prices_to;
        this.summary = summary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isList_price() {
        return list_price;
    }

    public void setList_price(boolean list_price) {
        this.list_price = list_price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrices() {
        return prices;
    }

    public void setPrices(boolean prices) {
        this.prices = prices;
    }

    public int getPrices_from() {
        return prices_from;
    }

    public void setPrices_from(int prices_from) {
        this.prices_from = prices_from;
    }

    public int getPrices_to() {
        return prices_to;
    }

    public void setPrices_to(int prices_to) {
        this.prices_to = prices_to;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
