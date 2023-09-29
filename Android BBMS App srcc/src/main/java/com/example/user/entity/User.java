package com.example.user.entity;

import java.io.Serializable;

public class User implements Serializable {
    private int user_id;
    private String userUserName;
    private String userFName;
    private int userAge;
    private String userGender;
    private String userBloodGroup;
    private long userPhone;
    private String userMail;
    private String userPassword;
    private String userPlace;

    public User() {
    }


    public User(int user_id, String userUserName, String userFName, int userAge, String userGender, String userBloodGroup, long userPhone, String userMail, String userPassword, String userPlace) {
        this.user_id = user_id;
        this.userUserName = userUserName;
        this.userFName = userFName;
        this.userAge = userAge;
        this.userGender = userGender;
        this.userBloodGroup = userBloodGroup;
        this.userPhone = userPhone;
        this.userMail = userMail;
        this.userPassword = userPassword;
        this.userPlace = userPlace;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUserUserName() {
        return userUserName;
    }

    public void setUserUserName(String userUserName) {
        this.userUserName = userUserName;
    }

    public String getUserFName() {
        return userFName;
    }

    public void setUserFName(String userFName) {
        this.userFName = userFName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserBloodGroup() {
        return userBloodGroup;
    }

    public void setUserBloodGroup(String userBloodGroup) {
        this.userBloodGroup = userBloodGroup;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPlace() {
        return userPlace;
    }

    public void setUserPlace(String userPlace) {
        this.userPlace = userPlace;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", userUserName='" + userUserName + '\'' +
                ", userFName='" + userFName + '\'' +
                ", userAge=" + userAge +
                ", userGender='" + userGender + '\'' +
                ", userBloodGroup='" + userBloodGroup + '\'' +
                ", userPhone=" + userPhone +
                ", userMail='" + userMail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userPlace='" + userPlace + '\'' +
                '}';
    }
}
