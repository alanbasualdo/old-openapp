package com.opencars.netgo.msgs;

public class Msg {

    private String msg;
    private int code;

    private Long id;

    public Msg(String msg) {
        this.msg = msg;
    }

    public Msg(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public Msg(String msg, int code, Long id) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
