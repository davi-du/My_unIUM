package com.example.my_unium;

import java.io.Serializable;

public class Exam implements Serializable {
    private String name;
    private int cfu, result;

    //getter
    public String getUser(){ return this.name; }
    public String getName(){ return this.name; }
    public int getCfu() { return cfu; }
    public int getResult() { return result; }

    //setter
    public void setName(String name) { this.name = name; }
    public void setCfu(int cfu) { this.cfu = cfu; }
    public void setResult(int result) { this.result = result;}
}
