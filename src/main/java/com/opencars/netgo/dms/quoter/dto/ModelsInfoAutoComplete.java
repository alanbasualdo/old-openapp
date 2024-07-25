package com.opencars.netgo.dms.quoter.dto;

public class ModelsInfoAutoComplete {

    private boolean as_codia;
    private BrandInfo brand;
    private int codia;
    private String description;
    private int[] features;
    private BrandInfo group;
    private boolean list_price;
    private String photo_url;
    private int position;
    private boolean prices;
    private int prices_from;
    private int prices_to;
    private int r_codia;
    private String summary;

    public ModelsInfoAutoComplete() {
    }

    public ModelsInfoAutoComplete(boolean as_codia, BrandInfo brand, int codia, String description, int[] features, BrandInfo group, boolean list_price, String photo_url, int position, boolean prices, int prices_from, int prices_to, int r_codia, String summary) {
        this.as_codia = as_codia;
        this.brand = brand;
        this.codia = codia;
        this.description = description;
        this.features = features;
        this.group = group;
        this.list_price = list_price;
        this.photo_url = photo_url;
        this.position = position;
        this.prices = prices;
        this.prices_from = prices_from;
        this.prices_to = prices_to;
        this.r_codia = r_codia;
        this.summary = summary;
    }

    public boolean isAs_codia() {
        return as_codia;
    }

    public void setAs_codia(boolean as_codia) {
        this.as_codia = as_codia;
    }

    public BrandInfo getBrand() {
        return brand;
    }

    public void setBrand(BrandInfo brand) {
        this.brand = brand;
    }

    public int getCodia() {
        return codia;
    }

    public void setCodia(int codia) {
        this.codia = codia;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int[] getFeatures() {
        return features;
    }

    public void setFeatures(int[] features) {
        this.features = features;
    }

    public BrandInfo getGroup() {
        return group;
    }

    public void setGroup(BrandInfo group) {
        this.group = group;
    }

    public boolean isList_price() {
        return list_price;
    }

    public void setList_price(boolean list_price) {
        this.list_price = list_price;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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

    public int getR_codia() {
        return r_codia;
    }

    public void setR_codia(int r_codia) {
        this.r_codia = r_codia;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
