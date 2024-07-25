package com.opencars.netgo.dms.quoter.dto;

import java.sql.Timestamp;

public class UpdateDatetime {

    private Timestamp updated_at;

    public UpdateDatetime() {
    }

    public UpdateDatetime(UpdateDatetime updateDatetime) {
    }

    public UpdateDatetime(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
