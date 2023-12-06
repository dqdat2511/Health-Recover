package com.example.cssk.object;

import java.util.ArrayList;
import java.util.List;

public class User {
    String User_id;
    String HoTen;
    String Date;
    String email;
    String sex;
    String age;
    List<String> benh_ly = new ArrayList<>();

    public User() {
    }

    public User(String user_id, String hoTen, String date, String email, String sex, String age, List<String> benh_ly) {
        User_id = user_id;
        HoTen = hoTen;
        Date = date;
        this.email = email;
        this.sex = sex;
        this.age = age;
        this.benh_ly = benh_ly;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public List<String> getBenh_ly() {
        return benh_ly;
    }

    public void setBenh_ly(List<String> benh_ly) {
        this.benh_ly = benh_ly;
    }


    @Override
    public String toString() {
        return "User{" +
                "User_id='" + User_id + '\'' +
                ", HoTen='" + HoTen + '\'' +
                ", Date='" + Date + '\'' +
                ", email='" + email + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", benh_ly=" + benh_ly +
                '}';
    }
}

