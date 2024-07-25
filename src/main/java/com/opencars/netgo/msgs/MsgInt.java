package com.opencars.netgo.msgs;

public class MsgInt {

    private String msg;
    private int code;
    private int id;

    public MsgInt() {
    }

    public MsgInt(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public MsgInt(String msg, int code, int id) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
