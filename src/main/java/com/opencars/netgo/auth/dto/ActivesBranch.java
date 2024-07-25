package com.opencars.netgo.auth.dto;

public class ActivesBranch {

    int actives;
    int total;
    String branch;

    public ActivesBranch() {
    }

    public ActivesBranch(int actives, int total, String branch) {
        this.actives = actives;
        this.total = total;
        this.branch = branch;
    }

    public int getActives() {
        return actives;
    }

    public void setActives(int actives) {
        this.actives = actives;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
