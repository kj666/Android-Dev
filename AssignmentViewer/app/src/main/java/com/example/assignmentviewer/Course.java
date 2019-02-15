package com.example.assignmentviewer;

public class Course {

    private int ID;
    private String Title;
    private String Code;

    public Course(int ID, String title, String code) {
        this.ID = ID;
        Title = title;
        Code = code;
    }

    public Course(String title, String code) {
        Title = title;
        Code = code;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
