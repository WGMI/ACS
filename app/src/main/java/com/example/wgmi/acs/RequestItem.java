package com.example.wgmi.acs;

/**
 * Created by WGMI on 14/11/2017.
 */

public class RequestItem {

    String name,item_id,user_id,date,date_from,date_to,permit,status;

    public RequestItem(){

    }

    public RequestItem(String name, String item_id, String user_id, String date, String date_from, String date_to, String permit, String status) {
        this.name = name;
        this.item_id = item_id;
        this.user_id = user_id;
        this.date = date;
        this.date_from = date_from;
        this.date_to = date_to;
        this.permit = permit;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getItem_id() {
        return item_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getDate() {
        return date;
    }

    public String getDate_from() {
        return date_from;
    }

    public String getDate_to() {
        return date_to;
    }

    public String getPermit() {
        return permit;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDate_from(String date_from) {
        this.date_from = date_from;
    }

    public void setDate_to(String date_to) {
        this.date_to = date_to;
    }

    public void setPermit(String permit) {
        this.permit = permit;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
