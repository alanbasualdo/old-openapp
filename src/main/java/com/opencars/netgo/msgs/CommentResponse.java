package com.opencars.netgo.msgs;

import com.opencars.netgo.support.tickets.entity.TicketsComents;

public class CommentResponse {

    private String msg;

    private int code;

    private TicketsComents coment;

    public CommentResponse() {
    }

    public CommentResponse(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public CommentResponse(String msg, int code, TicketsComents coment) {
        this.msg = msg;
        this.code = code;
        this.coment = coment;
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

    public TicketsComents getComent() {
        return coment;
    }

    public void setComent(TicketsComents coment) {
        this.coment = coment;
    }
}
