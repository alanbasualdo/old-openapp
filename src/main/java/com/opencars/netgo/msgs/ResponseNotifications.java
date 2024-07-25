package com.opencars.netgo.msgs;

import com.opencars.netgo.Notifications.entity.Notifications;

import java.util.List;

public class ResponseNotifications {

    private List<Notifications> list;
    private int code;

    public ResponseNotifications() {
    }

    public ResponseNotifications(List<Notifications> list, int code) {
        this.list = list;
        this.code = code;
    }

    public List<Notifications> getList() {
        return list;
    }

    public void setList(List<Notifications> list) {
        this.list = list;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
