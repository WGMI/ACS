package com.example.wgmi.acs;

/**
 * Created by WGMI on 03/11/2017.
 */

public class Users {

    int id,active,type;
    String firstname,lastname,username,password;
    long date_joined;

    public Users(){

    }

    public Users(int id, int active, int type, String firstname, String lastname, String username, String password, long date_joined) {
        this.id = id;
        this.active = active;
        this.type = type;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.date_joined = date_joined;
    }

    public int getId() {
        return id;
    }

    public int getActive() {
        return active;
    }

    public int getType() {
        return type;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public long getDate_joined() {
        return date_joined;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDate_joined(long date_joined) {
        this.date_joined = date_joined;
    }
}
