package com.btl.entity;

public class User {
    private String id;
    private String fname;
    private String lname;
    private String pass;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User(String id, String fname, String lname, String pass, String type) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.pass = pass;
        this.type = type;
    }

    public String toString() {
        return id + " " + fname + " " + lname + " " + type;
    }

}