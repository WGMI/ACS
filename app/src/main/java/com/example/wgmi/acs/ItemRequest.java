package com.example.wgmi.acs;

/**
 * Created by WGMI on 03/11/2017.
 */

public class ItemRequest {

    int request_id,user_id,permit,status;
    String sno;
    long date,date_from,date_to;

    public ItemRequest(){

    }

    public ItemRequest(int request_id, int user_id, int permit, int status, String sno, long date, long date_from, long date_to) {
        this.request_id = request_id;
        this.user_id = user_id;
        this.permit = permit;
        this.status = status;
        this.sno = sno;
        this.date = date;
        this.date_from = date_from;
        this.date_to = date_to;
    }

    public int getRequest_id() {
        return request_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getPermit() {
        return permit;
    }

    public int getStatus() {
        return status;
    }

    public String getSno() {
        return sno;
    }

    public long getDate() {
        return date;
    }

    public long getDate_from() {
        return date_from;
    }

    public long getDate_to() {
        return date_to;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setPermit(int permit) {
        this.permit = permit;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setDate_from(long date_from) {
        this.date_from = date_from;
    }

    public void setDate_to(long date_to) {
        this.date_to = date_to;
    }
}

