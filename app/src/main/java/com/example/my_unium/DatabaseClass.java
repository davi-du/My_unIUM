package com.example.my_unium;

import java.util.ArrayList;

public class DatabaseClass {

    public static Person person = new Person();
    public static ArrayList<Exam> userExamList = new ArrayList<Exam>();
    public static Exam getExam(int i) { return userExamList.get(i); }

}
