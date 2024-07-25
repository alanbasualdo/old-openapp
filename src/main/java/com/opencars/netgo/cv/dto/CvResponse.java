package com.opencars.netgo.cv.dto;

import com.opencars.netgo.cv.entity.Cv;

public class CvResponse {

    private Cv cv;
    private int code;

    public CvResponse() {
    }

    public CvResponse(Cv cv, int code) {
        this.cv = cv;
        this.code = code;
    }

    public Cv getCv() {
        return cv;
    }

    public void setCv(Cv cv) {
        this.cv = cv;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
