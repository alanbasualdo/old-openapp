package com.opencars.netgo.msgs;

public class MsgString {

    private String msg;
    private int code;
    private String id;

    public MsgString() {
    }

    public MsgString(String msg, int code, String id) {
        this.msg = msg;
        this.code = code;
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
