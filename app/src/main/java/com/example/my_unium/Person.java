package com.example.my_unium;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class Person implements Serializable {
    private String name, surname, birthday, mtr, email, course, username, password, confirmPassword;
    private Calendar birthdate;

    //getter
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getMtr() { return mtr; }
    public String getEmail() { return email; }
    public String getCourse() { return course; }
    public String getPassword(){ return password; }
    public String getConfirmPassword(){ return confirmPassword; }
    public String getUsername(){ return username; }
    public Calendar getBirthdate() { return birthdate; }
    public String getBirthday() {return birthday; }


    //setter
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setCourse(String course) { this.course = course; }
    public void setMtr(String mtr) { this.mtr = mtr; }
    public void setEmail(String email) { this.email = email; }
    public void setUsername(String username){ this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
    public void setBirthdate(Calendar birthdate) { this.birthdate = birthdate; }
    public void setBirthday(String birthday){ this.birthday = birthday; }
}
