package com.opencars.netgo.dms.quoter.dto;

public class BrandsInfoAuto {

    private int id;
    private boolean prices;
    private int prices_to;
    private String name;
    private String logo_url;
    private String summary;
    private int prices_from;
    private GroupsInfoAuto[] groups;

    public BrandsInfoAuto() {
    }

    public BrandsInfoAuto(int id, boolean prices, int prices_to, String name, String logo_url, String summary, int prices_from, GroupsInfoAuto[] groups) {
        this.id = id;
        this.prices = prices;
        this.prices_to = prices_to;
        this.name = name;
        this.logo_url = logo_url;
        this.summary = summary;
        this.prices_from = prices_from;
        this.groups = groups;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPrices() {
        return prices;
    }

    public void setPrices(boolean prices) {
        this.prices = prices;
    }

    public int getPrices_to() {
        return prices_to;
    }

    public void setPrices_to(int prices_to) {
        this.prices_to = prices_to;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getPrices_from() {
        return prices_from;
    }

    public void setPrices_from(int prices_from) {
        this.prices_from = prices_from;
    }

    public GroupsInfoAuto[] getGroups() {
        return groups;
    }

    public void setGroups(GroupsInfoAuto[] groups) {
        this.groups = groups;
    }
}
