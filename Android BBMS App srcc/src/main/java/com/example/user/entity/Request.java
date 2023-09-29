package com.example.user.entity;

import java.io.Serializable;

public class Request implements Serializable {
    private int user_id;
    private String blood_group;
    private int unit;

    public Request() {
    }

    public Request(int user_id, String blood_group, int unit) {
        this.user_id = user_id;
        this.blood_group = blood_group;
        this.unit = unit;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Request{" +
                "user_id=" + user_id +
                ", blood_group='" + blood_group + '\'' +
                ", unit=" + unit +
                '}';
    }
}
